#!/usr/bin/expect -f

cd ~/.bittensor/wallets/default
spawn btcli w regen_hotkey --mnemonic blood assault combine delay shed pet tattoo faith custom frame slogan pond

expect {
    "Enter wallet*" {
        send "default\r"
    }
}

expect {
    "Enter hotkey*" {
        send "jackie_hotkey_22\r"
    }
}


interact