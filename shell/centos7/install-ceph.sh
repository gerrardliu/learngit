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
ntpdate -u time1.aliyun.com

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

#pool
##create pool
ceph osd pool create pool1 32 32
##list pool
ceph osd lspools
ceph osd pool ls detail
##check pool stats
ceph osd pool stats pool1
##check pool configuration
ceph osd pool get pool1 all
ceph osd pool set pool1 size 2
##set pool application
ceph osd pool application enable pool1 rbd
##rename pool
ceph osd pool rename pool1 pool2
##set quota
ceph osd pool set-quota pool1 max-objects/max-bytes(1000/10240)
##delete pool
ceph osd pool delete pool1 pool1 --yes-i-really-really-mean-it

#pg
##check pg in a pool
ceph pg ls-by-pool pool1

#crash
##to solve '1 daemons have recently crashed'
ceph crash ls
ceph crash archive <CRASH ID>2024-07-09T09:13:54.619693Z_0736a026-2912-45e5-8d54-2d3017b82e90

#config
##to see global configs
ceph config dump
##allow mon to delete pool
ceph config get mon mon_allow_pool_delete
ceph config set mon mon_allow_pool_delete true

#osd
##osd list
ceph osd status
##osd summary
ceph osd stat
##relation between host, disk, osd
ceph osd tree

#mon
##epoch, mon list, address:port, leader
ceph mon stat

#auth
##list all users
ceph auth list
##create a user
ceph auth get-or-create client.user1 | tee /etc/ceph/ceph.client.user1.keyring
##get a user info
ceph auth get client.user1
##use user do something
ceph -s --id user1
##give r privilege
ceph auth caps client.user1 mon 'allow r'

#rados
rados -p pool1 ls
rados -p pool1 put 1/rbdmap rbdmap
rados -p pool1 rm 1/rbdmap

#rbd
##create a pool for rbd
ceph osd pool create pool1 16 16
#init pool application to rbd, equals to `ceph osd pool application enable pool1 rbd`
rbd pool init pool1
##create image
rbd create pool1/image1 --size=2G
##get image info
rbd info pool1/image1
##list images in a pool
rbd ls -p pool1
##create a user for rbd
ceph auth get-or-create client.rbd.user1 mon 'profile rbd' osd 'profile rbd pool=pool1' | tee /etc/ceph/ceph.client.rbd.user1.keyring

#use rbd image in client machine
##first install ceph-common
yum install -y python3
yum install -y yum-utils
yum install -y ceph-common
##then copy /etc/ceph/ceph.conf and /etc/ceph/ceph.client.rbd.user1.keyring to /etc/ceph/ on client machine
##rbd map, report error 'RBD image feature set mismatch. You can disable features unsupported by the kernel with "rbd feature disable pool1/image1 object-map fast-diff deep-flatten".'
rbd map pool1/image1 --id=rbd.user1
##disable some features
rbd feature disable pool1/image1 object-map
rbd feature disable pool1/image1 exclusive-lock
rbd feature disable pool1/image1 deep-flatten
##try again, and got output '/dev/rbd0'
rbd map pool1/image1 --id=rbd.user1
##format disk
mkfs -t xfs /dev/rbd0
##for auto map, vi /etc/ceph/rbdmap, add line 'pool1/image1    id=rbd.user1,keyring=/etc/ceph/ceph.client.rbd.user1.keyring'
vi /etc/ceph/rbdmap
##enable rbdmap daemon
systemctl start rbdmap.service
systemctl enable rbdmap.service
##create mount dir
mkdir /mnt/rbd0
##mount mannually
mount /dev/rbd0 /mnt/rbd0
##to know uuid, use blkid
blkid
##for auto mount, vi /etc/fstab, add line 'UUID=90acd48b-5cfd-4bca-a39d-bf6184042825 /mnt/rbd0 xfs defaults,_netdev 0 0'
