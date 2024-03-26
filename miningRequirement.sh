#install bittensor
pip install --upgrade pip
pip3 install bittensor

#setup coldkey and hotkey
./install-keys/install-coldkey.sh
./install-keys/install-hotkey-all.sh

#install subtensor
#cd ..
#git clone https://github.com/opentensor/subtensor.git
#cd subtensor
#sudo ./scripts/run/subtensor.sh -e docker --network mainnet --node-type lite
#pwd

#install new driver
#cd ..
#sudo curl -O https://us.download.nvidia.com/XFree86/Linux-x86_64/550.54.14/NVIDIA-Linux-x86_64-550.54.14.run
#sudo chmod 777 NVIDIA-Linux-x86_64-550.54.14.run
#sudo ./NVIDIA-Linux-x86_64-550.54.14.run