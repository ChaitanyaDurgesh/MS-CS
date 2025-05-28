# pcap replay
# Nik Sultana, January 2024, Illinois Tech
#
# Works around current FABRIC issue where interfaces aren't put into promiscuous mode, and are filtering by their MAC address.
#  See https://learn.fabric-testbed.net/forums/topic/receiving-multicast-frames-on-a-nic_basic-interface-on-an-l2bridge/
#
# Usage:
#   sudo python3 traffic_replay.py

from scapy.all import *

input_pcap_file = "test.pcap"
out_iface = "enp7s0"
new_dst_mac = "06:e4:2c:4e:db:c2"
repeats = 1

packet_count = 0
for i in range(repeats):
   for pkt in rdpcap(input_pcap_file):

    if pkt.haslayer(Ether):
      pkt[Ether].dst = new_dst_mac # See  https://scapy.readthedocs.io/en/latest/api/scapy.layers.l2.html#scapy.layers.l2.Ether

    sendp(pkt, iface = out_iface, verbose = False)
    packet_count += 1

print("Sent " + str(packet_count) + " packets over " + out_iface)