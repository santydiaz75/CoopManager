@echo off
echo Compilando reporte corregido con Java 21...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Compilando SimpleCompiler...
javac -cp ".;target/classes;target/dependency/*" SimpleCompiler.java

if %errorlevel% neq 0 (
    echo Error en la compilacion
    pause
    exit /b 1
)

echo Ejecutando compilacion del reporte...
java -cp ".;target/classes;target/dependency/*" SimpleCompiler

echo.
echo Verificando si el reporte funciona ahora...
java -cp ".;target/classes;target/dependency/*" TestListadoCosecheros

pause