#!/usr/bin/expect -f

set timeout 60

cd ~/.bittensor/wallets/default
spawn btcli subnet register --netuid [lindex $argv 0] --wallet.name [lindex $argv 1] --wallet.hotkey [lindex $argv 2]
expect {
    "Enter subtensor network*" {
        send "finney\r"
    }
}

expect {
    "Do*" {
        send "n\r"
    }
     timeout {
        send_user "Expected pattern not found within timeout period.\n"
        exit 1  
    }
}


interact

