@echo off
echo Ejecutando test completo del reporte con Java 21...

REM Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

REM Compilar
echo Compilando TestReporteCompleto...
javac -cp ".;target/classes;target/dependency/*" TestReporteCompleto.java

if %errorlevel% neq 0 (
    echo Error en la compilacion
    pause
    exit /b 1
)

REM Ejecutar
echo Ejecutando test del reporte...
java -cp ".;target/classes;target/dependency/*" TestReporteCompleto

pause