# AisVirtualNet #

## Introduction ##

AisVirtualNet is a small utility for creating a virtual AIS network. The network can be fed
real AIS data from multiple sources, and a number of virtual transponders can be configured.
The virtual transponders are given a MMSI number. Real messages from the MMSI number are converted
to own messages for the virtual transponder. Furthermore own messages are injected at a regular 
interval as copies of the last own message.   

AisVirtualNet is run on a server that clients connect to. The server could be a participating
computer that is also a client. Only one instance of AisVirtualNet should be run the create
the virtual network. For clients to be able to connect to AisVirtualNet it is recommended to have
all computers and equipment on the same LAN without firewall restrictions.

## Prerequisites ##

* Java 1.6+ (http://www.java.com/)
* At least one AIS source
* At least one AIS client like an ECDIS or the open source [ee-INS](https://github.com/DaMSA/ee-INS)

## Installation and running ##

The application does not have to be installed. Download the zip file

https://github.com/DaMSA/AisVirtualNet/raw/master/aisvirtualnet.zip

unpack at any location and run the BAT file.

## Sources ##

Sources can either be TCP or serial. Using TCP sources is recommended as serial sources 
require special system configuration for Java to be able to access the ports.

See: http://pradnyanaik.wordpress.com/2009/04/07/communicating-with-ports-using-javaxcomm-package-for-windows/

## Transponders ##

Transponders are given MMSI they shall simulate and a TCP port to offer interface on. 
Clients like AIS equipment or applications (e.g. ECDIS) can now be connected to the TCP port and 
will think they are connected to an actual transponder.

The transponder require that AIS messages from the given MMSI number is in the data stream from
the sources. Otherwise no own messages will be generated.
  
## Setup ##

The diagram below gives an illustration of an example setup. AisVirtualNet could be run on any computer
that is reachable by all the clients.

![Diagram](https://github.com/DaMSA/AisVirtualNet/raw/master/doc/Diagram.png) 

The figure below shows an example of a configuration of AisVirtualNet with a single AIS source and 
two virtual transponders.

![Example](https://github.com/DaMSA/AisVirtualNet/raw/master/doc/Example.png)

For the client to connect to the virtual transponder, the IP address of the machine running 
AisVirtualNet is needed. When the address is known the client can be configured to use address/port
for the virtual transponder. The figure below shows an example from the ee-INS setup where the 
IP is 10.3.240.46 and the port 10001 from the previous example is used.

![Setup](https://github.com/DaMSA/AisVirtualNet/raw/master/doc/Setup.png)

## ee-INS note ## 

If the AIS source contains a lot of targets, it could give a big load on the client.
Furthermore will ship based equipment only be able to receive messages from vessels in the 
vicinity. To simulate this go to: Setup -> Sensor and set "AIS sensor range" to e.g. 40 
(nautical miles). 
