# GestCoop Launcher Standalone - Versión sin ps2exe
# Este script funciona sin necesidad de ser convertido a .exe
$ErrorActionPreference = "Continue"
$DebugPreference = "Continue"

# Función para escribir al log con mejor manejo de errores
function Write-AppLog($message, $level = "INFO") {
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp] [$level] $message"
    
    # Obtener directorio del script
    $ScriptDir = if ($PSScriptRoot) { 
        $PSScriptRoot 
    } else {
        Split-Path -Parent $MyInvocation.MyCommand.Path
    }
    
    try {
        $logPath = Join-Path $ScriptDir "GestCoop_Standalone.log"
        Add-Content -Path $logPath -Value $logEntry -Encoding UTF8 -ErrorAction SilentlyContinue
        Write-Host $logEntry -ForegroundColor Gray
    } catch {
        Write-Host "ERROR LOGGING: $message" -ForegroundColor Red
    }
}

# Obtener directorio del script
$ScriptDir = if ($PSScriptRoot) { 
    $PSScriptRoot 
} else {
    Split-Path -Parent $MyInvocation.MyCommand.Path
}

Write-AppLog "===========================================" "INFO"
Write-AppLog "Iniciando GestCoop Standalone desde: $ScriptDir" "INFO"
Write-AppLog "Usuario actual: $env:USERNAME" "INFO"
Write-AppLog "Máquina: $env:COMPUTERNAME" "INFO"
Write-AppLog "PowerShell Version: $($PSVersionTable.PSVersion)" "INFO"
Write-AppLog "Sistema Operativo: $([System.Environment]::OSVersion)" "INFO"
Write-AppLog "===========================================" "INFO"

# Cargar Windows Forms para mostrar mensajes
try {
    Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue
    Add-Type -AssemblyName System.Drawing -ErrorAction SilentlyContinue
    Write-AppLog "Windows Forms cargado exitosamente" "INFO"
} catch {
    Write-AppLog "Error al cargar Windows Forms: $($_.Exception.Message)" "WARNING"
}

# Función mejorada para mostrar mensajes con debug
function Show-Error($msg) {
    Write-AppLog "ERROR: $msg" "ERROR"
    if ($Error.Count -gt 0) {
        Write-AppLog "Último error PowerShell: $($Error[0].Exception.Message)" "ERROR"
        Write-AppLog "Stack trace: $($Error[0].ScriptStackTrace)" "ERROR"
    }
    
    try {
        $debugMsg = $msg + "`n`nDetalles técnicos:`n"
        $debugMsg += "- Directorio de trabajo: $ScriptDir`n"
        $debugMsg += "- Usuario: $env:USERNAME`n"
        $debugMsg += "- Máquina: $env:COMPUTERNAME`n"
        $debugMsg += "- PowerShell: $($PSVersionTable.PSVersion)`n"
        $debugMsg += "`nVer log completo en: $(Join-Path $ScriptDir 'GestCoop_Standalone.log')"
        
        [System.Windows.Forms.MessageBox]::Show($debugMsg, "GestCoop Error", "OK", "Error")
    } catch {
        Write-Host "ERROR: $msg" -ForegroundColor Red
        Write-Host "Log ubicado en: $(Join-Path $ScriptDir 'GestCoop_Standalone.log')" -ForegroundColor Yellow
    }
}

