sudo yum check-update
curl -fsSL SL https://get.docker.com/ | / | sh
sudo systemctl start docker
sudo systemctl status docker
sudo systemctl enable docker
sudo usermod -aG docker $(whoami)
