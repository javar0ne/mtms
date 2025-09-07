drop-and-create-db: drop-db create-db

drop-db:
	rm -f src/main/resources/db/mtms.db
create-db:
	mvn liquibase:update

run:
	mvn clean package
	java -jar server/target/server-0.0.1-SNAPSHOT-shaded.jar &
	clear
	java -jar client-user/target/client-user-0.0.1-SNAPSHOT-shaded.jar