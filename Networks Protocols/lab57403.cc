/* -*- Mode:C++; c-file-style:"gnu"; indent-tabs-mode:nil; -*- */

/*
  Network topology:

  A----R----B 

  A--R: 12 Mbps / 15 ms delay
  R--B: 1000Kbps / 35 ms delay
  queue at R: size 10

oi apanthseis stis erwthseis tha uparxoun me sxolia akrivws ekei pou tha prepei na ginetai h allagh kathe fora.
oi allages gia thn RED (askisi 2.2 ) einai sthn seira 180.

*/

#include <iostream>
#include <fstream>
#include <string>

#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/ipv4-global-routing-helper.h"
#include "ns3/traffic-control-helper.h"

using namespace ns3;


std::string fileNameRoot = "lab-assignment";    // base name for trace files, etc

// Definition of trace sink callback
//kaleitai automata pao ton ns3 kathe fora pou uparxei allagh sthn timh tou cwnd
void CwndChange (Ptr<OutputStreamWrapper> stream, uint32_t oldCwnd, uint32_t newCwnd)
{
  *stream->GetStream () << Simulator::Now ().GetSeconds () << " " <<  newCwnd << std::endl;
}

//me auth thn sunarthsh pernw ta apotelesmata ths parapanw sunarthshs 
static void
TraceCwnd ()    // Trace changes to the congestion window
{
  AsciiTraceHelper ascii;
//to onoma tou orizetai sthn arxi seira 34 (se auth thn periptwsi lab1)
  Ptr<OutputStreamWrapper> stream = ascii.CreateFileStream (fileNameRoot + ".cwnd");
  // Connect the trace sink (CwndChange) to the appropriate trace source ("/.../CongestionWindow")
//me thn connectwithoucontext pernw tis metavoles tou cwnd to 0 sumvolizei ton komvo A
//kai ta stelnw se ena arxeio 
  Config::ConnectWithoutContext ("/NodeList/0/$ns3::TcpL4Protocol/SocketList/0/CongestionWindow", MakeBoundCallback (&CwndChange,stream)); 
}


