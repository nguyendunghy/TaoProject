"git clone https://github.com/fractal-net/fractal.git
cd fractal"
python3 -m venv venv
source venv/bin/activate
"pip install -e .
pip install -r requirements.txt"
pm2 start python3 -n server -- model/server.py
pm2 start python3 -n dunghk2 -- neurons/prover/app.py --subtensor.network finney --neuron.model_endpoint "http://127.0.0.1:5005" --logging.debug --logging.trace --netuid 29 --axon.port 40353 --axon.external_port 40353 --wallet.name dungck1 --wallet.hotkey dunghk2

pm2 start python3 -n jackie -- neurons/prover/app.py --subtensor.network finney --neuron.model_endpoint "http://127.0.0.1:5005" --logging.debug --logging.trace --netuid 29 --axon.port 13609 --axon.external_port 13609 --wallet.name default --wallet.hotkey jackie_hotkey_25



#in case mainnet failed
#nohup python ~/ImageAlchemy/neurons/miners/StableMiner/main.py --wallet.name default --wallet.hotkey jackie_hotkey_23 --netuid 26 --subtensor.chain_endpoint ws://3.144.223.133:9944 --axon.port 8102 --miner.device cuda:0 --blacklist.force_validator_permit true  >> hotkey23_output.log &



