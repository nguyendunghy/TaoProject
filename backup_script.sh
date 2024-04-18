mvn clean
mvn package
mkdir logs
source /home/ubuntu/venv/head-tail/bin/activate
cd /home/ubuntu/head-tail-llm-detection
#java -jar /home/ubuntu/TaoProject/target/TaoProject-1.0-SNAPSHOT.jar BACKUP_SCRIPT /home/ubuntu/TaoProject/src/main/resources/application.property  "nohup python3 /home/ubuntu/head-tail-llm-detection/neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7101  --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_23.log &" >> backup_scrip.log &
nohup python3 /home/ubuntu/head-tail-llm-detection/neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7101  --subtensor.network test --blacklist.minimum_stake_requirement 0 >> /home/ubuntu/head-tail-llm-detection/miner_hotkey_23.log &
