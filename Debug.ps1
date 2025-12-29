# Script específico para debuggear problemas de Hibernate
# Uso: .\Debug-Hibernate.ps1

param(
    [int]$DebugPort = 5006,
    [switch]$ShowSQL = $true,
    [switch]$ShowConnections = $false,
    [string]$LogFile = "hibernate-debug.log"
)

Write-Host "?? HIBERNATE DEBUG MODE" -ForegroundColor Yellow
Write-Host "======================" -ForegroundColor Yellow

$javaPath = "C:\Program Files\Java\jdk-21\bin\java.exe"
if (-not (Test-Path $javaPath)) {
    $javaPath = "java"
    Write-Host "??  Usando java del PATH" -ForegroundColor Yellow
}

$jarPath = "target\dist\GestCoop.jar"
if (-not (Test-Path $jarPath)) {
    Write-Host "? GestCoop.jar no encontrado. Ejecutando build..." -ForegroundColor Red
    & ".\build_and_package.ps1"
}

# Argumentos específicos para debugging de Hibernate
$jvmArgs = @(
    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DebugPort",
    "-Xms512m",
    "-Xmx1024m",
    "-Dfile.encoding=ISO-8859-1",  # Cambiar a ISO-8859-1 para consistencia
    "-Duser.timezone=Europe/Madrid",
    
    # Hibernate debugging específico
    "-Dhibernate.show_sql=$ShowSQL",
    "-Dhibernate.format_sql=true",
    "-Dhibernate.use_sql_comments=true",
    "-Dhibernate.jdbc.batch_size=0",  # Deshabilitar batching para mejor debug
    "-Dhibernate.order_updates=true",
    "-Dhibernate.order_inserts=true",
    
    # Configuración de tipos y conversiones
    "-Dhibernate.dialect=org.hibernate.dialect.SQLServerDialect",
    "-Dhibernate.connection.characterEncoding=ISO-8859-1",
    "-Dhibernate.connection.useUnicode=true",
    
    # Logging específico usando Log4j en lugar de java.util.logging
    "-Dlog4j.debug=false",
    "-Dhibernate.show_sql=$ShowSQL"
)

# Configuración de conexiones si se requiere
if ($ShowConnections) {
    $jvmArgs += "-Dorg.hibernate.connection=DEBUG"
    $jvmArgs += "-Dcom.mchange.v2.c3p0=DEBUG"
    Write-Host "?? Debugging de conexiones habilitado" -ForegroundColor Cyan
}

# Configurar logging a archivo
if ($LogFile) {
    # Crear archivo log4j.properties en lugar de java.util.logging
    $log4jConfig = @"
# Root logger option
log4j.rootLogger=INFO, console, file

# Console appender
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# File appender
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=$LogFile
log4j.appender.file.MaxFileSize=10MB
log4j.appender.file.MaxBackupIndex=5
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Hibernate logging
log4j.logger.org.hibernate=WARN, file
log4j.logger.org.hibernate.SQL=DEBUG, file
log4j.logger.org.hibernate.type=WARN, file
log4j.logger.org.hibernate.engine=WARN, file
log4j.logger.org.hibernate.transaction=INFO, file
log4j.logger.org.hibernate.cache=WARN, file

# SQL Exception logging
log4j.logger.org.hibernate.engine.jdbc.spi.SqlExceptionHelper=ERROR, file

# Avoid additivity
log4j.additivity.org.hibernate=false
"@
    $log4jConfig | Out-File "target\dist\log4j.properties" -Encoding UTF8
    $jvmArgs += "-Dlog4j.configuration=file:log4j.properties"
    Write-Host "?? Log4j configurado en: $LogFile" -ForegroundColor Green
}

Write-Host "?? Iniciando GestCoop con Hibernate debugging..." -ForegroundColor Green
Write-Host "Puerto debug: $DebugPort" -ForegroundColor Cyan
Write-Host "Mostrar SQL: $ShowSQL" -ForegroundColor Cyan
Write-Host "Log file: $LogFile" -ForegroundColor Cyan
Write-Host ""
Write-Host "?? Para conectar debugger:" -ForegroundColor Yellow
Write-Host "   Host: localhost" -ForegroundColor Gray
Write-Host "   Port: $DebugPort" -ForegroundColor Gray
Write-Host ""

try {
    Push-Location "target\dist"
    
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaPath
    $startInfo.Arguments = ($jvmArgs + @("-jar", "GestCoop.jar")) -join ' '
    $startInfo.WorkingDirectory = (Get-Location).Path
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $false
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    Write-Host "? Proceso iniciado. PID: $($process.Id)" -ForegroundColor Green
    
    if ($LogFile) {
        Write-Host "?? Monitoreando log file..." -ForegroundColor Cyan
        Start-Sleep -Seconds 3
        
        if (Test-Path $LogFile) {
            Write-Host "?? Últimas entradas del log:" -ForegroundColor Yellow
            Get-Content $LogFile -Tail 10 -Wait
        }
    }
    
} catch {
    Write-Host "? Error: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    Pop-Location
}