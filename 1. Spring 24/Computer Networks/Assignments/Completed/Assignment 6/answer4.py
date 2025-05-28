from scapy.all import Ether, IP, IPv6, TCP, UDP, wrpcap

# Create an empty list to hold the network_packets
network_packets = []

# Generate 3 IPv4 packets with TCP protocol and specific source/destination ports
for counter in range(3):
    packet = Ether() / IP(src="192.168.1.{}".format(counter+1), dst="192.168.1.{}".format(counter+2)) / TCP(sport=1024+counter, dport=80)
    network_packets.append(packet)

# Generate 2 IPv6 packets with UDP protocol and specific source/destination ports
for counter in range(2):
    packet = Ether() / IPv6(src="2001:db8:1::{:02x}".format(counter+1), dst="2001:db8:2::{:02x}".format(counter+1)) / UDP(sport=1024+counter, dport=7809)
    network_packets.append(packet)

# Write the packet list to a pcap file
wrpcap('accepted_packets.pcap', network_packets)