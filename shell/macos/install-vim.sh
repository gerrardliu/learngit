git clone https://github.com/vim/vim.git
cd vim
git pull
./configure --with-features=huge --enable-pythoninterp --with-python-config-dir=/usr/lib/python2.7/config --enable-gui=gtk2 --enable-cscope --enable-luainterp --enable-multibyte
make
sudo make install
#add alias to follow file
#~/.bash_profile
alias vim='/usr/local/bin/vim'
git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
brew install cmake
~/.vim/bundle/YouCompleteMe/install.py --clang-completer --go-completer
#in vim:
#PluginInstall
#GoInstallBinaries
