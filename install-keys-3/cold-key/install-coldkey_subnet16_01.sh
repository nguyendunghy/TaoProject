#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 90


#cd ~/.bittensor/wallets/default
spawn btcli w regen_coldkey --mnemonic brave village girl sell switch rough all walk pencil speed yard switch

expect {
    "Enter wallet name*" {
        send "subnet16_01\r"
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