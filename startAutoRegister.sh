mvn clean
mvn package
java -jar target/TaoProject-1.0-SNAPSHOT.jar AUTO_REGISTER ./src/main/resources/application.property >> logs/subnet29_autoRegister.log &
#tail -100f logs/autoRegister.log