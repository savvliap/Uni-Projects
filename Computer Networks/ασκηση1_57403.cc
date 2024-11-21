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
 */

#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

NS_LOG_COMPONENT_DEFINE ("FirstScriptExample");

int
main (int argc, char *argv[])
{
  CommandLine cmd;
  cmd.Parse (argc, argv);
  
  Time::SetResolution (Time::NS);
  LogComponentEnable ("UdpEchoClientApplication", LOG_LEVEL_INFO);
  LogComponentEnable ("UdpEchoServerApplication", LOG_LEVEL_INFO);

  NodeContainer nodes;
  //edw kathorizw posa nodes tha xreiastw .gia thn askis thelw 2 
  nodes.Create (2);

  //point to point epikoinwnia 
  PointToPointHelper pointToPoint;
  //kathorizw posa bit per second tha stelnw 3+1=4 Mbps
  //sto erwthma b se auto to shmeio tha allaksw to bandwidth se 8 , 12 kai 16 Mbps
  pointToPoint.SetDeviceAttribute ("DataRate", StringValue ("12Mbps"));
  //kathorizw to delay 
  pointToPoint.SetChannelAttribute ("Delay", StringValue ("10ms"));

  NetDeviceContainer devices;
  devices = pointToPoint.Install (nodes);

  InternetStackHelper stack;
  stack.Install (nodes);

  Ipv4AddressHelper address;
  address.SetBase ("10.1.1.0", "255.255.255.0");

  Ipv4InterfaceContainer interfaces = address.Assign (devices);

  UdpEchoServerHelper echoServer (9);

  //edw dixnw poio node pairnei o server 
  ApplicationContainer serverApps = echoServer.Install (nodes.Get (0));
  serverApps.Start (Seconds (1.0));
  serverApps.Stop (Seconds (20.0));

  //prepei na alaksw kai edw na valw to node tou server 
  UdpEchoClientHelper echoClient (interfaces.GetAddress (0), 9);
  //posa paketa tha steilw 
  echoClient.SetAttribute ("MaxPackets", UintegerValue (20));
  //kathe pote stelnw paketo 2 paketa to deuterolepto ara 1 kathe miso deuterolepto 
  echoClient.SetAttribute ("Interval", TimeValue (Seconds (0.5)));
  //to megethos tou paketou 
  echoClient.SetAttribute ("PacketSize", UintegerValue (1024));


  //edw kathorizw to node pou tha parei o client (sumfwna me thn aksisi 1)
  ApplicationContainer clientApps = echoClient.Install (nodes.Get (1));
  clientApps.Start (Seconds (1.0));
  clientApps.Stop (Seconds (20.0));

  Simulator::Run ();
  Simulator::Destroy ();
  return 0;
}