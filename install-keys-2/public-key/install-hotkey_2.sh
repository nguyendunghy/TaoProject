#!/usr/bin/expect -f

cd ~/.bittensor/wallets/dungngoc
spawn btcli w regen_hotkey --mnemonic foam bronze final erase pet host fall sword catch follow reveal that


expect {
    "Enter wallet*" {
        send "dungngoc\r"
    }
}

expect {
    "Enter hotkey*" {
        send "nannan_hotkey_2\r"
    }
}


interact