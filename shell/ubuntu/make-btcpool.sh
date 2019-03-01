#use ubuntu16.04

CPUS=`lscpu | grep '^CPU(s):' | awk '{print $2}'`

apt-get update
apt-get install -y build-essential autotools-dev libtool autoconf automake pkg-config cmake \
                   openssl libssl-dev libcurl4-openssl-dev libconfig++-dev \
                   libboost-all-dev libgmp-dev libmysqlclient-dev libzookeeper-mt-dev \
                   libzmq3-dev libgoogle-glog-dev libhiredis-dev zlib1g zlib1g-dev \
                   libprotobuf-dev protobuf-compiler

# zmq-v4.1.5
mkdir -p /root/source && cd /root/source
wget https://github.com/zeromq/zeromq4-1/releases/download/v4.1.5/zeromq-4.1.5.tar.gz
tar zxvf zeromq-4.1.5.tar.gz
cd zeromq-4.1.5
./autogen.sh && ./configure && make -j $CPUS
make check && make install && ldconfig

# glog-v0.3.4
mkdir -p /root/source && cd /root/source
wget https://github.com/google/glog/archive/v0.3.4.tar.gz
tar zxvf v0.3.4.tar.gz
cd glog-0.3.4
./configure && make -j $CPUS && make install

# librdkafka-v0.9.1
mkdir -p /root/source && cd /root/source
wget https://github.com/edenhill/librdkafka/archive/0.9.1.tar.gz
tar zxvf 0.9.1.tar.gz
cd librdkafka-0.9.1
./configure && make -j $CPUS && make install
rm -v /usr/local/lib/librdkafka*.so /usr/local/lib/librdkafka*.so.*

# libevent-2.0.22-stable
#mkdir -p /root/source && cd /root/source
#wget https://github.com/libevent/libevent/releases/download/release-2.0.22-stable/libevent-2.0.22-stable.tar.gz
#tar zxvf libevent-2.0.22-stable.tar.gz
#cd libevent-2.0.22-stable
#./configure
#make -j $CPUS
#make install

#libevent master work in ubuntu18.04
mkdir -p /root/source && cd /root/source
git clone https://github.com/libevent/libevent.git
cd libevent
./autogen.sh
./configure --disable-shared
make && make install

#bitcoin
mkdir -p /work
cd /work
wget -O bitcoin-0.16.0.tar.gz https://github.com/bitcoin/bitcoin/archive/v0.16.0.tar.gz
tar zxf bitcoin-0.16.0.tar.gz

#btcpool
git clone https://github.com/btccom/btcpool.git
cd btcpool
mkdir build
cd build
#cmake -DCHAIN_TYPE=BTC -DCHAIN_SRC_ROOT=/work/bitcoin-0.16.0 ..
cmake -DCMAKE_BUILD_TYPE=Debug -DCHAIN_TYPE=BTC -DCHAIN_SRC_ROOT=/work/bitcoin-0.16.0 ..
make
