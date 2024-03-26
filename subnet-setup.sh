cd ~
git clone https://github.com/It-s-AI/llm-detection
cd llm-detection
python3 -m pip install -e .

#fluidstack
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2023 --subtensor.network finney --blacklist.force_validator_permit true >> hotkey22_output.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.network finney --blacklist.force_validator_permit true  >> hotkey23_output.log &


#vast.io
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_24 --logging.debug --neuron.device cuda:0 --axon.port 6006  --axon.external_port 41014 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_24.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_25 --logging.debug --neuron.device cuda:0 --axon.port 8888  --axon.external_port 41755 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_25.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_26 --logging.debug --neuron.device cuda:0 --axon.port 6006  --axon.external_port 42116 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_26.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7100  --axon.external_port 42659 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_23.log &

#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2023 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true >> hotkey22_output.log &

#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true  >> hotkey23_output.log &
