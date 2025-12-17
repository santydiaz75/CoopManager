# Script para ejecutar GestCoop en modo depuración
# Configuración avanzada de debugging Java para desarrollo

param(
    [int]$DebugPort = 5005,
    [string]$JavaPath = "C:\Program Files\Java\jdk-21\bin\java.exe",
    [switch]$WaitForDebugger = $true,
    [switch]$Verbose = $false,
    [switch]$ShowConsole = $true,
    [string]$LogLevel = "INFO",
    [switch]$HibernateDebug = $false,
    [switch]$ShowSQL = $false
)

# Configurar preferencias de output
if ($Verbose) { $VerbosePreference = "Continue" }
$ErrorActionPreference = "Stop"

# Función para escribir logs con colores
function Write-DebugLog {
    param([string]$Message, [string]$Type = "INFO")
    
    $timestamp = Get-Date -Format "HH:mm:ss"
    switch ($Type) {
        "INFO" { Write-Host "[$timestamp] [INFO] $Message" -ForegroundColor Green }
        "WARN" { Write-Host "[$timestamp] [WARN] $Message" -ForegroundColor Yellow }
        "ERROR" { Write-Host "[$timestamp] [ERROR] $Message" -ForegroundColor Red }
        "DEBUG" { Write-Host "[$timestamp] [DEBUG] $Message" -ForegroundColor Cyan }
    }
}

Write-Host "?? GESTCOOP - MODO DEPURACIÓN" -ForegroundColor Magenta
Write-Host "=================================" -ForegroundColor Magenta

# Verificar Java
if (-not (Test-Path $JavaPath)) {
    $JavaPath = "java"
    Write-DebugLog "Java 21 no encontrado, usando java del PATH" "WARN"
} else {
    Write-DebugLog "Usando Java: $JavaPath" "INFO"
}

# Verificar JAR
$jarPath = "target\dist\GestCoop.jar"
if (-not (Test-Path $jarPath)) {
    Write-DebugLog "GestCoop.jar no encontrado. Ejecutando build..." "WARN"
    try {
        & ".\build_and_package.ps1"
        Write-DebugLog "Build completado" "INFO"
    } catch {
        Write-DebugLog "Error en build: $($_.Exception.Message)" "ERROR"
        exit 1
    }
}

Write-DebugLog "JAR encontrado: $jarPath" "INFO"

# Configurar argumentos JVM para debugging
$jvmArgs = @()

# Debug remoto
if ($WaitForDebugger) {
    $jvmArgs += "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:$DebugPort"
    Write-DebugLog "Esperando conexión del debugger en puerto $DebugPort" "DEBUG"
} else {
    $jvmArgs += "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DebugPort"
    Write-DebugLog "Debugger disponible en puerto $DebugPort (no espera)" "DEBUG"
}

# Configuraciones de memoria y performance
$jvmArgs += "-Xms512m"          # Memoria inicial
$jvmArgs += "-Xmx2048m"         # Memoria máxima
$jvmArgs += "-XX:+UseG1GC"      # Garbage Collector optimizado
$jvmArgs += "-XX:+ShowCodeDetailsInExceptionMessages"  # Mejores stack traces

# Configuraciones de desarrollo
$jvmArgs += "-Djava.awt.headless=false"  # Permitir GUI
$jvmArgs += "-Dfile.encoding=UTF-8"      # Encoding correcto
$jvmArgs += "-Duser.timezone=Europe/Madrid"  # Zona horaria

# Logging avanzado - Configurado para minimizar ruido de Hibernate
if ($LogLevel -eq "DEBUG") {
    $jvmArgs += "-Djava.util.logging.config.level=INFO"  # Reducido de FINEST a INFO
    $jvmArgs += "-Xlog:gc*:gc.log"    # GC logging a archivo separado
} else {
    $jvmArgs += "-Djava.util.logging.config.level=WARN"  # Solo advertencias importantes
}

