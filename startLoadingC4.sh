mvn clean
mvn package
java -jar target/TaoProject-1.0-SNAPSHOT.jar LOAD_ALL_C4 ./src/main/resources/application.property >> logs/loadc4.log &
