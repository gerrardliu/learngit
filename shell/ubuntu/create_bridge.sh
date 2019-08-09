apt -y install bridge-utils
brctl addbr br0
brctl addif br0 eno1
brctl addif br0 enx000ec6ac5bec
ifconfig eno1 0.0.0.0 up
ifconfig enx000ec6ac5bec up
ifconfig br0 up
modprobe br_netfilter