# Configuraciones específicas de Hibernate
if ($HibernateDebug) {
    $jvmArgs += "-Dhibernate.show_sql=true"              # Mostrar SQL
    $jvmArgs += "-Dhibernate.format_sql=true"            # Formatear SQL
    $jvmArgs += "-Dorg.hibernate.SQL=DEBUG"              # Debug SQL
    $jvmArgs += "-Dorg.hibernate.type=DEBUG"             # Debug tipos
    Write-DebugLog "Hibernate debugging HABILITADO" "WARN"
} else {
    $jvmArgs += "-Dhibernate.show_sql=$ShowSQL"          # Controlado por parámetro
    $jvmArgs += "-Dhibernate.format_sql=false"           # No formatear SQL
    $jvmArgs += "-Dorg.hibernate.SQL=WARN"               # Solo advertencias SQL
    $jvmArgs += "-Dorg.hibernate.type=WARN"              # Solo advertencias de tipo
    Write-DebugLog "Hibernate debugging deshabilitado (use -HibernateDebug para habilitar)" "INFO"
}

$jvmArgs += "-Dorg.hibernate.tool.hbm2ddl=ERROR"      # Solo errores críticos de schema
$jvmArgs += "-Dorg.hibernate.pretty=false"            # Desactivar pretty printing
$jvmArgs += "-Dorg.hibernate.connection=ERROR"        # Solo errores de conexión
$jvmArgs += "-Dorg.hibernate.transaction=WARN"        # Advertencias de transacción
$jvmArgs += "-Dorg.hibernate.cache=WARN"              # Advertencias de cache

# Configuración de Swing para debugging
$jvmArgs += "-Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel"
$jvmArgs += "-Dsun.awt.disablegrab=true"  # Mejor para debugging UI
$jvmArgs += "-Djava.awt.headless=false"   # Asegurar GUI habilitada
$jvmArgs += "-Dswing.aatext=true"         # Texto suavizado
$jvmArgs += "-Dawt.useSystemAAFontSettings=on"  # Fuentes del sistema

# Forzar que la ventana sea visible
$jvmArgs += "-Djava.awt.Window.locationByPlatform=true"
$jvmArgs += "-Dswing.crossplatformlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel"

# Crear comando completo
$allArgs = $jvmArgs + @("-jar", "GestCoop.jar")

Write-DebugLog "Configuración de debugging:" "INFO"
Write-Host "  Puerto debug: $DebugPort" -ForegroundColor Cyan
Write-Host "  Esperar debugger: $WaitForDebugger" -ForegroundColor Cyan
Write-Host "  Java: $JavaPath" -ForegroundColor Cyan
Write-Host "  JAR: $jarPath" -ForegroundColor Cyan

Write-DebugLog "Argumentos JVM:" "DEBUG"
foreach ($arg in $jvmArgs) {
    Write-Host "  $arg" -ForegroundColor Gray
}

Write-Host "`n?? INICIANDO GESTCOOP EN MODO DEBUG..." -ForegroundColor Green

