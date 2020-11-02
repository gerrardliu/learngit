#!/bin/bash
sudo yum install -y gcc gcc-c++ ncurses-devel python-devel
git clone https://github.com/vim/vim.git
cd vim
git pull
#./configure --with-features=huge --enable-pythoninterp=yes --with-python-config-dir=/usr/lib64/python2.7/config --enable-gui=gtk2 --enable-cscope --enable-luainterp --enable-multibyte \
./configure --with-features=huge --enable-python3interp=yes --with-python3-config-dir=/usr/local/lib/python3.6/config-3.6m-x86_64-linux-gnu --enable-gui=gtk2 --enable-cscope --enable-luainterp --enable-multibyte \
&& make \
&& sudo make install \
&& sudo rm -rf vim
