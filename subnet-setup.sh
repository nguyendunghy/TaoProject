cd ~
git clone https://github.com/It-s-AI/llm-detection
cd llm-detection
python3 -m pip install -e .

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2023 --subtensor.network local --blacklist.force_validator_permit true >> hotkey22_output.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.network local --blacklist.force_validator_permit true  >> hotkey23_output.log &


nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_24 --logging.debug --neuron.device cuda:0 --axon.port 6006  --axon.external_port 41014 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_24.log &


nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_25 --logging.debug --neuron.device cuda:0 --axon.port 8080  --axon.external_port 41365 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_25.log &


#mainnet fail

#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2023 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true >> hotkey22_output.log &

#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true  >> hotkey23_output.log &
