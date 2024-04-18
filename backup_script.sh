mvn clean
mvn package
mkdir logs
source /home/ubuntu/venv/head-tail/bin/activate
cd /home/ubuntu/head-tail-llm-detection
java -jar /home/ubuntu/TaoProject/target/TaoProject-1.0-SNAPSHOT.jar BACKUP_SCRIPT /home/ubuntu/TaoProject/src/main/resources/application.property  /home/ubuntu/TaoProject/startClientApp.sh >> backup_scrip.log &
