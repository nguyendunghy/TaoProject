#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 90


#cd ~/.bittensor/wallets/default
spawn btcli w regen_coldkey --mnemonic ugly state exact father pattern child warm rubber grid tumble excuse general

expect {
    "Enter wallet name*" {
        send "subnet16_02\r"
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