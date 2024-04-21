#!/usr/bin/expect -f

cd ~/.bittensor/wallets/dungngoc
spawn btcli w regen_hotkey --mnemonic attend envelope ready inform often smooth achieve farm sudden leave tumble robust


expect {
    "Enter wallet*" {
        send "dungngoc\r"
    }
}

expect {
    "Enter hotkey*" {
        send "nannan_hotkey_1\r"
    }
}


interact