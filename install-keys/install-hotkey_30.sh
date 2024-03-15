#!/usr/bin/expect -f

cd ~/.bittensor/wallets/default
spawn btcli w regen_hotkey --mnemonic demand arrow embark light upset fever wedding indoor mouse rate demand alarm
expect {
    "Enter wallet*" {
        send "default\r"
    }
}

expect {
    "Enter hotkey*" {
        send "jackie_hotkey_30\r"
    }
}


interact