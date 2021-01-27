copy config.properties .\SQL_Calendar\src\main\resources\com\sql_calendar\config.properties
copy config.properties .\SQL_server\webserver\src\main\resources\config.properties
cd ./SQL_server/webserver
mvn clean package cargo:redeploy