#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 90


#cd ~/.bittensor/wallets/default
spawn btcli w regen_coldkey --mnemonic fuel lizard wrong ribbon relax atom churn slide document knock shaft normal

expect {
    "Enter wallet name*" {
        send "subnet16_04\r"
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