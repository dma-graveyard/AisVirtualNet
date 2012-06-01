# AisVirtualNet #

## Description ##

AisVirtualNet is a small utiltiy for creating a virtual AIS network. The network can be fed
real AIS data from multiple sources, and a number of virtual transponders can be configured.
The virtual transponders are given a MMSI number. Real messages from the MMSI number are converted
to own messages for the virtual transponder. Furthermore own messages are injected at a regular 
interval as copies of the last own message. 

## Prerequisites ##

* Java 1.6+ (http://www.java.com/)

## Sources ##

Sources can either be TCP or serial. Using TCP sources is recommended as serial sources 
require special system configuration for Java to be able to access the ports.

See: http://pradnyanaik.wordpress.com/2009/04/07/communicating-with-ports-using-javaxcomm-package-for-windows/

## Transponders ##

Transponders are given MMSI they shall simulate to be and a TCP port to offer interface on. 
Equipment or applications can now be connected to the TCP port and will think they are connected
to an actual transponder.
  

