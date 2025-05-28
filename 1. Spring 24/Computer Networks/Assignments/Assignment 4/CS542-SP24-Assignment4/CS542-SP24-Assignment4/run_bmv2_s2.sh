#!/bin/bash

p4c --target bmv2 --arch v1model --std p4-14 ~/assignment4.p4 -o ~

sudo simple_switch -i 0@enp10s0 -i 1@enp9s0 -i 2@enp7s0 -i 3@enp8s0 ~/assignment4.json --log-console