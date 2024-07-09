#!/bin/bash
rm -f /etc/localtime
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#timedatectl set-timezone Asia/Shanghai

yum install -y ntpdate
/usr/sbin/ntpdate -u ntp.api.bz

#cn.pool.ntp.org  中国开源免费NTP服务器
#ntp1.aliyun.com 阿里云NTP服务器
#ntp2.aliyun.com 阿里云NTP服务器
#time1.aliyun.com 阿里云NTP服务器
#time2.aliyun.com 阿里云NTP服务器
