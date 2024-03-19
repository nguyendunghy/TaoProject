#instruction: https://docs.google.com/document/d/1FzZe5EskEV0Q6BkOuVkqCrIEexzQZd_134eT74B7hPA/edit

#install bittensor
pip install --upgrade pip
pip3 install bittensor

#setup coldkey and hotkey
./install-keys/install-coldkey.sh
./install-keys/install-hotkey-all.sh

#install subtensor
#cd ~
#git clone https://github.com/opentensor/subtensor.git
#cd subtensor
#sudo ./scripts/run/subtensor.sh -e docker --network mainnet --node-type lite
#pwd

cd ~
git clone https://github.com/Supreme-Emperor-Wang/ImageAlchemy.git
sudo apt-get update
sudo apt-get install python3.10-venv
python3.10 -m venv ~/venvs/ImageAlchemy
source ~/venvs/ImageAlchemy/bin/activate
pip install wheel
pip install --upgrade setuptools
source ~/venvs/ImageAlchemy/bin/activate
cd ~/ImageAlchemy
pip install -e .

#install project
#cd ~
#git clone https://github.com/Supreme-Emperor-Wang/ImageAlchemy.git
##git clone git@github.com:Supreme-Emperor-Wang/ImageAlchemy.git
#cd ~/ImageAlchemy
#pip install -r requirements.txt

#upgrade torch and torchvision
#pip install torch torchvision --upgrade
#cd ~/ImageAlchemy
#export PYTHONPATH=`pwd`

#install new driver
#cd ..
#sudo curl -O https://us.download.nvidia.com/XFree86/Linux-x86_64/550.54.14/NVIDIA-Linux-x86_64-550.54.14.run
#sudo chmod 777 NVIDIA-Linux-x86_64-550.54.14.run
#sudo ./NVIDIA-Linux-x86_64-550.54.14.run