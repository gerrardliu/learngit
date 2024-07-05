yum install -y yum-utils
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

yum list docker-ce.x86_64 --showduplicates | sort -r
yum install -y docker-ce-23.0.6-1.el8

systemctl enable docker-ce
systemctl start docker-ce
