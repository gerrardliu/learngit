#!/bin/bash
docker run -d -p 8080:8080 oddrationale/docker-shadowsocks -s 0.0.0.0 -p 8080 -k 123abc -m aes-256-cfb
