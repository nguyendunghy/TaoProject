#!/bin/bash

cd /Users/nannan/IdeaProjects/TaoProject/src/main/resources
echo "$(./getRegisterPrice.sh $1 default $2)" | ansi2txt