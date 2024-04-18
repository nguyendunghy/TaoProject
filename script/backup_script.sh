mvn clean
mvn package
mkdir logs
source /home/ubuntu/venv/head-tail/bin/activate
nohup python3 /home/ubuntu/head-tail-llm-detection/neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7100 --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_23.log &