@echo off
echo === COMPILADOR FINAL JASPER ===

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Compilando FinalCompiler...
javac -cp ".;target/classes;target/dependency/*" FinalCompiler.java

if %errorlevel% neq 0 (
    echo Error en la compilacion de FinalCompiler
    pause
    exit /b 1
)

echo Ejecutando compilacion final...
java -cp ".;target/classes;target/dependency/*" FinalCompiler

echo.
echo Verificando resultado con test...
java -cp ".;target/classes;target/dependency/*" TestListadoCosecheros

pause