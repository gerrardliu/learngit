#!/bin/bash
make mrproper
make clean
make menuconfig #-> exit -> save
make -j4
make modules_install
make install
#vim /etc/default/grub # comment GRUB_HIDDEN_TIMEOUT=0
update-grub
update-grub2
#restart
