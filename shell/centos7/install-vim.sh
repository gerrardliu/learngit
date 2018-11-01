#!/bin/bash
sudo yum install -y gcc gcc-c++ ncurses-devel python-devel
git clone https://github.com/vim/vim.git
cd vim
git pull
./configure --with-features=huge --enable-pythoninterp --with-python-config-dir=/usr/lib64/python2.7/config --enable-gui=gtk2 --enable-cscope --enable-luainterp --enable-multibyte \
&& make \
&& sudo make install \
&& sudo rm -rf vim
