#!/bin/bash
sudo yum install -y wget 
[ -f "go1.10.3.linux-amd64.tar.gz" ] || wget https://dl.google.com/go/go1.10.3.linux-amd64.tar.gz 
sudo tar -C /usr/local -xzf go1.10.3.linux-amd64.tar.gz \
&& sudo sh -c 'echo "export PATH=\$PATH:/usr/local/go/bin" > /etc/profile.d/go.sh' \
&& source /etc/profile.d/go.sh \
&& rm -f go1.10.3.linux-amd64.tar.gz
