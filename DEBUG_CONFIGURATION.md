# Configuración de debugging para IDEs
# GestCoop Debug Configuration

## IntelliJ IDEA / Android Studio
1. Run/Debug Configurations ? Add New ? Remote JVM Debug
   - Host: localhost
   - Port: 5005
   - Debugger mode: Attach to remote JVM
   - Transport: Socket
   - Use module classpath: <module-name>

## Eclipse
1. Run ? Debug Configurations ? Remote Java Application
   - Project: CoopManager
   - Connection Type: Standard (Socket Attach)
   - Host: localhost
   - Port: 5005

## Visual Studio Code
1. Instalar Extension Pack for Java
2. Crear launch.json:
{
    "type": "java",
    "name": "Attach to GestCoop",
    "request": "attach",
    "hostName": "localhost",
    "port": 5005
}

## Argumentos JVM utilizados:
-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005
-Xms512m
-Xmx2048m
-XX:+UseG1GC
-XX:+ShowCodeDetailsInExceptionMessages
-Djava.awt.headless=false
-Dfile.encoding=UTF-8
-Duser.timezone=Europe/Madrid
-Djava.util.logging.config.level=WARN
-Dhibernate.show_sql=False
-Dhibernate.format_sql=false
-Dorg.hibernate.SQL=WARN
-Dorg.hibernate.type=WARN
-Dorg.hibernate.tool.hbm2ddl=ERROR
-Dorg.hibernate.pretty=false
-Dorg.hibernate.connection=ERROR
-Dorg.hibernate.transaction=WARN
-Dorg.hibernate.cache=WARN
-Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel
-Dsun.awt.disablegrab=true
