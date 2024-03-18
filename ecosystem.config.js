module.exports = {
    apps: [
        {
            name: 'redis-server',
            script: 'redis-server',
            args: "/etc/redis/redis.conf"
        },
        {
            name: 'server',
            script: 'python',
            interpreter: 'none',
            args: 'model/server.py',
        },
        {
            name: 'neurons-prover',
            script: 'python',
            interpreter: 'none',
            args: 'neurons/prover/app.py --subtensor.network finney --neuron.model_endpoint "http://127.0.0.1:5005" --logging.debug --logging.trace --netuid 29 --axon.port 8888 --axon.external_port 6006 --wallet.name default --wallet.hotkey jackie_hotkey_25'
        }
    ]
};
