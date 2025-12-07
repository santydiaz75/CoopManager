@echo off
REM CoopManager Java 21 Debug Runner
echo Starting CoopManager in DEBUG mode with Java 21...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set MAVEN_HOME=C:\Users\santy\.maven\maven-3.9.11
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

REM Debug configuration
set DEBUG_PORT=5005
set DEBUG_SUSPEND=n
set JVM_DEBUG_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=%DEBUG_SUSPEND%,address=*:%DEBUG_PORT%

echo.
echo ================================================
echo   CoopManager DEBUG Mode Configuration
echo ================================================
echo   Debug Port: %DEBUG_PORT%
echo   Suspend on Start: %DEBUG_SUSPEND%
echo   Java Version: Java 21
echo   Encoding: ISO-8859-1 (Spanish)
echo   Locale: Spanish (ES)
echo ================================================
echo.
echo Connect your IDE debugger to localhost:%DEBUG_PORT%
echo Press any key to continue...
pause

REM Check if dependencies exist, if not compile and copy them
if not exist "target\dependency" (
    echo Compiling and copying dependencies for debug...
    mvn clean compile dependency:copy-dependencies
    if errorlevel 1 (
        echo Error: Maven compilation failed
        pause
        exit /b 1
    )
)

REM Run the application in debug mode with proper classpath
echo Starting CoopManager in DEBUG mode...
echo Debug server will be listening on port %DEBUG_PORT%
echo.

java %JVM_DEBUG_OPTS% ^
     -Dfile.encoding=ISO-8859-1 ^
     -Duser.language=es ^
     -Duser.country=ES ^
     -Djava.awt.headless=false ^
     -Dswing.aatext=true ^
     -Dsun.java2d.opengl=false ^
     -Xlog:gc*:gc.log ^
     -Xms512m ^
     -Xmx2048m ^
     -cp "target\classes;target\dependency\*" ^
     winUIPackage.Launcher

echo.
echo ================================================
echo Application finished - Debug session ended
echo ================================================
pause