#!/usr/bin/expect -f

set timeout 60
set wallet_path ~/.bittensor/wallets/
set wallet_name [lindex $argv 0]
set hotkey [lindex $argv 1]
set password [lindex $argv 2]

spawn btcli stake remove
expect {
    "Enter wallet name *" {
        send "$wallet_name\r"
    }
}

expect {
    "Enter hotkey name *" {
        send "$hotkey\r"
    }
}


expect {
    "Unstake all Tao *" {
        send "y\r"
    }
}

expect {
    "Do you want to unstake from the following keys *" {
        send "y\r"
    }
}

expect {
    "Enter password *" {
        send "$password\r"
    }
}

expect {
    "Do you want *" {
        send "y\r"
    }
}

interact