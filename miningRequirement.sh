#install bittensor
pip install --upgrade pip
pip3 install bittensor

#setup coldkey and hotkey
./install-keys/install-coldkey.sh
./install-keys/install-hotkey-all.sh

  sudo apt-get update && sudo apt-get install redis-server
sudo apt-get install libgl1-mesa-glx
sudo apt-get install libglib2.0-0

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash

nvm install 18 && nvm use 18

npm install pm2@latest -g

git clone https://github.com/fractal-net/fractal.git
cd fractal

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
nvm install 18 && nvm use 18
npm install pm2@latest -g

pip install -e .
pip install -r requirements.txt

#upgrade torch and torchvision
pip install torch torchvision --upgrade


#install new driver
#cd ..
#sudo curl -O https://us.download.nvidia.com/XFree86/Linux-x86_64/550.54.14/NVIDIA-Linux-x86_64-550.54.14.run
#sudo chmod 777 NVIDIA-Linux-x86_64-550.54.14.run
#sudo ./NVIDIA-Linux-x86_64-550.54.14.run