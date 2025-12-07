@echo off
REM CoopManager Runner with Maven Dependencies
echo Starting CoopManager with Maven dependencies...

REM Set JAVA_HOME and Maven
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set MAVEN_HOME=C:\Users\santy\.maven\maven-3.9.11
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

REM Check if dependencies exist, if not compile and copy them
if not exist "target\dependency" (
    echo Compiling and copying dependencies...
    mvn clean compile dependency:copy-dependencies
    if errorlevel 1 (
        echo Error: Maven compilation failed
        pause
        exit /b 1
    )
)

REM Run the application with proper classpath
echo Running CoopManager...
java -Dfile.encoding=ISO-8859-1 -Duser.language=es -Duser.country=ES -cp "target\classes;target\dependency\*" winUIPackage.Launcher

echo.
echo Application finished.
pause