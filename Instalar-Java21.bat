@echo off
echo =====================================
echo  INSTALADOR JAVA 21 SDK - GestCoop
echo =====================================
echo.
echo Ejecutando script de instalacion...
echo.

powershell -ExecutionPolicy Bypass -File "%~dp0Install-Java21-SDK.ps1" %*

echo.
echo Presiona cualquier tecla para cerrar...
pause >nul