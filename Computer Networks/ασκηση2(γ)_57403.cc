/* -*- Mode:C++; c-file-style:"gnu"; indent-tabs-mode:nil; -*- */
/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation;
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

//
// Network topology
//
//           10Mb/s, 10ms       10Mb/s, 10ms
//       n0-----------------n1-----------------n2
//
//                     topology ths askisis 2
//
//          0.5Mbps, 10ms         0.5Mbps,10ms       0.5Mbps, 10ms
//      no-----------------n1-----------------n2----------------n3
//
//                 topology tis askisis 2 me to neo delay
//
//          0.5Mbps, 10ms         0.5Mbps,10ms    0.5Mbps, 38,32ms
//      no-----------------n1-----------------n2----------------n3
//         o prosthetos kwdika gia to erwtima g einai stis seires 
//                            150-162
//
//
//
// - Tracing of queues and packet receptions to file
//   "tcp-large-transfer.tr"
// - pcap traces also generated in the following files
//   "tcp-large-transfer-$n-$i.pcap" where n and i represent node and interface
// numbers respectively
//  Usage (e.g.): ./waf --run tcp-large-transfer

#include <iostream>
#include <fstream>
#include <string>

#include "ns3/core-module.h"
#include "ns3/applications-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/ipv4-global-routing-helper.h"
#include "ns3/netanim-module.h"


using namespace ns3;




NS_LOG_COMPONENT_DEFINE ("TcpLargeTransfer");

// The number of bytes to send in this simulation.
//thelw na steillw 300KB=307200
static const uint32_t totalTxBytes = 307200;
static uint32_t currentTxBytes = 0;
// Perform series of 1040 byte writes (this is a multiple of 26 since
// we want to detect data splicing in the output stream)
//thelw ta paketa na einai twn 1040bytes
static const uint32_t writeSize = 1040;
uint8_t data[writeSize];

// These are for starting the writing process, and handling the sending
// socket's notification upcalls (events).  These two together more or less
// implement a sending "Application", although not a proper ns3::Application
// subclass.

//newval
void StartFlow (Ptr<Socket>, Ipv4Address, uint16_t);
//oldval
void WriteUntilBufferFull (Ptr<Socket>, uint32_t);

//to sink mou
static void CwndTracer (uint32_t oldval, uint32_t newval)
{
//  NS_LOG_INFO ("moving from "<<oldval<<" to "<<newval);
NS_LOG_INFO(Simulator::Now ().GetSeconds ()<< "\t"<<newval);
}