try {
    # Cambiar a directorio de trabajo
    Push-Location "target\dist"
    
    if ($ShowConsole) {
        # Ejecutar en la consola actual para ver output
        Write-DebugLog "Ejecutando en consola (Ctrl+C para detener)" "INFO"
        
        if ($WaitForDebugger) {
            Write-Host "`n?? ESPERANDO DEBUGGER..." -ForegroundColor Yellow
            Write-Host "   1. Abra su IDE (IntelliJ, Eclipse, VS Code)" -ForegroundColor Cyan
            Write-Host "   2. Configure 'Remote JVM Debug' en puerto $DebugPort" -ForegroundColor Cyan
            Write-Host "   3. Inicie la sesión de debug" -ForegroundColor Cyan
            Write-Host "   4. La aplicación continuará automáticamente`n" -ForegroundColor Cyan
        }
        
        # Ejecutar usando Start-Process para manejar argumentos correctamente
        Write-DebugLog "Iniciando proceso Java con argumentos de debug..." "DEBUG"
        
        $startInfo = New-Object System.Diagnostics.ProcessStartInfo
        $startInfo.FileName = $JavaPath
        $startInfo.Arguments = $allArgs -join ' '
        $startInfo.WorkingDirectory = (Get-Location).Path
        $startInfo.UseShellExecute = $false
        $startInfo.RedirectStandardOutput = $true
        $startInfo.RedirectStandardError = $true
        $startInfo.CreateNoWindow = $false
        $startInfo.WindowStyle = "Normal"
        
        $process = [System.Diagnostics.Process]::Start($startInfo)
        Write-DebugLog "GestCoop iniciado con PID: $($process.Id)" "INFO"
        
        # Monitorear el proceso por unos segundos
        Write-DebugLog "Monitoreando inicio de la aplicación..." "INFO"
        Start-Sleep -Seconds 5
        
        if (-not $process.HasExited) {
            Write-DebugLog "? Proceso Java ejecutándose correctamente" "INFO"
            
            # Verificar si hay ventanas de Java
            $javaWindows = Get-Process | Where-Object { $_.ProcessName -eq "java" -and $_.MainWindowTitle -ne "" }
            if ($javaWindows) {
                Write-DebugLog "? Ventana GUI detectada: $($javaWindows[0].MainWindowTitle)" "INFO"
            } else {
                Write-DebugLog "??  No se detectó ventana GUI - verificando errores..." "WARN"
                
                # Leer errores si los hay
                $stderr = $process.StandardError.ReadToEnd()
                $stdout = $process.StandardOutput.ReadToEnd()
                
                if ($stderr) {
                    Write-DebugLog "STDERR: $stderr" "ERROR"
                }
                if ($stdout) {
                    Write-DebugLog "STDOUT: $stdout" "INFO"
                }
            }
        } else {
            $exitCode = $process.ExitCode
            $stderr = $process.StandardError.ReadToEnd()
            $stdout = $process.StandardOutput.ReadToEnd()
            
            Write-DebugLog "? Proceso terminó con código: $exitCode" "ERROR"
            Write-DebugLog "STDERR: $stderr" "ERROR"
            Write-DebugLog "STDOUT: $stdout" "ERROR"
        }
        
    } else {
        # Ejecutar en ventana separada
        $startInfo = New-Object System.Diagnostics.ProcessStartInfo
        $startInfo.FileName = $JavaPath
        $startInfo.Arguments = $allArgs -join ' '
        $startInfo.WorkingDirectory = (Get-Location).Path
        $startInfo.UseShellExecute = $true
        $startInfo.WindowStyle = "Normal"
        
        $process = [System.Diagnostics.Process]::Start($startInfo)
        Write-DebugLog "GestCoop iniciado en proceso separado. PID: $($process.Id)" "INFO"
        
        if ($WaitForDebugger) {
            Write-Host "`n?? GestCoop está esperando conexión del debugger en puerto $DebugPort" -ForegroundColor Yellow
        }
    }
    
} catch {
    Write-DebugLog "Error ejecutando GestCoop: $($_.Exception.Message)" "ERROR"
    Write-Host "Stack trace:" -ForegroundColor Red
    Write-Host $_.ScriptStackTrace -ForegroundColor Red
} finally {
    Pop-Location
}

Write-Host "`n? Script de debug completado" -ForegroundColor Green

# Crear archivo de configuración para IDEs
$debugConfig = @"
# Configuración de debugging para IDEs
# GestCoop Debug Configuration

## IntelliJ IDEA / Android Studio
1. Run/Debug Configurations ? Add New ? Remote JVM Debug
   - Host: localhost
   - Port: $DebugPort
   - Debugger mode: Attach to remote JVM
   - Transport: Socket
   - Use module classpath: <module-name>

## Eclipse
1. Run ? Debug Configurations ? Remote Java Application
   - Project: CoopManager
   - Connection Type: Standard (Socket Attach)
   - Host: localhost
   - Port: $DebugPort

## Visual Studio Code
1. Instalar Extension Pack for Java
2. Crear launch.json:
{
    "type": "java",
    "name": "Attach to GestCoop",
    "request": "attach",
    "hostName": "localhost",
    "port": $DebugPort
}

## Argumentos JVM utilizados:
$($jvmArgs -join "`n")
"@

$debugConfig | Out-File "DEBUG_CONFIGURATION.md" -Encoding UTF8
Write-DebugLog "Configuración guardada en: DEBUG_CONFIGURATION.md" "INFO"