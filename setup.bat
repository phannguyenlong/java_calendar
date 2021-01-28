copy config.properties .\SQL_Calendar\src\main\resources\com\sql_calendar\config.properties
copy config.properties .\SQL_server\webserver\src\main\resources\config.properties
cd ./SQL_server/webserver
mvn clean package cargo:redeploy
if errorlevel 1 (
    D:\stuff\apache-maven-3.6.3\bin\mvn clean package cargo:redeploy
    if errorlevel 1 (
        @ECHO off
        echo msgbox "Make sure you dowload apache-maven-3.6.3 in D://stuff or install maven on your workstatione",0,"Error" > #.vbs
        #.vbs
        del #.vbs
    )
)