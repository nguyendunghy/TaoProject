#!/usr/bin/expect -f

cd ~/.bittensor/wallets/subnet16_06
spawn btcli w regen_hotkey --mnemonic tray dragon gift thank stomach skirt prosper kangaroo two awful brick orange


expect {
    "Enter wallet*" {
        send "subnet16_06\r"
    }
}

expect {
    "Enter hotkey*" {
        send "subnet16_hotkey_6\r"
    }
}


interact