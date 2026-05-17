@echo off
chcp 65001 > nul

if not exist out mkdir out

dir /s /b src\*.java > sources.txt

javac -encoding UTF-8 -d out @sources.txt

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

java -cp out Main

pause
