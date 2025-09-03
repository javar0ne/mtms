drop-and-create-db: drop-db create-db

drop-db:
	rm -f src/main/resources/db/mtms.db
create-db:
	mvn liquibase:update
