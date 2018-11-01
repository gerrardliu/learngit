#!/bin/bash
git clone https://github.com/universal-ctags/ctags
cd ctags/
./autogen.sh
sudo yum install autoconf automake
./configure
make
sudo make install

ctags --output-format=e-ctags -R .
ctags -I __THROW -I __attribute_pure__ -I __nonnull -I __attribute__ --file-scope=yes --langmap=c:+.h --languages=c,c++ --links=yes --c-kinds=+p --c++-kinds=+p --fields=+iaS --extra=+q -R -f ~/.vim/systags /usr/include /usr/local/include

