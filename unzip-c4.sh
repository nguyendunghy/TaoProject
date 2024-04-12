#!/bin/bash


C4_DATA_SET="/root/c4-dataset/c4/en"
cd $C4_DATA_SET
for dir in $C4_DATA_SET/*01024.json.gz; do
  #unzip and remove file *.gz
  #use gzip -d -k filename.gz to keep the *.gz files
  gzip -d $dir
  echo "unzip data from $dir success"
done
