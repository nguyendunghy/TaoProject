#!/usr/bin/expect -f

set password "xuanhuy@123"
set timeout 180
set REGISTER_PRICE_THRESHOLD [lindex $argv 1]


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
    -re {Recycle τ([0-9.]+) to register on subnet:([0-9]+)\?} {
            set amount $expect_out(1,string)
            set amountFloat [expr {double($amount)}]
            if {$amountFloat > $REGISTER_PRICE_THRESHOLD} {
                send "n\r"
            } else {
                send "y\r"
            }
    }

     timeout {
        send_user "Expected recycle not found within timeout period.\n"
        exit 1  
    }
}

interact