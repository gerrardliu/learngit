#!/bin/bash
rm -f /etc/localtime
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
yum install -y ntpdate
ntpdate -u ntp.api.bz
