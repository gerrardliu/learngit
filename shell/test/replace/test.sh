#!/bin/bash
cat 1.txt |while read LINE
do
     echo "${LINE//\"/\\\"}"
done
