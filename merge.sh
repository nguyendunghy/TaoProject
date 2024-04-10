#!/bin/bash


C4_INDEX="/home/ubuntu/c4-dataset/c4-index-v1"

# Loop through each CSV file in the directory
for dir in C4_INDEX/*; do
  # Extract the table name from the file name (removing path and .csv extension)
  dir_name=$(basename "$dir")
  cd $dir
  paste -d',' *flush* > merged_$dir_name.txt
  rm *flush*.txt
  echo "Data flush from $dir merged to merged_$dir_name.txt"
done
