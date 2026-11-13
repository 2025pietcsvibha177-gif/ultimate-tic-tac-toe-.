@echo off
echo Testing MySQL Database Connection...
mvn exec:java -Ptest-db
pause
