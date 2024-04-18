mvn clean
mvn package
mkdir logs
java -jar target/TaoProject-1.0-SNAPSHOT.jar BACKUP_SCRIPT ./src/main/resources/application.property  "nohup python3 /home/ubuntu/head-tail-llm-detection/neurons/miner.py --netuid 87 --wallet.name default --wallet.hotkey jackie_hotkey_23 --logging.debug --neuron.device cuda:0 --axon.port 7101  --subtensor.network test --blacklist.minimum_stake_requirement 0 >> miner_hotkey_23.log &"
