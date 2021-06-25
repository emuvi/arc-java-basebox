@echo off
call mvn clean install
call mvn source:jar install
pause
