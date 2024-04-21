#!/usr/bin/expect -f

cd ~/.bittensor/wallets/dungngoc
spawn btcli w regen_hotkey --mnemonic soda deny misery ticket keen illegal cat clock leave capital cabin parade


expect {
    "Enter wallet*" {
        send "dungngoc\r"
    }
}

expect {
    "Enter hotkey*" {
        send "nannan_hotkey_7\r"
    }
}


interact