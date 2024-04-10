#!/bin/bash


GZ_DIR="/home/ubuntu/c4-dataset/indexed_data/c4-index-v1"

# Loop through each CSV file in the directory
for dir in $GZ_DIR/*; do
  # Extract the table name from the file name (removing path and .csv extension)
  dir_name=$(basename "$dir")
  cd $dir
  paste -d',' *flush* > merged_$dir_name.txt

  echo "Data flush from $dir merged to merged_$dir_name.txt"
done
