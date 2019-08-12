#!/bin/bash
apt-get update
apt-get install -y build-essential cmake git

#
# install libevent
#
mkdir -p /root/source && cd /root/source
wget https://github.com/libevent/libevent/releases/download/release-2.1.9-beta/libevent-2.1.9-beta.tar.gz
tar zxvf libevent-2.1.9-beta.tar.gz
cd libevent-2.1.9-beta
./configure
make
make install

#
# install glog
#
mkdir -p /root/source && cd /root/source
wget https://github.com/google/glog/archive/v0.3.5.tar.gz
tar zxvf v0.3.5.tar.gz
cd glog-0.3.5
./configure && make && make install

#
# build agent
#
mkdir -p /work && cd /work
git clone https://github.com/btccom/btcagent.git
cd btcagent
mkdir -p build && cd build
cmake -DCMAKE_BUILD_TYPE=Release ..
make
cp ../src/agent/agent_conf.json .
mkdir -p log_btcagent
