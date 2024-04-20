#!/bin/bash


C4_DATA_SET="/home/ubuntu/c4-dataset/c4/en"
FILE_PATTERN="c4-train.001*-of-01024.json.gz"
cd $C4_DATA_SET
for dir in $C4_DATA_SET/$FILE_PATTERN; do
  #unzip and remove file *.gz
  #use gzip -d -k filename.gz to keep the *.gz files
  gzip -d $dir
  echo "unzip data from $dir success"
done
