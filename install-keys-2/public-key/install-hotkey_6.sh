#!/usr/bin/expect -f

cd ~/.bittensor/wallets/dungngoc
spawn btcli w regen_hotkey --mnemonic clown genuine school buzz artefact life inhale ramp much logic glass divert


expect {
    "Enter wallet*" {
        send "dungngoc\r"
    }
}

expect {
    "Enter hotkey*" {
        send "nannan_hotkey_6\r"
    }
}


interact