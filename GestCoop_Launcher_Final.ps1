# GestCoop Launcher para ps2exe
param([switch]$Debug)

if ($Debug) { $DebugPreference = "Continue" } else { $DebugPreference = "SilentlyContinue" }
$ErrorActionPreference = "SilentlyContinue"

# Función para escribir al log simplificada para ps2exe
function Write-AppLog {
    param([string]$message, [string]$level = "INFO")
    
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp] [$level] $message"
    
    # Obtener directorio del script - compatible con ps2exe
    $ScriptDir = $PSScriptRoot
    if (-not $ScriptDir) {
        try {
            $ScriptDir = Split-Path -Parent ([System.Reflection.Assembly]::GetExecutingAssembly().Location)
        } catch {
            $ScriptDir = Get-Location | Select-Object -ExpandProperty Path
        }
    }
    
    try {
        $logPath = "$ScriptDir\GestCoop.log"
        $logEntry | Out-File -FilePath $logPath -Append -Encoding UTF8 -ErrorAction SilentlyContinue
        if ($Debug) { Write-Host $logEntry -ForegroundColor Gray }
    } catch {
        if ($Debug) { Write-Host "ERROR LOGGING: $message" -ForegroundColor Red }
    }
}

# Obtener directorio del script - compatible con ps2exe
$ScriptDir = $PSScriptRoot
if (-not $ScriptDir) {
    try {
        $ScriptDir = Split-Path -Parent ([System.Reflection.Assembly]::GetExecutingAssembly().Location)
    } catch {
        $ScriptDir = Get-Location | Select-Object -ExpandProperty Path
    }
}

Write-AppLog "===========================================" "INFO"
Write-AppLog "GestCoop Launcher v2.0 (ps2exe compatible)" "INFO"
Write-AppLog "Iniciando desde: $ScriptDir" "INFO"
Write-AppLog "Usuario: $env:USERNAME, PC: $env:COMPUTERNAME" "INFO"
Write-AppLog "PowerShell: $($PSVersionTable.PSVersion.ToString())" "INFO"
Write-AppLog "===========================================" "INFO"

# Cargar Windows Forms
try {
    Add-Type -AssemblyName System.Windows.Forms
    Add-Type -AssemblyName System.Drawing
    Write-AppLog "Windows Forms cargado" "INFO"
} catch {
    Write-AppLog "Error cargando Windows Forms: $($_.Exception.Message)" "WARNING"
}

# Función para mostrar errores
function Show-Error {
    param([string]$msg)
    Write-AppLog "ERROR: $msg" "ERROR"
    try {
        [System.Windows.Forms.MessageBox]::Show($msg, "GestCoop Error", [System.Windows.Forms.MessageBoxButtons]::OK, [System.Windows.Forms.MessageBoxIcon]::Error) | Out-Null
    } catch {
        Write-Host "ERROR: $msg" -ForegroundColor Red
    }
}

# Verificar JAR
$jarPath = "$ScriptDir\GestCoop.jar"
if (-not (Test-Path $jarPath)) {
    $errorMsg = "GestCoop.jar no encontrado en: $ScriptDir"
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando JAR: $jarPath" "INFO"

# Iniciar aplicación
try {
    Write-AppLog "Iniciando GestCoop..." "INFO"
    
    # Configurar Java 21 para esta sesión
    $javaExe = "C:\Program Files\Java\jdk-21\bin\java.exe"
    if (-not (Test-Path $javaExe)) {
        $javaExe = "java" # Fallback al java del PATH
        Write-AppLog "Java 21 no encontrado, usando java del PATH" "WARNING"
    } else {
        Write-AppLog "Usando Java 21: $javaExe" "INFO"
    }
    
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaExe
    $startInfo.Arguments = "-jar `"$jarPath`""
    $startInfo.WorkingDirectory = $ScriptDir
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $true
    $startInfo.RedirectStandardOutput = $true
    $startInfo.RedirectStandardError = $true
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    
    if ($process -ne $null) {
        Write-AppLog "Proceso iniciado. PID: $($process.Id)" "INFO"
        
        # Verificar que sigue ejecutándose
        Start-Sleep -Seconds 2
        
        if ($process.HasExited) {
            $stdout = $process.StandardOutput.ReadToEnd()
            $stderr = $process.StandardError.ReadToEnd()
            $exitCode = $process.ExitCode
            
            Write-AppLog "Proceso terminó. Código: $exitCode" "ERROR"
            Write-AppLog "STDOUT: $stdout" "ERROR"
            Write-AppLog "STDERR: $stderr" "ERROR"
            
            $errorMsg = "GestCoop se cerró inesperadamente.`nCódigo: $exitCode"
            if ($stderr) { $errorMsg += "`nError: $stderr" }
            Show-Error $errorMsg
            exit 1
        }
        
        Write-AppLog "GestCoop ejecutándose correctamente" "INFO"
        exit 0
    } else {
        Show-Error "No se pudo iniciar GestCoop"
        exit 1
    }
} catch {
    $errorMsg = "Error iniciando GestCoop: $($_.Exception.Message)"
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}