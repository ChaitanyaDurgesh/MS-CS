from scapy.all import *
  
max_cap = 10
cap_so_far = 0
#output_pcap_file = "output.pcap" # (2) Saving a pcap file from a listening session.
#input_pcap_file = "input.pcap" # (3) Analyzing a pcap file.

def print_packet (pkt):
  global max_cap
  global cap_so_far

  #wrpcap(output_pcap_file, pkt, append = True) # (2) Saving a pcap file from a listening session.
  print(pkt.summary()) # (3) vs hexdump(pkt) vs pkt.show()

  cap_so_far += 1
  if cap_so_far >= max_cap: exit(0)

pkt = sniff(iface = 'enp3s0', prn = print_packet) # (1) Listening to interface and analyzing traffic.

## (3) Analyzing a pcap file.
#for pkt in rdpcap(input_pcap_file):
#  ##  (4) Editing a pcap file.
#  #if pkt.haslayer(TCP):
#  #  pkt[TCP].sport = 1 # See  https://scapy.readthedocs.io/en/latest/api/scapy.layers.inet.html#scapy.layers.inet.TCP
#  #  pkt[TCP].dport = 2
#  print_packet(pkt)