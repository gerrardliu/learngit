cd /etc/sysconfig/network-scripts/
vi ifcfg-ens33
service network restart

curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum clean all
yum makecache

yum install -y openssh-server
systemctl start sshd
systemctl enable sshd

yum install net-tools

hostnamectl set-hostname ceph1
hostnamectl status

setenforce 0
vi /etc/selinux/config
systemctl disable --now firewalld

ssh-keygen -f /root/.ssh/id_rsa -P ''
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.11
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.12
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.13

vi hosts
scp hosts root@192.168.78.12:/etc/
scp hosts root@192.168.78.13:/etc/

timedatectl set-timezone Asia/Shanghai
timedatectl status

yum install -y yum-utils
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum install -y docker-ce
systemctl start docker
systemctl enable docker

yum install -y python3

curl --silent --remote-name --location https://mirrors.chenby.cn/https://github.com/ceph/ceph/raw/octopus/src/cephadm/cephadm
chmod 755 cephadm
./cephadm add-repo --release octopus
./cephadm install

cephadm bootstrap --mon-ip 192.168.78.11
#remember initial password

cephadm shell

#https://ceph1:8443 admin / initial password / qwer@1234

cephadm install ceph-common

ceph -v
ceph -s

ssh-copy-id -f -i /etc/ceph/ceph.pub root@ceph2
ssh-copy-id -f -i /etc/ceph/ceph.pub root@ceph3

ceph orch host add ceph2
ceph orch host add ceph3
ceph orch host ls

ceph orch apply mon --placement="3 ceph1 ceph2 ceph3"
ceph orch apply mgr --placement="3 ceph1 ceph2 ceph3"

ceph orch daemon add osd ceph1:/dev/sdb
ceph orch device ls
ceph orch daemon add osd ceph1:/dev/sdc



