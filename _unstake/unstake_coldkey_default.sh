#!/bin/bash
for i in {1..30}
do
    ./_unstake/unstake_base.sh default "jackie_hotkey_$i"
done