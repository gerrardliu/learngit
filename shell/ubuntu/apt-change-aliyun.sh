#!/bin/bash
VER=1604
mv /etc/apt/sources.list /etc/apt/sources.list.bak
cp sources.list/sources.list.$VER /etc/apt/sources.list
