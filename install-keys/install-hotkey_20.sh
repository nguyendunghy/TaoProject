#!/usr/bin/expect -f

cd ~/.bittensor/wallets/default
spawn btcli w regen_hotkey --mnemonic sense assist wrestle enlist rotate blame receive horse forum citizen silk push
expect {
    "Enter wallet*" {
        send "default\r"
    }
}

expect {
    "Enter hotkey*" {
        send "jackie_hotkey_20\r"
    }
}


interact