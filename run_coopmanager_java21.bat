@echo off
REM CoopManager Java 21 Runner
echo Starting CoopManager with Java 21...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8

REM Use Maven to run the application
"C:\Users\santy\.maven\maven-3.9.11\bin\mvn" clean compile exec:java -Dexec.mainClass="winUIPackage.Launcher" -Dfile.encoding=ISO-8859-1

echo.
echo Application finished.
pause