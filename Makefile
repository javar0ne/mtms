package:
	mvn clean package

server: package
	clear
	java -jar server/target/server-0.0.1-SNAPSHOT-shaded.jar

client-user: package
	clear
	java -jar client-user/target/client-user-0.0.1-SNAPSHOT-shaded.jar

client-manager: package
	clear
	java -jar client-manager/target/client-manager-0.0.1-SNAPSHOT-shaded.jar