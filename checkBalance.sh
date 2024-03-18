#!/usr/bin/expect -f

set timeout 60
set wallet_path ~/.bittensor/wallets/

spawn btcli wallet balance
expect {
    "Enter wallets path *" {
        send "$wallet_path\r"
    }
}

expect {
    "Enter wallet name *" {
        send "default\r"
    }
}

expect {
    "Enter network *" {
        send "local\r"
    }
}

interact