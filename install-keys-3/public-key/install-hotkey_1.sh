#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_01
spawn btcli w regen_hotkey --mnemonic isolate deer hover blame swim endorse space gate congress olympic ribbon they


expect {
    "Enter wallet*" {
        send "subnet16_01\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_1\r"
    }
}


interact