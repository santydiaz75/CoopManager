@echo off
echo Ejecutando test con Java 21...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

REM Ejecutar el test
java -cp ".;target/classes;target/dependency/*" TestListadoCosecheros

pause