# Función para instalar Java 21 automáticamente (versión simplificada)
function Install-Java21-Auto {
    Write-AppLog "Iniciando instalación automática de Java 21" "INFO"
    
    try {
        $jdkInstallDir = "C:\Users\$env:USERNAME\.jdk"
        if (-not (Test-Path $jdkInstallDir)) {
            New-Item -ItemType Directory -Path $jdkInstallDir -Force | Out-Null
        }
        
        $downloadUrl = "https://aka.ms/download-jdk/microsoft-jdk-21.0.8-windows-x64.zip"
        $zipFile = Join-Path $jdkInstallDir "jdk-21-windows.zip"
        $extractDir = Join-Path $jdkInstallDir "jdk-21.0.8"
        
        Write-AppLog "Descargando Java 21 desde: $downloadUrl" "INFO"
        
        # Mostrar progreso al usuario
        try {
            [System.Windows.Forms.MessageBox]::Show("Descargando Java 21. Esto puede tomar unos minutos...", "GestCoop", "OK", "Information")
        } catch {}
        
        # Descargar Java 21
        Invoke-WebRequest -Uri $downloadUrl -OutFile $zipFile -UseBasicParsing
        Write-AppLog "Java 21 descargado exitosamente" "INFO"
        
        # Extraer el archivo ZIP
        Write-AppLog "Extrayendo Java 21..." "INFO"
        Expand-Archive -Path $zipFile -DestinationPath $jdkInstallDir -Force
        
        # Buscar el directorio extraído
        $extractedDirs = Get-ChildItem $jdkInstallDir -Directory | Where-Object { $_.Name -like "*jdk-21*" }
        if ($extractedDirs.Count -gt 0) {
            $actualExtractDir = $extractedDirs[0].FullName
            if ($actualExtractDir -ne $extractDir -and -not (Test-Path $extractDir)) {
                Rename-Item $actualExtractDir $extractDir
            }
        }
        
        # Limpiar archivo ZIP
        Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
        
        # Verificar instalación
        $javaExePath = Join-Path $extractDir "bin\java.exe"
        if (Test-Path $javaExePath) {
            Write-AppLog "Java 21 instalado exitosamente en: $extractDir" "INFO"
            return $javaExePath
        } else {
            Write-AppLog "La instalación de Java 21 falló - ejecutable no encontrado en: $javaExePath" "ERROR"
            return $null
        }
        
    } catch {
        Write-AppLog "Error durante la instalación de Java 21: $($_.Exception.Message)" "ERROR"
        return $null
    }
}

# Buscar Java de forma sistemática
Write-AppLog "Iniciando búsqueda de Java..." "INFO"
$javaExe = $null

# Probar rutas conocidas
$knownPaths = @(
    "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8\bin\java.exe",
    "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
)

foreach ($knownPath in $knownPaths) {
    if (Test-Path $knownPath) {
        $javaExe = $knownPath
        Write-AppLog "Java encontrado en ubicación conocida: $knownPath" "INFO"
        break
    }
}

# Si no se encuentra, probar en PATH
if (-not $javaExe) {
    try {
        $javaTest = Get-Command java -ErrorAction Stop 2>$null
        if ($javaTest) {
            $javaExe = $javaTest.Source
            Write-AppLog "Java encontrado en PATH: $javaExe" "INFO"
        }
    } catch {
        Write-AppLog "Java no encontrado en PATH" "WARNING"
    }
}

# Buscar en directorios comunes
if (-not $javaExe) {
    $javaSearchPaths = @(
        "C:\Program Files\Java\jdk-21*",
        "C:\Program Files (x86)\Java\jdk-21*",
        "C:\Users\$env:USERNAME\.jdk\jdk-21*"
    )
    
    foreach ($searchPath in $javaSearchPaths) {
        try {
            $parentDir = Split-Path $searchPath
            if (Test-Path $parentDir) {
                $pattern = Split-Path $searchPath -Leaf
                $javaDirs = Get-ChildItem $parentDir -Directory | Where-Object { $_.Name -like $pattern }
                foreach ($javaDir in $javaDirs) {
                    $testPath = Join-Path $javaDir.FullName "bin\java.exe"
                    if (Test-Path $testPath) {
                        $javaExe = $testPath
                        Write-AppLog "Java encontrado en búsqueda: $testPath" "INFO"
                        break
                    }
                }
                if ($javaExe) { break }
            }
        } catch {
            Write-AppLog "Error en búsqueda: $($_.Exception.Message)" "WARNING"
        }
    }
}

# Si no encontramos Java, intentar instalación automática
if (-not $javaExe) {
    Write-AppLog "Java 21 no encontrado, iniciando instalación automática" "INFO"
    $javaExe = Install-Java21-Auto
}

