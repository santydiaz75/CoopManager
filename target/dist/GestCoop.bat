@echo off
setlocal enabledelayedexpansion
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Starting GestCoop Application...
java --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED -cp "GestCoop.jar;GestCoop_lib\*" -Dfile.encoding=ISO-8859-1 winUIPackage.Launcher

if %ERRORLEVEL% neq 0 (
    echo.
    echo Error: no se ha encontrado o cargado la clase principal winUIPackage.Launcher
    echo.
    pause
) else (
    pause
)
