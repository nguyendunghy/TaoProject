mvn clean
mvn package
java -jar target/TaoProject-1.0-SNAPSHOT.jar TEST_DATA ./src/main/resources/application.property >> logs/testData.log &