# Verificar que encontramos Java
if (-not $javaExe) {
    $errorMsg = "No se encontró Java 21+ y no se pudo instalar automáticamente. Por favor instale Java 21 manualmente."
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando Java: $javaExe" "INFO"

# Verificar que existe el JAR
$jarPath = Join-Path $ScriptDir "GestCoop.jar"
if (-not (Test-Path $jarPath)) {
    $errorMsg = "No se encontró GestCoop.jar en $ScriptDir"
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando JAR: $jarPath" "INFO"

# Validar requisitos antes del inicio
Write-AppLog "Validando requisitos previos al inicio..." "INFO"

# Verificar versión de Java
try {
    $javaVersionOutput = & $javaExe -version 2>&1
    Write-AppLog "Salida de versión Java: $javaVersionOutput" "INFO"
    
    if ($javaVersionOutput -match 'version "(\d+)') {
        $javaVersion = $Matches[1]
        Write-AppLog "Versión de Java detectada: $javaVersion" "INFO"
        if ([int]$javaVersion -lt 21) {
            $errorMsg = "Java $javaVersion detectado, pero se requiere Java 21 o superior"
            Show-Error $errorMsg
            Write-AppLog $errorMsg "ERROR"
            exit 1
        }
    }
} catch {
    Write-AppLog "Error verificando versión de Java: $($_.Exception.Message)" "ERROR"
}

# Verificar permisos de directorio
try {
    $testFile = Join-Path $ScriptDir "test_permissions.tmp"
    "test" | Out-File -FilePath $testFile -ErrorAction Stop
    Remove-Item $testFile -ErrorAction SilentlyContinue
    Write-AppLog "Permisos de escritura en directorio verificados" "INFO"
} catch {
    Write-AppLog "Advertencia: Posibles problemas de permisos en directorio ${ScriptDir}: $($_.Exception.Message)" "WARNING"
}

# Iniciar la aplicación con manejo robusto de errores
try {
    Write-AppLog "Iniciando aplicación GestCoop..." "INFO"
    Write-AppLog "Comando: $javaExe -jar `"$jarPath`"" "INFO"
    Write-AppLog "Directorio de trabajo: $ScriptDir" "INFO"
    
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaExe
    $startInfo.Arguments = "-jar `"$jarPath`""
    $startInfo.WorkingDirectory = $ScriptDir
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $false  # Cambiar a $true para ocultar consola
    $startInfo.RedirectStandardOutput = $true
    $startInfo.RedirectStandardError = $true
    
    Write-AppLog "Configuración de proceso completada" "INFO"
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    
    if ($process) {
        Write-AppLog "Proceso iniciado exitosamente. PID: $($process.Id)" "INFO"
        
        # Esperar un poco y verificar que el proceso sigue ejecutándose
        Start-Sleep -Milliseconds 3000
        
        if (-not $process.HasExited) {
            Write-AppLog "GestCoop ejecutándose correctamente después de 3 segundos" "INFO"
            Write-AppLog "===========================================" "INFO"
            exit 0
        } else {
            # El proceso terminó prematuramente, capturar salida de error
            $stdout = $process.StandardOutput.ReadToEnd()
            $stderr = $process.StandardError.ReadToEnd()
            $exitCode = $process.ExitCode
            
            Write-AppLog "Proceso terminó prematuramente con código: $exitCode" "ERROR"
            Write-AppLog "STDOUT: $stdout" "ERROR"
            Write-AppLog "STDERR: $stderr" "ERROR"
            
            $errorMsg = "GestCoop se cerró inesperadamente.`nCódigo de salida: $exitCode"
            if ($stderr) {
                $errorMsg += "`nError: $stderr"
            }
            if ($stdout) {
                $errorMsg += "`nSalida: $stdout"
            }
            
            Show-Error $errorMsg
            exit 1
        }
    } else {
        $errorMsg = "No se pudo iniciar GestCoop - Process.Start() devolvió null"
        Show-Error $errorMsg
        Write-AppLog $errorMsg "ERROR"
        exit 1
    }
} catch {
    $errorMsg = "Error crítico al iniciar GestCoop: $($_.Exception.Message)"
    Write-AppLog "Excepción completa: $($_.Exception.ToString())" "ERROR"
    Write-AppLog "Información del sistema: PowerShell $($PSVersionTable.PSVersion), .NET $($PSVersionTable.CLRVersion)" "ERROR"
    Show-Error $errorMsg
    exit 1
}