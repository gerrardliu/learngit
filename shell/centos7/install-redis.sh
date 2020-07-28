#!/bin/bash
REDIS=redis-5.0.5
yum install -y gcc-c++ wget
wget http://download.redis.io/releases/$REDIS.tar.gz
tar zxf $REDIS.tar.gz
cd $REDIS
make
#make install
