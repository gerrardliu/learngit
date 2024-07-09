# set static ip
cd /etc/sysconfig/network-scripts/
vi ifcfg-ens33
service network restart

# change yum source
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum clean all
yum makecache

#install sshd
yum install -y openssh-server
systemctl start sshd
systemctl enable sshd

#install tools, ifconfig
yum install net-tools

#set hostname on every node
hostnamectl set-hostname ceph1
hostnamectl status

#disable fireware
setenforce 0
vi /etc/selinux/config
systemctl disable --now firewalld

#set ssh password free
ssh-keygen -f /root/.ssh/id_rsa -P ''
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.11
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.12
ssh-copy-id -o StrictHostKeyChecking=no 192.168.78.13

#set /etc/hosts
vi hosts
scp hosts root@192.168.78.12:/etc/
scp hosts root@192.168.78.13:/etc/

#time sync
timedatectl set-timezone Asia/Shanghai
timedatectl status
yum install -y ntpdate
ntpdate -u ntp1.aliyun.com

#install docker
yum install -y yum-utils
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum install -y docker-ce
systemctl start docker
systemctl enable docker

#install python
yum install -y python3

#install cephadm
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

#add host
ceph orch host add ceph2
ceph orch host add ceph3
ceph orch host ls

#add mon
ceph orch apply mon --placement="3 ceph1 ceph2 ceph3"

#add mgr
ceph orch apply mgr --placement="3 ceph1 ceph2 ceph3"

#add osd
ceph orch daemon add osd ceph1:/dev/sdb
ceph orch device ls
ceph orch daemon add osd ceph1:/dev/sdc

#create rgw
radosgw-admin realm create --rgw-realm=realm1 --default
radosgw-admin zonegroup create --rgw-zonegroup=zonegroup1 --master --default
radosgw-admin zone create --rgw-zonegroup=zonegroup1 --rgw-zone=zone1 --master --default
ceph orch apply rgw realm1 zone1 --placement="3 ceph1 ceph2 ceph3"

#check logs
ceph orch ps --daemon-type rgw
cephadm logs -n rgw.realm1.zone1.ceph1.mklbhf

#create rgw user
radosgw-admin user create --uid=dashboard --display-name=dashboard --system
radosgw-admin user info --uid=dashboard | grep secret_key

#enable rgw object getate functions in dashboard
echo PXU8AUKZA6P4UCFL3W1V >  /root/rgw-dashboard-access-key
echo llwxd5oumcNZT3B0eObXBYXMiZsIuhtJRiYUcO4X > /root/rgw-dashboard-secret-key
ceph dashboard set-rgw-api-access-key -i /root/rgw-dashboard-access-key
ceph dashboard set-rgw-api-secret-key -i /root/rgw-dashboard-secret-key

#install s3cmd
yum -y install epel-release
yum -y install s3cmd
#put file, with public acl, http://192.168.78.11/bucket1/file.txt
s3cmd put --acl-public file.txt s3://bucket1/
#set acl public
s3cmd setacl s3://bucket1/VMwareTools-10.3.23-16594550.tar.gz --acl-public --recursive

#install cephadm in every node
scp cephadm root@ceph2:/root
##in ceph2
./cephadm add-repo --release octopus
##replace aliyun source
##vi /etc/yum.repo.d/ceph.repo
##%s/download.ceph.com/mirrors.aliyun.com\/ceph/g
./cephadm install
cephadm install ceph-common
##after ceph2 finish
scp /etc/ceph/ceph.conf root@ceph2:/etc/ceph
scp /etc/ceph/ceph.client.admin.keyring root@ceph2:/etc/ceph
