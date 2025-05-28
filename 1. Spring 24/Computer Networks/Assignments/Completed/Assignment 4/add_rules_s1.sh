#!/bin/bash

simple_switch_CLI << _EOF_
table_add ipv6_lpm forward 2001:db8:2cc9:223::/64 => 24:29:08:4f:d3:ed 0
table_add ipv6_lpm forward 2001:db8:6e5c:a556::a493:e71e/128 => 60:29:c3:91:36:ba 1
table_add ipv6_lpm forward 2001:db8:6e5c:a556::2f3b:e61/128 => c4:2c:64:6d:c2:cb 2
table_add ipv6_lpm forward 2001:db8:6e5c:a556::244:9c5/128 => 20:27:e1:b9:ae:e1 3

# Allow TCP ports 80 and 22, drop port 25
table_add tcp_filtering_1 forward 80 => 1
table_add tcp_filtering_2 forward 80 => 1
table_add tcp_filtering_1 forward 22 => 2
table_add tcp_filtering_2 forward 22 => 2
table_add tcp_filtering_1 drop 25 => 
table_add tcp_filtering_2 drop 25 => 

# Allow UDP ports 53 and 67, drop port 69
table_add udp_filtering_1 forward 53 => 1
table_add udp_filtering_2 forward 53 => 1
table_add udp_filtering_1 forward 67 => 2
table_add udp_filtering_2 forward 67 => 2
table_add udp_filtering_1 drop 69 => 
table_add udp_filtering_2 drop 69 => 

_EOF_
