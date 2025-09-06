drop-and-create-db: drop-db create-db

drop-db:
	rm -f src/main/resources/db/mtms.db
create-db:
	mvn liquibase:update

run: drop-db
	mvn clean package
	clear
	#mvn exec:java -Dexec.mainClass="com.dg.mtms.server.ServerApplication"
	java -jar target/server-0.0.1-SNAPSHOT-shaded.jar