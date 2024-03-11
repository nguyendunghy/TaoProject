#!/usr/bin/expect -f

set password "Iltmt@e1"
set timeout 60
set REGISTER_PRICE_THRESHOLD [lindex $argv 1]
set pattern {[-+]?[0-9]*\.?[0-9]+}


cd ~/.bittensor/wallets/default
spawn btcli subnet register --netuid [lindex $argv 0] --wallet.name [lindex $argv 2] --wallet.hotkey [lindex $argv 3]
expect {
    "Enter subtensor network*" {
        send "finney\r"
    }
}

expect {
    "Do*" {
        send "y\r"
    }
     timeout {
        send_user "Expected pattern not found within timeout period.\n"
        exit 1  
    }
}


expect {
    "Enter password*" {
        send "$password\r"
    }
     timeout {
        send_user "Expected password not found within timeout period.\n"
        exit 1  
    }
}

expect {
    "Recycle*" {
        send "y\r"
    }
     timeout {
        send_user "Expected recycle not found within timeout period.\n"
        exit 1  
    }
}

interact