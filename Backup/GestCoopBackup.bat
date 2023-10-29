@ echo off
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem |  Mysqldump y Compresión con 7zip de MySQL DB en Windows
rem |  Version: 1.0
rem |  Fecha: 18 / 05 / 2010
rem |  Autor: Gabriel Soltz  |  thegaby@gmail.com  |  knox-it.blogspot.com
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | VARIABLES
rem | BKP_PATH = Path donde se guardaran los backups
rem | ZIP_PATH = Ruta a 7zip (setear ZIP_PATH="7za.exe" si 7za.exe se encuentra en la
rem | misma ubicacion que este script)
rem | 7za.exe puede descargase de http://downloads.sourceforge.net/sevenzip/7za465.zip
SET BKP_PATH="C:\Development\GestCoop\Backup"
SET ZIP_PATH="7za.exe"
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | CRON = CON O SIN INTERACCION DE USUARIO (1/0)
rem | Si se setea en 1 la variable CRON, se debe configurar tambien usuario, contraseña,
rem | base de datos, etc yel script se correrá sin interacción del usuario.
rem | Si se setea en 0, el script preguntará cada una de las variables.
rem | ------------------------------------------------------------------------------------------------------------------------------------
SET CRON=0
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | DO_CHECK = Ejecutar optimize y repair en la base de datos (1/0)
rem | DO_ZIP = Comprimir backup (1/0)
rem | DB_USER = Usuario de MySQL con permisos en la base de datos a backupear
rem | DB_PASS = Password
rem | DB_NAME = Nombre de la base de datos a backupear. Si queremos un backup de todas las db, setear DB_NAME=ALL
rem | PARAMS = Parametros adicionales de mysqldump. Ej: PARAMS=--ssl --ssl-ca=name
rem | ------------------------------------------------------------------------------------------------------------------------------------   
SET DO_CHECK=1
SET DO_ZIP=1
SET DB_USER=root
SET DB_PASS=sametsis
SET DB_NAME=coopmanagerdb
SET PARAMS=
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | Obtener FECHA                                                                                                   
for /f "tokens=1 delims=: " %%h in ('time /T') do set hour=%%h
for /f "tokens=2 delims=: " %%m in ('time /T') do set minutes=%%m
for /f "tokens=3 delims=: " %%a in ('time /T') do set ampm=%%a
set NOW=%hour%-%minutes%-%ampm%
set FECHA=%date%
set FECHA=%FECHA:/=%
set FECHA=%FECHA: =%
set FECHA=%FECHA::=%
set FECHA=%FECHA:,=%
set FECHA=%FECHA%-%NOW%
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | ------------------------------------------------------------------------------------------------------------------------------------
color 02
echo --------------------------------------------------------------------------
echo BACK UP CON MYSQLDUMP EN WINDOWS
echo --------------------------------------------------------------------------
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | Chequeo Variable CRON
if %CRON%== 1 GOTO :mysql2
if %CRON%== 0 GOTO :checkVAR
rem | ------------------------------------------------------------------------------------------------------------------------------------
rem | CHEQUEO DE VARIABLES
:checkVAR
echo CHEQUEANDO VARIABLES...
if not exist %BKP_PATH% GOTO :errorVAR
if not exist %ZIP_PATH% GOTO :errorVAR
if exist %ZIP_PATH% SET DO_ZIP=1
if %DO_ZIP%== 0 GOTO :VAR_OK
if %DO_ZIP%== 1 GOTO :VAR_OK_ZIP
:VAR_OK_ZIP
echo.
echo     ZIP_PATH:%ZIP_PATH% = OK
GOTO :VAR_OK
:VAR_OK
echo     BKP_PATH:%BKP_PATH% = OK
echo --------------------------------------------------------------------------
GOTO :mysql
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Obetener Variables de MySQL
:mysql
color 02
echo --------------------------------------------------------------------------
SET DB_USER=root
SET DB_PASS=sametsis
SET MY=no
rem SET /P DB_USER=[ INGRESE USUARIO MYSQL:] 
rem SET /P DB_PASS=[ INGRESE CONTRASENA:] 
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Chequeo Acceso
c:
cd %MYSQLPATHBIN%
SET MY_DO_CHECK=mysql -u %DB_USER% -p%DB_PASS% -e "SELECT VERSION();\G"
for /f "tokens=*" %%a in ('%MY_DO_CHECK%') do SET MY=%%a
if "%MY%"== "no" GOTO :errorMY
echo.
echo --------------------------------------------------------------------------
echo           MYSQL: %MY%
echo           USER: %DB_USER%
echo           PASSWORD: OK
echo --------------------------------------------------------------------------
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Listar DB
echo.
echo BASES DE DATOS DISPONIBLES:
mysql -u %DB_USER% -p%DB_PASS% -e "SHOW DATABASES;\G;"
echo --------------------------------------------------------------------------
SET DB_NAME=coopmanagerdb
rem SET /P DB_NAME=[ INGRESE NOMBRE DE LA BASE DE DATOS, (ALL = TODAS LAS BDS):] 
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Chequeo DB
if %DB_NAME%==ALL GOTO :set_check
SET DB=no
SET DB_DO_CHECK=mysql -u %DB_USER% -p%DB_PASS% -D %DB_NAME% -e "SELECT DATABASE();\G"
for /f "tokens=*" %%b in ('%DB_DO_CHECK%') do (SET DB=%%b)
if "%DB%"== "no" GOTO :errorMY
echo DATABASE: %DB_NAME%: OK
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Parametros Adicionales
SET PARAMS=
rem SET /P PARAMS=[ PARAMETROS ADICIONALES? (enter para none):] 
GOTO :set_check
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | Chequeo y Optimización
:set_check
SET DO_CHECK=1
rem SET /P DO_CHECK=[ DESEA REALIZAR CHEQUEO Y OPTIMIZACION? (1/0):] 
GOTO :mysql2
rem | -------------------------------------------------------------------------------------------------------------------------------------
:mysql2
if %DO_CHECK%==1 GOTO :mysqlcheck
if %DO_CHECK%==0 GOTO :backup
rem | -------------------------------------------------------------------------------------------------------------------------------------
:mysqlcheck
echo --------------------------------------------------------------------------
echo CHEQUEO Y OPTIMIZACION, ESPERE...
echo --------------------------------------------------------------------------
if %DB_NAME%==ALL SET DB_NAME=-A
mysqlcheck -u %DB_USER% -p%DB_PASS% --auto-repair --optimize %DB_NAME%
if %DB_NAME%==-A SET DB_NAME=ALL
GOTO :backup
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | MySQL Dump
:backup
echo --------------------------------------------------------------------------
echo REALIZANDO BACKUP, ESPERE...
echo --------------------------------------------------------------------------
SET FILE=%BKP_PATH%\BackupDB_%DB_NAME%_%FECHA%.sql
SET LOG=%BKP_PATH%\BackupLOG_%DB_NAME%_%FECHA%.log
if %DB_NAME%==ALL SET DB_NAME=-A
"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump" -l --result-file=%FILE% --log-error=%LOG% %PARAMS% -u %DB_USER% -p%DB_PASS% %DB_NAME%
if %DB_NAME%==-A SET DB_NAME=ALL
echo.
echo   MYSQLDUMP OK: %FILE%
if %DO_ZIP%== 1 GOTO :ZIP
if %DO_ZIP%== 0 GOTO :BACKUP_OK
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | ZIP
:ZIP
echo --------------------------------------------------------------------------
echo COMPRIMIENDO ARCHIVO, ESPERE...
echo --------------------------------------------------------------------------
%ZIP_PATH% a %FILE%.zip %FILE%
del %FILE%
del %LOG%
rem | -------------------------------------------------------------------------------------------------------------------------------------
rem | END BACKUP
:BACKUP_OK
echo --------------------------------------------------------------------------
echo BACKUP FINALIZADO: 
if %DO_ZIP%== 1 echo %FILE%.zip
if %DO_ZIP%== 0 echo %FILE%
echo --------------------------------------------------------------------------
GOTO :sub_end
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | ERROR MYSQL:                                                                                                               
:errorMY
color 04
cls
echo.
echo --------------------------------------------------------------------------
echo        MYSQL ERROR:
echo.
if "%MY%"== "no" GOTO :errorMY_MY
if "%DB%"== "no" GOTO :errorMY_DB
:errorMY_MY
%MY_DO_CHECK%
GOTO :errorMY_END
:errorMY_DB
%DB_DO_CHECK%
GOTO :errorMY_END
:errorMY_END
echo.
echo --------------------------------------------------------------------------
GOTO :sub_end
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | ERROR VAR:                                                                                                               
:errorVAR
cls
echo --------------------------------------------------------------------------
color 04
if not exist %BKP_PATH% GOTO :errorBKP_PATH
if not exist %ZIP_PATH% GOTO :errorZIP_PATH
rem | ERROR BKP_PATH
:errorBKP_PATH
echo     BKP_PATH = ERROR! (%BKP_PATH%)
:MenuB
echo.
echo     Seleccione una opcion:
echo.
echo            1. Especificar otra ubicacion
echo            2. Crear la ubicacion especificada (%BKP_PATH%)
echo            3. Salir
set /p var="     > "
if %var%==1 goto :changeBKP_PATH
if %var%==2 goto :createBKP_PATH
if %var%==3 goto :end
if %var% GTR 3 echo OPCION NO VALIDA
cls
GOTO :MenuB
:changeBKP_PATH
SET /P BKP_PATH=[ INGRESE UBICACION PARA BACKUPS:] 
GOTO :checkVAR
:createBKP_PATH
mkdir %BKP_PATH%
if exist %BKP_PATH% echo Se ha creado la ubicacion %BKP_PATH%
if not exist %BKP_PATH% echo IMPOSIBLE CREAR UBICACION %BKP_PATH%
if not exist %BKP_PATH% pause
GOTO :checkVAR
rem | ERROR ZIP_PATH
:errorZIP_PATH
echo     ZIP_PATH = ERROR! (%ZIP_PATH%)
:MenuW
echo.
echo     Seleccione una opcion:
echo.
echo            1. Especificar otra ubicacion
echo            2. No Comprimir Backup
echo            3. Salir
set /p var2="     > "
if %var2%==1 goto :changeZIP_PATH
if %var2%==2 goto :notZIP_PATH
if %var2%==3 goto :end
if %var2% GTR 3 echo Opcion NO VALIDA
cls
GOTO :MenuW
:changeZIP_PATH
echo Deberá ingresar la ubicacion donde se encuentra el archivo 7za.exe (Ej.: c:\program files\7zip\7za.exe)
echo Especificar 7za.exe si se encuentra en la misma ubicacion que este script.
SET /P ZIP_PATH=[ INGRESE UBICACION DE 7za.exe:] 
SET ZIP_PATH="%ZIP_PATH%"
GOTO :checkVAR
:notZIP_PATH
set DO_ZIP=0
GOTO :VAR_OK
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | SUB_END
:sub_end
if %CRON%== 1 GOTO :end
echo.
echo     Seleccione una opcion:
echo.
SET var3=2
echo            1. Ejecutar nuevamente
echo            2. Salir
rem set /p var3="     > "
if %var3%==1 cls
if %var3%==1 goto :mysql
if %var3%==2 goto :end
if %var3% GTR 2 echo Opcion NO VALIDA
GOTO :sub_end
rem | ------------------------------------------------------------------------------------------------------------------------------------   
rem | END
:end
cls
color 30
echo.
echo.
echo --------------------------------------------------------------------------
echo.
rem echo   Programa Finalizado. Cualquier tecla para salir...
echo.
echo --------------------------------------------------------------------------
echo.
echo                                                      santydiaz75@gmail.com

echo.
echo --------------------------------------------------------------------------
rem if %CRON%== 0 pause>Nul
exit
rem | ------------------------------------------------------------------------------------------------------------------------------------   