#!/bin/bash

subnetId=$1
threshold=$2
hotkey=$3
coldkey=$4
password=$5
cd $5
./autoRegisterScript.sh $subnetId $threshold $coldkey $hotkey $password