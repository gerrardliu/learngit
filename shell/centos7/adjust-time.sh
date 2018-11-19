#!/bin/bash
rm -f /etc/localtime
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
yum install -y ntpdate
/usr/sbin/ntpdate -u ntp.api.bz
