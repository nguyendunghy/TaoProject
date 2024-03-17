mvn clean
mvn package
java -jar target/TaoProject-1.0-SNAPSHOT.jar MONITOR ./src/main/resources/application.property >> logs/monitor.log &
