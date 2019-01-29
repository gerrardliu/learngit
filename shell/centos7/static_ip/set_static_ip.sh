#!/bin/bash
cp ifcfg-ens33 /etc/sysconfig/network-scripts/
systemctl restart network
systemctl disable firewalld
