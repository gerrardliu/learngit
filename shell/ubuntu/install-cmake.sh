#!/bin/bash
wget https://github.com/Kitware/CMake/releases/download/v3.13.4/cmake-3.13.4.tar.gz
tar zxf cmake-3.13.4.tar.gz
cd cmake-3.13.4
./configure
make
sudo make install
