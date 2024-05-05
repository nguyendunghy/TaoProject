#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_05
spawn btcli w regen_hotkey --mnemonic rice sudden display history chunk idle exact conduct train image announce come


expect {
    "Enter wallet*" {
        send "subnet16_05\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_5\r"
    }
}


interact