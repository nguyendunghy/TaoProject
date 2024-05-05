#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 90


#cd ~/.bittensor/wallets/default
spawn btcli w regen_coldkey --mnemonic dog deputy sunset bone report few bulb neither gesture act poet brush

expect {
    "Enter wallet name*" {
        send "subnet16_05\r"
    }
}

expect {
    "Specify password*" {
        send "$password\r"
    }
}

expect {
    "Retype your password*" {
        send "$password\r"
    }
}


interact