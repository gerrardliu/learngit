#!/bin/bash

#1. down grade gcc5 to gcc4.8
apt-get install gcc-4.8
ls /usr/bin/gcc*
update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-4.8 100
update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-5 90
update-alternatives --config gcc
gcc -v
#2. Makefile -m64 -m32
vim arch/x86/vdso/Makefile
#3. !define(@val) to !@val
vim kernel/timeconst.pl +373
#4. comment 2nd *page
vim drivers/net/igbvf/igbvf.h +128
