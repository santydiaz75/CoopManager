@echo off
rem Set environment variables for Java 21 and Maven
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=C:\Users\santy\.jdk\jdk-21.0.8\bin;C:\Users\santy\.maven\maven-3.9.11(2)\bin;%PATH%

echo Using Java 21 LTS for CoopManager
java -version
echo.

rem Clean and compile the project
echo Cleaning and compiling with Java 21...
mvn clean compile

rem Run the application
echo Starting CoopManager with Java 21...
mvn exec:java

pause