#iptables -t nat -A PREROUTING -p tcp -s 192.168.1.102/32 -d 59.110.145.38/32 -j DNAT --to-destination 47.96.188.122:2333
iptables -nv -L --line-number -t nat
#iptables -F -t nat
#ebtables -t broute -A BROUTING -p IPv4 --ip-destination 59.110.145.38 -j DROP
#ebtables -t broute -F
