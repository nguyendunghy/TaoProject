mvn clean
mvn package
mkdir logs
java -jar target/TaoProject-1.0-SNAPSHOT.jar AUTO_REGISTER ./src/main/resources/application.property REG-SN43 >> logs/autoRegister.log &
#tail -100f logs/autoRegister.log