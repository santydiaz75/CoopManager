@echo off
echo =====================================
echo   GESTCOOP - EJECUCION SILENCIOSA
echo =====================================
echo.

REM Configurar variables
set JAVA_EXE=C:\Program Files\Java\jdk-21\bin\java.exe
set JAR_PATH=target\dist\GestCoop.jar

REM Verificar Java
if not exist "%JAVA_EXE%" (
    echo Usando java del PATH
    set JAVA_EXE=java
)

REM Verificar JAR
if not exist "%JAR_PATH%" (
    echo Construyendo proyecto...
    call build_and_package.ps1
)

echo Iniciando GestCoop con logging minimo...
echo.

cd /d target\dist

REM Ejecutar con logging minimo - sin ruido de Hibernate
"%JAVA_EXE%" ^
    -Xms512m ^
    -Xmx1024m ^
    -Dfile.encoding=UTF-8 ^
    -Djava.awt.headless=false ^
    -Djava.util.logging.config.level=WARNING ^
    -Dhibernate.show_sql=false ^
    -Dhibernate.format_sql=false ^
    -Dorg.hibernate.SQL=ERROR ^
    -Dorg.hibernate.type=ERROR ^
    -Dorg.hibernate.tool.hbm2ddl=ERROR ^
    -Dorg.hibernate.connection=ERROR ^
    -Dorg.hibernate.transaction=ERROR ^
    -Dorg.hibernate.cache=ERROR ^
    -Dorg.hibernate.pretty=false ^
    -jar GestCoop.jar

if errorlevel 1 (
    echo.
    echo Error al ejecutar GestCoop
    echo Revise los logs para mas detalles
)

echo.
echo Presione cualquier tecla para cerrar...
pause >nul