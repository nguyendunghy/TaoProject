cd ~
git clone https://github.com/Supreme-Emperor-Wang/ImageAlchemy.git
#git clone git@github.com:Supreme-Emperor-Wang/ImageAlchemy.git
cd ~/ImageAlchemy
pip install -r requirements.txt

nohup python ~/ImageAlchemy/neurons/miners/StableMiner/main.py --wallet.name default --wallet.hotkey jackie_hotkey_24 --netuid 26 --subtensor.network local --axon.port 8102 --miner.device cuda:0 --blacklist.force_validator_permit true  >> hotkey24_output.log &


