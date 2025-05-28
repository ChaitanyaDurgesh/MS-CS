from scapy.all import *

# Examples from https://github.com/secdev/scapy/blob/master/doc/notebooks/Scapy%20in%2015%20minutes.ipynb

pkt = IP(dst="1.2.3.4")/TCP(dport=502, options=[("MSS", 0)])

#pkt = IP()/TCP()
#pkt = Ether()/pkt
#
#pkt = IP() / UDP() / DNS(qd=DNSQR())
#
#pkt = Ether()/IP(dst="8.8.8.8", ttl=(5,10))/UDP()/DNS(rd=1, qd=DNSQR(qname="www.example.com"))
#
#pkt = Ether()/IP(dst="www.secdev.org")/TCP()

print(pkt.summary()) # (3) vs hexdump(pkt) vs pkt.show()

# Next: Save the generated packet to a pcap file.