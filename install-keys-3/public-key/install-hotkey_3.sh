#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_03
spawn btcli w regen_hotkey --mnemonic iron sleep isolate census debate snack genius blush tornado owner giant day


expect {
    "Enter wallet*" {
        send "subnet16_03\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_3\r"
    }
}


interact