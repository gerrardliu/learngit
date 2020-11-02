#!/bin/bash
VERSION=1.15.3
sudo yum install -y wget 
[ -f "go${VERSION}.linux-amd64.tar.gz" ] || wget https://dl.google.com/go/go${VERSION}.linux-amd64.tar.gz 
sudo tar -C /usr/local -xzf go${VERSION}.linux-amd64.tar.gz \
&& sudo sh -c 'echo "export PATH=\$PATH:/usr/local/go/bin" > /etc/profile.d/go.sh' \
&& source /etc/profile.d/go.sh \
&& rm -f go${VERSION}.linux-amd64.tar.gz
