from scapy.all import Ether, IP, IPv6, ICMP, TCP, UDP, wrpcap

# Create an empty list to hold the network_packets
network_packets = []

# Generate 1 IPv6 packet with ICMP protocol
packet = Ether() / IPv6(src="2001:db8:1::1", dst="2001:db8:2::1") / ICMP()
network_packets.append(packet)

# Generate 1 IPv4 packet with TCP protocol and specific source/destination ports
packet = Ether() / IP(src="192.168.1.1", dst="192.168.1.2") / TCP(sport=1234, dport=5678)
network_packets.append(packet)

# Generate 1 IPv4 packet with UDP protocol and specific source/destination ports
packet = Ether() / IP(src="192.168.1.3", dst="192.168.1.4") / UDP(sport=1234, dport=5678)
network_packets.append(packet)

# Generate 1 IPv4 packet with ICMP protocol
packet = Ether() / IP(src="192.168.1.5", dst="192.168.1.6") / ICMP()
network_packets.append(packet)

# Generate 1 IPv6 packet with TCP protocol and specific source/destination ports
packet = Ether() / IPv6(src="2001:db8:3::1", dst="2001:db8:4::1") / TCP(sport=1234, dport=5678)
network_packets.append(packet)

# Write the packet list to a pcap file
wrpcap('rejected_packets.pcap', network_packets)