#!/bin/bash
cd $3
echo "$(./getRegisterPrice.sh $1 default $2)" | ansi2txt