#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_04
spawn btcli w regen_hotkey --mnemonic witness carbon laundry ritual food reduce tag human relief whale chat danger

expect {
    "Enter wallet*" {
        send "subnet16_04\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_4\r"
    }
}


interact