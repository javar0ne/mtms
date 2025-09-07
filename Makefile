package-all:
	mvn clean package

server:
	cd server/ && mvn clean package
	clear
	java -jar server/target/server-0.0.1-SNAPSHOT-shaded.jar

client-user:
	cd client-user/ && mvn clean package
	clear
	java -jar client-user/target/client-user-0.0.1-SNAPSHOT-shaded.jar