#!/bin/bash

simple_switch_CLI << _EOF_

table_add ipv6_lpm forward 2001:db8:6e5c:a556::/64 => a0:33:3d:ea:39:29 0
table_add ipv6_lpm forward 2001:db8:2cc9:223::ed8d:8bdb/128 => 2c:34:6e:b9:54:54 1
table_add ipv6_lpm forward 2001:db8:2cc9:223::9407:2950/128 => 14:a5:4c:41:27:9c 2

_EOF_
