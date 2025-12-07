@echo off
REM CoopManager Maven Debug Runner
echo Starting CoopManager in DEBUG mode using Maven exec...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8

REM Debug configuration
set DEBUG_PORT=5005
set DEBUG_SUSPEND=n

echo.
echo ================================================
echo   CoopManager MAVEN DEBUG Mode Configuration
echo ================================================
echo   Debug Port: %DEBUG_PORT%
echo   Suspend on Start: %DEBUG_SUSPEND%
echo   Java Version: Java 21
echo   Encoding: UTF-8 (with Spanish locale)
echo   Maven Exec Plugin: Enabled
echo ================================================
echo.
echo Connect your IDE debugger to localhost:%DEBUG_PORT%
echo Press any key to continue...
pause

REM Use Maven to run the application in debug mode
echo Running CoopManager with Maven in DEBUG mode...
echo Debug server will be listening on port %DEBUG_PORT%
echo.

"C:\Users\santy\.maven\maven-3.9.11\bin\mvn" clean compile exec:java ^
    -Dexec.mainClass="winUIPackage.Launcher" ^
    -Dexec.args="-agentlib:jdwp=transport=dt_socket,server=y,suspend=%DEBUG_SUSPEND%,address=*:%DEBUG_PORT%" ^
    -Dfile.encoding=UTF-8 ^
    -Duser.language=es ^
    -Duser.country=ES ^
    -Djava.awt.headless=false ^
    -Dswing.aatext=true

echo.
echo ================================================
echo Application finished - Maven Debug session ended
echo ================================================
pause