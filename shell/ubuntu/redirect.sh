#!/bin/bash
iptables -t nat -A PREROUTING -p tcp -s 10.0.0.20/32 -d 59.110.145.38/32 -j DNAT --to-destination 47.96.188.122:2333
