cd SQL_Calendar
mvn javafx:run
if errorlevel 1 (
    D:\stuff\apache-maven-3.6.3\bin\mvn javafx:run
    if errorlevel 1 (
        @ECHO off
        echo msgbox "Make sure you dowload apache-maven-3.6.3 in D://stuff or install maven on your workstatione",0,"Error" > #.vbs
        #.vbs
        del #.vbs
    )
)