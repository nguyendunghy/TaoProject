#!/usr/bin/expect -f

cd ~/.bittensor/wallets/default
spawn btcli w regen_hotkey --mnemonic collect three need tower time hospital yellow expand fiction little flower stumble

expect {
    "Enter wallet*" {
        send "default\r"
    }
}

expect {
    "Enter hotkey*" {
        send "jackie_hotkey_25\r"
    }
}


interact