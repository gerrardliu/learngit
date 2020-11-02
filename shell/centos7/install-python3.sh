#!/bin/bash

yum -y install openssl-devel bzip2-devel expat-devel gdbm-devel readline-devel sqlite-devel gcc

yum install wget
mkdir -p /tools
cd tools
wget  https://www.python.org/ftp/python/3.6.0/Python-3.6.0.tgz
tar -xzvf Python-3.6.0.tgz
cd Python-3.6.0

./configure --prefix=/usr/local
make && make install

ln -s /usr/local/bin/python3.6 /usr/bin/python3
