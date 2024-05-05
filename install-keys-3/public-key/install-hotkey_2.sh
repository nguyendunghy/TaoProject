#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_02
spawn btcli w regen_hotkey --mnemonic twelve kitchen entry duty denial tornado clap fatal shield destroy green suffer


expect {
    "Enter wallet*" {
        send "subnet16_02\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_2\r"
    }
}


interact