int main (int argc, char *argv[])
{
  // Users may find it convenient to turn on explicit debugging
  //for selected modules; the below lines suggest how to do this
   // LogComponentEnable("TcpL4Protocol", LOG_LEVEL_ALL);
   // LogComponentEnable("TcpSocketImpl", LOG_LEVEL_ALL);
  // LogComponentEnable("PacketSink", LOG_LEVEL_ALL);
   // LogComponentEnable("TcpLargeTransfer", LOG_LEVEL_ALL);

  CommandLine cmd;
  cmd.Parse (argc, argv);

  // initialize the tx buffer.
  //apo i=0 ; i<1040 (to exw orisei egw san megethos paketou;i++
  for(uint32_t i = 0; i < writeSize; ++i)
    {
      char m = toascii (97 + i % 26);
      data[i] = m;
    }

  // Here, we will explicitly create three nodes.  The first container contains
  // nodes 0 and 1 from the diagram above, and the second one contains nodes
  // 1 and 2.  This reflects the channel connectivity, and will be used to
  // install the network interfaces and connect them with a channel.

  //ftiaxnw 3 node containers gia na vallw ta no n1 n2 n3
  NodeContainer n0n1;
  n0n1.Create (2);

  NodeContainer n1n2;
  n1n2.Add (n0n1.Get (1));
  n1n2.Create (1);

  //auto einai to kainourgio kai sthn ousia an exw katalavei kala
  //lew oti pairnw to ena pou uparxei idi kai dimiourgw allo ena
  NodeContainer n2n3;
  n2n3.Add (n1n2.Get (1));
  n2n3.Create (1);

  // We create the channels first without any IP addressing information
  // First make and configure the helper, so that it will put the appropriate
  // attributes on the network interfaces and channels we are about to install.
 
  //ftianw to kananali edw kai kathorizw to datarate kai to delay opws mou leei h askisi
  //diladi  datarate 0.5 Mbps kai delay 10ms gia to erwtima g):delay=30.3155
  PointToPointHelper p2p;
  p2p.SetDeviceAttribute ("DataRate", DataRateValue (DataRate (500000)));
  p2p.SetChannelAttribute ("Delay", TimeValue (MilliSeconds (10)));

  //gia to kanali 3 : nomizw oti prepei na kanw auto
  PointToPointHelper p2pnew;
  p2pnew.SetDeviceAttribute ("DataRate", DataRateValue (DataRate (500000)));
  p2pnew.SetChannelAttribute ("Delay", TimeValue (MilliSeconds (38.32)));


  // And then install devices and channels connecting our topology.
  //suskevi diktiou
  NetDeviceContainer dev0 = p2p.Install (n0n1);
  NetDeviceContainer dev1 = p2p.Install (n1n2);
  //h nea suskei duktiou
  NetDeviceContainer dev2 = p2pnew.Install (n2n3);

  // Now add ip/tcp stack to all nodes.
  InternetStackHelper internet;
  internet.InstallAll ();

  // Later, we add IP addresses.
  Ipv4AddressHelper ipv4;
  ipv4.SetBase ("10.1.3.0", "255.255.255.0");
  ipv4.Assign (dev0);
  ipv4.SetBase ("10.1.2.0", "255.255.255.0");
  //auto to evala egw den kserw an einai komple
  ipv4.Assign (dev1);
  ipv4.SetBase ("10.1.1.0", "255.255.255.0");
  //auto to ksanavlepw stis seira 160 ksana alla den nomizw oti prepei na alaksw kati
  Ipv4InterfaceContainer ipInterfs = ipv4.Assign (dev2);

  // and setup ip routing tables to get total ip-level connectivity.
  //gia na dromologithoun dedomena apo to ena diktuo sto allo aparaititi entoli
  Ipv4GlobalRoutingHelper::PopulateRoutingTables ();

  ///////////////////////////////////////////////////////////////////////////
  // Simulation 1
  //
  // Send 2000000 bytes over a connection to server port 50000 at time 0
  // Should observe SYN exchange, a lot of data segments and ACKS, and FIN
  // exchange.  FIN exchange isn't quite compliant with TCP spec (see release
  // notes for more info)
  //
  ///////////////////////////////////////////////////////////////////////////

  //to port to server einai to 50000 den nomizw oti me endiaferei kai poli
  uint16_t servPort = 50000;

  // Create a packet sink to receive these packets on n2...
  // auto prepei na to alaksw ? gt egw thelw na ta lamvanw sto n3 h thelw na to afisw etsi
  //afou prepei na perasoun kai apao to n2
  //nomizw oti den xreiazetai allagi
  PacketSinkHelper sink ("ns3::TcpSocketFactory",
                         InetSocketAddress (Ipv4Address::GetAny (), servPort));

  //edw logika einai ekei pou tha valw to sink
  //kanw install
  //to allasa se 0 na dw kati kai tha to ksanallaksw se 1
  //me to get(0) den mou vgazei kanena apotelesma
  ApplicationContainer apps = sink.Install (n2n3.Get (1));
  apps.Start (Seconds (0.0));
  //prepei na valw kainourgio xrono gia na oloklirwthei i prosomoiwsi
  apps.Stop (Seconds (7.0));

  // Create a source to send packets from n0.  Instead of a full Application
  // and the helper APIs you might see in other example files, this example
  // will use sockets directly and register some socket callbacks as a sending
  // "Application".

  // Create and bind the socket...
  Ptr<Socket> localSocket =
    Socket::CreateSocket (n0n1.Get (0), TcpSocketFactory::GetTypeId ());
  localSocket->Bind ();

  // Trace changes to the congestion window
  //auto einai to trace mou
  Config::ConnectWithoutContext ("/NodeList/0/$ns3::TcpL4Protocol/SocketList/0/CongestionWindow", MakeCallback (&CwndTracer));

  // ...and schedule the sending "Application"; This is similar to what an
  // ns3::Application subclass would do internally.
  ///grami 160///////////////////////////////////////////////////
  //an dokimasw na vallw 0 den mou vgazei kanena apotelesma
  //an dokimasw na vallw 2  vgazei error
  //ti na allaksw dld
  Simulator::ScheduleNow (&StartFlow, localSocket,
                          ipInterfs.GetAddress (1), servPort);

  // One can toggle the comment for the following line on or off to see the
  // effects of finite send buffer modelling.  One can also change the size of
  // said buffer.

  //localSocket->SetAttribute("SndBufSize", UintegerValue(4096));

  //Ask for ASCII and pcap traces of network traffic
  AsciiTraceHelper ascii;
  p2p.EnableAsciiAll (ascii.CreateFileStream ("tcp-large-transfer.tr"));
  p2p.EnablePcapAll ("tcp-large-transfer");

  AnimationInterface anim ("tcplarge-anim.xml"); //generate
  Ptr<Node> n1 = n0n1.Get(0);
  Ptr<Node> n2 = n0n1.Get(1);
  Ptr<Node> n3 = n1n2.Get(1);
  Ptr<Node> n4 = n2n3.Get(1);
  anim.SetConstantPosition(n1,0,0);
  anim.SetConstantPosition(n2,20,20);
  anim.SetConstantPosition(n3,40,40);
  anim.SetConstantPosition(n4,60,60);


  // Finally, set up the simulator to run.  The 1000 second hard limit is a
  // failsafe in case some change above causes the simulation to never end
  Simulator::Stop (Seconds (1000));
  Simulator::Run ();
  Simulator::Destroy ();
}


//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//begin implementation of sending "Application"
void StartFlow (Ptr<Socket> localSocket,
                Ipv4Address servAddress,
                uint16_t servPort)
{
  NS_LOG_LOGIC ("Starting flow at time " <<  Simulator::Now ().GetSeconds ());
  localSocket->Connect (InetSocketAddress (servAddress, servPort)); //connect

  // tell the tcp implementation to call WriteUntilBufferFull again
  // if we blocked and new tx buffer space becomes available
  localSocket->SetSendCallback (MakeCallback (&WriteUntilBufferFull));
  WriteUntilBufferFull (localSocket, localSocket->GetTxAvailable ());
}

void WriteUntilBufferFull (Ptr<Socket> localSocket, uint32_t txSpace)
{
  while (currentTxBytes < totalTxBytes && localSocket->GetTxAvailable () > 0)
    {
      uint32_t left = totalTxBytes - currentTxBytes;
      uint32_t dataOffset = currentTxBytes % writeSize;
      uint32_t toWrite = writeSize - dataOffset;
      toWrite = std::min (toWrite, left);
      toWrite = std::min (toWrite, localSocket->GetTxAvailable ());
      int amountSent = localSocket->Send (&data[dataOffset], toWrite, 0);
      if(amountSent < 0)
        {
          // we will be called again when new tx space becomes available.
          return;
        }
      currentTxBytes += amountSent;
    }
  localSocket->Close ();
}
