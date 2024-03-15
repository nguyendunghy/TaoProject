#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 90


#cd ~/.bittensor/wallets/default
spawn btcli w regen_coldkey --mnemonic margin tragic zero reject upset dad heart sound advice century cool surface

expect {
    "Enter wallet name*" {
        send "default\r"
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