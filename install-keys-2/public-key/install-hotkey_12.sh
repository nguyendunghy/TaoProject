#!/usr/bin/expect -f

cd ~/.bittensor/wallets/dungngoc
spawn btcli w regen_hotkey --mnemonic apple solution cancel home tomato inner heavy useful same able adapt field


expect {
    "Enter wallet*" {
        send "dungngoc\r"
    }
}

expect {
    "Enter hotkey*" {
        send "nannan_hotkey_12\r"
    }
}


interact