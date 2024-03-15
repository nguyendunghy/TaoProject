#!/usr/bin/expect -f

cd ~/.bittensor/wallets/default
spawn btcli w regen_hotkey --mnemonic figure october property gold talent stable miss brain economy print error field

expect {
    "Enter wallet*" {
        send "default\r"
    }
}

expect {
    "Enter hotkey*" {
        send "jackie_hotkey_24\r"
    }
}


interact