echo "start running python app"
source /home/ubuntu/venv/head-tail/bin/activate
cd /home/ubuntu/head-tail-llm-detection
nohup python3 /home/ubuntu/head-tail-llm-detection/neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_1 --logging.debug --neuron.device cuda:0 --axon.port 7101 --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_1.log &