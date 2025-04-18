@ECHO OFF
IF "%OS%" == "Windows_NT" setlocal ENABLEDELAYEDEXPANSION

set SCRIPT_NAME=digest.bat
cd %0\..
cd ..
set EXECUTABLE_CLASS=org.jasypt.intf.cli.JasyptStringDigestCLI
set EXEC_CLASSPATH=.
if "%JASYPT_CLASSPATH%" == "" goto computeclasspath
set EXEC_CLASSPATH=%EXEC_CLASSPATH%;%JASYPT_CLASSPATH%

:computeclasspath
FOR %%c in (.\lib\*.jar) DO set EXEC_CLASSPATH=!EXEC_CLASSPATH!;%%c

set JAVA_EXECUTABLE=java
if "%JAVA_HOME%" == "" goto execute
set JAVA_EXECUTABLE="%JAVA_HOME%\bin\java"

:execute
%JAVA_EXECUTABLE% -classpath %EXEC_CLASSPATH% %EXECUTABLE_CLASS% %SCRIPT_NAME% %*
