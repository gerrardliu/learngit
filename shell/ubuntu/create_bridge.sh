apt -y install bridge-utils
brctl addbr br0
brctl addif br0 enx000ec6ac5bec
brctl addif br0 eno1
ifconfig eno1 0 up
ifconfig enx000ec6ac5bec up
ifconfig br0 up
modprobe br_netfilter
echo 1 > /proc/sys/net/ipv4/ip_forward
cat /proc/sys/net/bridge/bridge-nf-call-iptables
