cd ~
git clone https://github.com/It-s-AI/llm-detection
cd llm-detection
python3 -m pip install -e .

#fluidstack
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2024 --subtensor.network finney --blacklist.force_validator_permit true >> hotkey22_output.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.network finney --blacklist.force_validator_permit true  >> hotkey23_output.log &

#vast.io 213.181.123.100:42556 -> 6006/tcp
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_24 --logging.debug --neuron.device cuda:0 --axon.port 6006  --axon.external_port 41014 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_24.log &
axon
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_25 --logging.debug --neuron.device cuda:0 --axon.port 7102  --axon.external_port 42983 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_25.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_26 --logging.debug --neuron.device cuda:0 --axon.port 6006  --axon.external_port 42556 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_26.log &

nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7100  --axon.external_port 23939 --subtensor.network finney --blacklist.force_validator_permit true >> jackie_hotkey_23.log &


nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 7101 --axon.external_port  44416 --subtensor.network finney   >> miner_jackie_hotkey_22.log &
nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7102 --axon.external_port  44316 --subtensor.network finney   >> miner_jackie_hotkey_23.log &


nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_21 --logging.debug --neuron.device cuda:0 --axon.port 7100 --subtensor.network finney  >> miner_hotkey_21.log &


#Test environment154.20.200.88:44571 -> 7100/tcp
nohup python3 neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 7100  --axon.external_port 44571 --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_22.log &

#Test on lastitude
nohup python3 neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 7100  --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_22.log &


#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_22 --logging.debug --neuron.device cuda:0 --axon.port 2023 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true >> hotkey22_output.log &

#nohup python3 neurons/miner.py --netuid 32 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 1992 --subtensor.chain_endpoint ws://3.144.223.133:9944 --blacklist.force_validator_permit true  >> hotkey23_output.log &
