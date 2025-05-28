# Assignment 4

For this assignment, you will design P4 code and corresponding table entries to enable programmable switches to function is IPv6 routers and firewalls according to certain specifications. Your specifications can be found at http://caper.cs.iit.edu/S24CS542/asgn4/<username>_assignment4.txt, for example, if your student id was "jdoe", you'd go to http://caper.cs.iit.edu/S24CS542/asgn4/jdoe_assignment4.txt.

Contained in this repository is a python notebook (.ipynb) and supporting files. This lab will be run on FABRIC, and this entire repository with your .txt specification file should be uploaded to its own directory on FABRIC. The notebook will handle building the topology - your responsibility will be just the P4 code and control plane commands. The python notebook will require a single modification - setting student_id to your id (for example, id jdoe sets student_id="jdoe").

For the P4 code, you are provided the P4 program built in the class tutorial as starter code in assignment4.p4. This is an example of a working IPv4 router - your job is to modify it to fulfil two requirements:
- It can route IPv6
- It can filter TCP/UDP packets if they do not have a specific port

The notebook shows the topology - it is two subnets, each with three hosts connected to a switch, and the switches connect to each other. The hosts within a subnet share a 64-bit prefix.
The TCP/UDP filtering means that when the switch receives a TCP/UDP packet, if neither the source or destination port match one of multiple allowed ports, the packet is dropped. Otherwise, it is let through.

With these clarifications, more specific requirements for the P4 program are:
- It can route IPv6 to a neighboring 64-bit prefix subnet or to directly connects hosts with unique IPs. It should not route to exact IPs on the other subnet.
- It drops TCP/UDP packets which do not have a specific port as either source or destination port

This describes P4 code, which is general - it can apply to any similar topology with the same requirements. To taylor the solution to your specifications, you need to add table entries to the switches. There are two scripts, add_rules_s1.sh and add_rules_s2.sh, which you should fill out with the necessary table_add commands so that the switches, running your P4 code, can route between the hosts and filter TCP/UDP packets according to your specifications file. The extraction of the specifications occurs in the second cell of the python notebook, and each specification is clearly labeled.

For your grade, your P4 code and control plane commands will be subjected to three tests.
1. Each host h11 to h22 will ping each host. You receive +0.5pts for each host that can successfully ping every other host.
2. Three TCP and three UDP packets will be sent from a random subnet 1 host to a random subnet 2 host. Two of each are valid, one of each should be dropped. You receive 2.5pts for correctly filtering TCP and another 2.5 for correctly filtering UDP.
3. A new host, h23, will be added to subnet 3, and s2 will be provided correct P4 code and table entries. It will then attempt to ping all other hosts. This tests that your switch configuration, running on s1, correctly routes traffic from subnet 1 to subnet 2, not relying on exact IP match to reach subnet 2 hosts. +5pts if successful.

You can run the autograder yourself, so you should know the grade when you submit. Submission on Blackboard should be a .zip file containing your specification .txt file, your .p4 code, and your two add_rules_s1.sh and add_rules_s2.sh scripts.
