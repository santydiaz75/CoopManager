@echo off
set JAVA_HOME=C:\Users\santy\.jdk\jdk-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

java -Dfile.encoding=ISO-8859-1 -cp "target\classes;target\dependency\*" winUIPackage.Launcher