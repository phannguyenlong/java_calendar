copy config.properties .\SQL_Calendar\src\main\resources\com\sql_calendar\config.properties
copy config.properties .\SQL_server\webserver\src\main\resources\config.properties
cd ./SQL_server/webserver
D:\stuff\apache-maven-3.6.3\bin\mvn clean package cargo:redeploy