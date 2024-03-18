cd ~
git clone https://github.com/Supreme-Emperor-Wang/ImageAlchemy.git
#git clone git@github.com:Supreme-Emperor-Wang/ImageAlchemy.git
cd ~/ImageAlchemy
pip install -r requirements.txt

nohup python ~/ImageAlchemy/neurons/miners/StableMiner/main.py --wallet.name default --wallet.hotkey jackie_hotkey_22 --netuid 26 --subtensor.network local --axon.port 8102 --miner.device cuda:0 --blacklist.force_validator_permit true  >> hotkey22_output.log &

#in case mainnet failed
#nohup python ~/ImageAlchemy/neurons/miners/StableMiner/main.py --wallet.name default --wallet.hotkey jackie_hotkey_22 --netuid 26 --subtensor.chain_endpoint ws://3.144.223.133:9944 --axon.port 8102 --miner.device cuda:0 --blacklist.force_validator_permit true  >> hotkey22_output.log &