int main (int argc, char *argv[])
{
 

  // Set simulation parameters
  int tcpSegmentSize = 1000;
  Config::SetDefault ("ns3::TcpSocket::SegmentSize", UintegerValue (tcpSegmentSize));
  Config::SetDefault ("ns3::TcpSocket::DelAckCount", UintegerValue (0));
  //edw mporw na allazw to protokolo pou xrhsimopoiw. wra exw to tcp new reno 
  Config::SetDefault ("ns3::TcpL4Protocol::SocketType", StringValue ("ns3::TcpNewReno"));
  
  //sthn epomenh seira einai h entoli gia TCP Vegas erwthma 1.3
  //Config::SetDefault ("ns3::TcpL4Protocol::SocketType", StringValue ("ns3::TcpVegas"));
  
  //sthn epomenh seira einai h entoli gia TCP West Wood erwthma 1.3
  //Config::SetDefault ("ns3::TcpL4Protocol::SocketType", StringValue ("ns3::TcpWestwood"));
  //Config::SetDefault ("ns3::RttEstimator::MinRTO", TimeValue (MilliSeconds (500)));

  unsigned int runtime = 20;   // seconds
  //kathisterisi prwtou kanaliou 
  int delayAR = 15;            // ms
  //kathisterisi deuterou kanaliou 
  int delayRB = 35;            // ms
  
  //ta epomena 2 einai gia to bandwidth 
  double bottleneckBW= 1;     // Mbps
  //double bottleneckBW= 0.1; //erwtima 1.2.a prwto 
  //double bottleneckBW= 11;  //erwthma 1.2.a deutero
  
  double fastBW = 12;         // Mbps

  //megethos ouras 
  uint32_t queuesize = 10;
  //uint32_t queuesize = 19.5;  //erwthma 2.1 me z=3 vgainei 19.5(int 19.5 =19).
 
  //posa byte epitrepw ston apostolea na steilei 
  uint32_t maxBytes = 0;       // 0 means "unlimited"

  CommandLine cmd;
  // Here, we define command line options overriding some of the above.
  cmd.AddValue ("runtime", "How long the applications should send data", runtime);
  cmd.AddValue ("delayRB", "Delay on the R--B link, in ms", delayRB);
  cmd.AddValue ("queuesize", "queue size at R", queuesize);
  cmd.AddValue ("tcpSegmentSize", "TCP segment size", tcpSegmentSize);
  
  cmd.Parse (argc, argv);

  //einai san thn printf mas emfanizei poio einai to megethos ths ouras kai poia h kathysterhsh tou deuterou kanaliou. Edw mporw na prosthesw oti thelw na mou emfanisei 
  std::cout << "queuesize=" << queuesize << ", delayRB=" << delayRB << std::endl;
  //NodeContainer allNodes; allNodes.Create(3); Ptr<Node> A = allNodes.Get(0), etc
  //edw ksekinaw na dhmiourgw to diktuo ftiaxnwntas 3 komvous  
  Ptr<Node> A = CreateObject<Node> ();
  Ptr<Node> R = CreateObject<Node> ();
  Ptr<Node> B = CreateObject<Node> ();

  // use PointToPointChannel and PointToPointNetDevice  
  //dhmiourgw 2 kartes duktiou pou tha thesw sta 2 kanalia           
  NetDeviceContainer devAR, devRB;
  //dhmiourgw 2 kanalia
  PointToPointHelper AR, RB;

  //gia to prwto kanali 
  // create point-to-point link from A to R
  //ruthmos dedomenwn pou tha stelnei 
  AR.SetDeviceAttribute ("DataRate", DataRateValue (DataRate (fastBW * 1000 * 1000)));
  //kathisterhsh tou prwtou kanaliou 
  AR.SetChannelAttribute ("Delay", TimeValue (MilliSeconds (delayAR)));
  //afou ta orisw ta egkathistw sthn karta diktuou 
  devAR = AR.Install(A, R);

  //antistoixa kai sto deutero kanali 
  // create point-to-point link from R to B
  RB.SetDeviceAttribute ("DataRate", DataRateValue (DataRate (bottleneckBW * 1000 * 1000)));
  RB.SetChannelAttribute ("Delay", TimeValue (MilliSeconds (delayRB)));
  std::string maxsize = std::to_string(queuesize).append("p");
  //edw orizw kai to megisto arithmo paketwn pou tha dexetai o endiamesos dromologitis
  //thn timh thn pairnei apo thn parapanw seira to queuesize   
  RB.SetQueue("ns3::DropTailQueue", "MaxSize", StringValue(maxsize));
  //RB.SetQueue("ns3::DropTailQueue", "MaxPackets", UintegerValue(queuesize));
  devRB = RB.Install(R,B);

   // pointers to netdevices
    Ptr<NetDevice> Adev = devAR.Get(0) ;
    Ptr<NetDevice> Rdev1 = devAR.Get(1) ;
    Ptr<NetDevice> Rdev2 = devRB.Get(0) ;
    Ptr<NetDevice> Bdev = devRB.Get(1) ;

  InternetStackHelper internet;
  internet.Install (A);
  internet.Install (R);
  internet.Install (B);

  // Assign IP addresses

  Ipv4AddressHelper ipv4;
  ipv4.SetBase ("10.0.0.0", "255.255.255.0");
  Ipv4InterfaceContainer ipv4Interfaces;
  ipv4Interfaces.Add(ipv4.Assign (devAR));
  ipv4.SetBase ("10.0.1.0", "255.255.255.0");
  ipv4Interfaces.Add(ipv4.Assign(devRB));

  //sumplirononta oi pinakes tou routing kata thn prosomoiwsh
  Ipv4GlobalRoutingHelper::PopulateRoutingTables ();

  //me aute sti entoles pairnw tis dieuthinseis kai tis emfanizw sto terminal kata thn prosomoiwsh 
  Ptr<Ipv4> A4 = A->GetObject<Ipv4>();  // gets node A's IPv4 subsystem
  Ptr<Ipv4> B4 = B->GetObject<Ipv4>();
  Ptr<Ipv4> R4 = R->GetObject<Ipv4>();
  Ipv4Address Aaddr = A4->GetAddress(1,0).GetLocal();
  Ipv4Address Baddr = B4->GetAddress(1,0).GetLocal();
  Ipv4Address Raddr = R4->GetAddress(1,0).GetLocal();

  std::cout << "A's address: " << Aaddr << std::endl;
  std::cout << "B's address: " << Baddr << std::endl;
  std::cout << "R's #1 address: " << Raddr << std::endl;
  std::cout << "R's #2 address: " << R4->GetAddress(2,0).GetLocal() << std::endl;

  // Remove traffic controller, so that no queuing discipline is used and cwnd fluctuations can  be clearly observed
   TrafficControlHelper tch;
   tch.Uninstall(Rdev1);
   tch.Uninstall(Rdev2);
   tch.Uninstall(Adev);
   tch.Uninstall(Bdev);
   

  /*//askisi 2.2
  //RED PARAMETERS
  //gnwrizoume apo theoria oti prepei na einai 0.002 
  Config::SetDefault ("ns3::RedQueueDisc::QW", DoubleValue (0.002));
  //oi times twn 2 katfliwn 
  //to min threshold tha einai to 1/3 tou maxthreshold 
  Config::SetDefault ("ns3::RedQueueDisc::MinTh", DoubleValue (3.17));
  //to max threshold tha einai to miso tou 19  ( 19 einai h int value tou 19.5 pou prokuptei apo to diko mou aem)
  Config::SetDefault ("ns3::RedQueueDisc::MaxTh", DoubleValue (9.5));

  //traffic control pou antistoixei sthn red
  TrafficControlHelper tchRed;
  //ta datarate kai kathisterisi einai opws ta exw orisei sthn arxh tou kwdika 
  tchRed.SetRootQueueDisc ("ns3::RedQueueDisc");
  //vhma 3 gia thn red 
   tchRed.Install(Rdev2);
   tchRed.Install(Bdev);
   */ 

  // create a sink on B
  //autos o komvos tha dexetai ta paketa sthn porta me ton arithmo 80 
  uint16_t Bport = 80;
  Address sinkAaddr(InetSocketAddress (Ipv4Address::GetAny (), Bport));
  PacketSinkHelper sinkA ("ns3::TcpSocketFactory", sinkAaddr);
  ApplicationContainer sinkAppA = sinkA.Install (B);
  //ksekinaei thn xronikh stigmh 0.01 
  sinkAppA.Start (Seconds (0.01));
  // the following means the receiver will run 1 min longer than the sender app.
  //stamataei na dexetai paketa thn xronikh stigmh 80 sec afou ksekinaw sta 20 
  sinkAppA.Stop (Seconds (runtime + 60.0));

  Address sinkAddr(InetSocketAddress(Baddr, Bport));
  // edw egkathistw ton sender ston A einai ena source
  //edw deixnw pou tha to stelnei
  BulkSendHelper sourceAhelper ("ns3::TcpSocketFactory",  sinkAddr);
  //posa bytes tou epitrepw na steilei an einai 0 shmainei aperiorista 
  sourceAhelper.SetAttribute ("MaxBytes", UintegerValue (maxBytes)); 
  //to megethos twn paketwn pou th astelnw orizetai sthn arxh tou kwdika shtn metailti tcp
  //segment size
  sourceAhelper.SetAttribute ("SendSize", UintegerValue (tcpSegmentSize));
  ApplicationContainer sourceAppsA = sourceAhelper.Install (A);
  //ksekinaei thn xroniki stigmh 0 
  sourceAppsA.Start (Seconds (0.0));
  //stamataei thn xroniki stigmh 20 
  sourceAppsA.Stop (Seconds (runtime));

  // Set up tracing
  AsciiTraceHelper ascii;
  std::string tfname = fileNameRoot + ".tr";
  AR.EnableAsciiAll (ascii.CreateFileStream (tfname));
  // Setup tracing for cwnd
  Simulator::Schedule(Seconds(0.01),&TraceCwnd);       // this Time cannot be 0.0
  
  // This tells ns-3 to generate pcap traces, including "-node#-dev#-" in filename
  //  AR.EnablePcapAll (fileNameRoot);    // ".pcap" suffix is added automatically

  Simulator::Stop (Seconds (runtime+60));
  Simulator::Run ();

  Ptr<PacketSink> sink1 = DynamicCast<PacketSink> (sinkAppA.Get (0));
  std::cout << "Total Bytes Received from B: " << sink1->GetTotalRx () << std::endl;
  return 0;
}
