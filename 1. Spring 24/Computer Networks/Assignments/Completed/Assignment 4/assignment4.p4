/* -- P4_16 -- */
#include <core.p4>
#include <v1model.p4>

const bit<16> TYPE_IPV6 = 0x86DD;
const bit<8> TYPE_TCP = 0x06;
const bit<8> TYPE_UDP = 0x11;

typedef bit<9>  egressSpec_t;
typedef bit<48> macAddr_t;
typedef bit<128> ip6Addr_t;

/*************************************************************************
*********************** H E A D E R S  ***********************************
*************************************************************************/

header ethernet_t {
    macAddr_t dstAddr;
    macAddr_t srcAddr;
    bit<16>   etherType;
}

header ipv6_t {
    bit<4>     version;
    bit<8>     trafficClass;
    bit<20>    flowLabel;
    bit<16>    payloadLen;
    bit<8>     nextHdr;
    bit<8>     hopLimit;
    ip6Addr_t  srcAddr;
    ip6Addr_t  dstAddr;
    bit<16>    checksum;
}

header tcp_t {
    bit<16> srcPort;
    bit<16> dstPort;
    bit<32> seqNo;
    bit<32> ackNo;
    bit<4>  dataOffset;
    bit<3>  res;
    bit<3>  ecn;
    bit<6>  ctrl;
    bit<16> window;
    bit<16> checksum;
    bit<16> urgentPtr;
}

header udp_t {
    bit<16> srcPort;
    bit<16> dstPort;
    bit<16> length_;
    bit<16> checksum;
}

struct metadata {
    bit<1> is_tcp_udp_filtered;
    bit<16> allowed_ports;
}

struct headers {
    ethernet_t ethernet;
    ipv6_t ipv6;
    tcp_t tcp;
    udp_t udp;
}

/*************************************************************************
*********************** P A R S E R ***********************************
*************************************************************************/

parser MyParser(packet_in packet,
                out headers hdr,
                inout metadata meta,
                inout standard_metadata_t standard_metadata) {

    state start {
        transition parse_ethernet;
    }

    state parse_ethernet {
        packet.extract(hdr.ethernet);
        transition select(hdr.ethernet.etherType) {
            TYPE_IPV6: parse_ipv6;
            default: accept;
        }
    }

    state parse_ipv6 {
        packet.extract(hdr.ipv6);
        transition select(hdr.ipv6.nextHdr) {
            TYPE_TCP: parse_tcp;
            TYPE_UDP: parse_udp;
            default: accept;
        }
    }

    state parse_tcp {
        packet.extract(hdr.tcp);
        transition accept;
    }

    state parse_udp {
        packet.extract(hdr.udp);
        transition accept;
    }
}

/*************************************************************************
*************  C H E C K S U M  V E R I F I C A T I O N  **************
*************************************************************************/

control MyVerifyChecksum(inout headers hdr, inout metadata meta) {
    apply {
    
    }
}

/*************************************************************************
****************** I N G R E S S   P R O C E S S I N G *******************
*************************************************************************/

control MyIngress(inout headers hdr,
                  inout metadata meta,
                  inout standard_metadata_t standard_metadata) {
   
    action forward(macAddr_t dstAddr, egressSpec_t port) {
        standard_metadata.egress_spec = port;
        hdr.ethernet.srcAddr = hdr.ethernet.dstAddr;
        hdr.ethernet.dstAddr = dstAddr;
    }

    action drop() {
        mark_to_drop(standard_metadata);
    }
    
    action noAction() {
        /* do nothing */
    }

    table ipv6_lpm {
        key = {
            hdr.ipv6.dstAddr:lpm;
        }
        actions = {
            forward;
            drop;
        }
        size = 1024;
        default_action = drop();
    }

    table tcp_filtering_1 {
        key = {
            hdr.tcp.srcPort:exact;
        }
        actions = {
            forward;
            drop;
        }
        size = 1024;
        default_action = drop();
    }
    
    table tcp_filtering_2 {
        key = {
            hdr.tcp.dstPort:exact;
        }
        actions = {
            forward;
            drop;
        }
        size = 1024;
        default_action = drop();
    }
    
    table udp_filtering_1 {
        key = {
            hdr.udp.srcPort:exact;
        }
        actions = {
            forward;
            drop;
        }
        size = 1024;
        default_action = drop();
    }
    
    table udp_filtering_2 {
        key = {
            hdr.udp.dstPort:exact;  // Changed from hdr.tcp.dstPort
        }
        actions = {
            forward;
            drop;
        }
        size = 1024;
        default_action = drop();
    }

    apply {
    
        if(hdr.ipv6.isValid()) {
            ipv6_lpm.apply();
        }
        if(hdr.tcp.isValid()){
            if(!tcp_filtering_1.apply().hit) {  // Changed from .miss
                tcp_filtering_2.apply();
            }
            else {
                meta.is_tcp_udp_filtered = 1;
            }
        }
        else if(hdr.udp.isValid()) {
            if(!udp_filtering_1.apply().hit) {  // Changed from .miss
                udp_filtering_2.apply();
            }
            else {
                meta.is_tcp_udp_filtered = 1;
            }
        }
    }
}


/*************************************************************************
****************** E G R E S S   P R O C E S S I N G *******************
*************************************************************************/

control MyEgress(inout headers hdr, inout metadata meta, inout standard_metadata_t standard_metadata) {
    apply {
        if (meta.is_tcp_udp_filtered == 1) {
            mark_to_drop(standard_metadata);
        }
    }
}

/*************************************************************************
************* C H E C K S U M   C O M P U T A T I O N   ******************
*************************************************************************/

control MyComputeChecksum(inout headers hdr, inout metadata meta) {
    apply {
    }
}

/*************************************************************************
*********************** D E P A R S E R *******************************
*************************************************************************/

control MyDeparser(packet_out packet, in headers hdr) {
    apply {
        packet.emit(hdr.ethernet);
        packet.emit(hdr.ipv6);
        packet.emit(hdr.tcp);
        packet.emit(hdr.udp);
    }
}

/*************************************************************************
**************************   S W I T C H   *******************************
*************************************************************************/

V1Switch(
    MyParser(),
    MyVerifyChecksum(),
    MyIngress(),
    MyEgress(),
    MyComputeChecksum(),
    MyDeparser()
) main;