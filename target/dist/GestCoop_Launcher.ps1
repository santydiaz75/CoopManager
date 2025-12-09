# GestCoop Launcher - Version Ultra Simple con Logging
$ErrorActionPreference = "SilentlyContinue"

# Funci�n para escribir al log
function Write-AppLog($message, $level = "INFO") {
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp] [$level] $message"
    try {
        Add-Content -Path (Join-Path $ScriptDir "GestCoop.log") -Value $logEntry -ErrorAction SilentlyContinue
    } catch {
        # Ignorar errores de escritura de log
    }
}

# Obtener directorio del script
$ScriptDir = if ($MyInvocation.MyCommand.Path) { 
    Split-Path -Parent $MyInvocation.MyCommand.Path 
} elseif ($PSScriptRoot) { 
    $PSScriptRoot 
} else {
    # Para ejecutables ps2exe, usar la ubicaci�n del ejecutable
    try {
        $exeLocation = [System.Reflection.Assembly]::GetExecutingAssembly().Location
        if ($exeLocation) {
            Split-Path -Parent $exeLocation
        } else {
            Get-Location | Select-Object -ExpandProperty Path
        }
    } catch {
        # Fallback al directorio actual
        Get-Location | Select-Object -ExpandProperty Path
    }
}

Write-AppLog "Iniciando GestCoop desde: $ScriptDir" "INFO"

# Cargar Windows Forms para mostrar mensajes
try {
    Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue
    Add-Type -AssemblyName System.Drawing -ErrorAction SilentlyContinue
} catch {
    Write-AppLog "Error al cargar Windows Forms: $($_.Exception.Message)" "WARNING"
}

# Funci�n simple para mostrar mensajes
function Show-Error($msg) {
    Write-AppLog "ERROR: $msg" "ERROR"
    try {
        [System.Windows.Forms.MessageBox]::Show($msg, "GestCoop Error", "OK", "Error")
    } catch {
        Write-Host "ERROR: $msg" -ForegroundColor Red
    }
}

# Funci�n para instalar Java 21 autom�ticamente
function Install-Java21-Auto {
    Write-AppLog "Iniciando instalaci�n autom�tica de Java 21" "INFO"
    
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
        
        # Buscar el directorio extra�do
        $extractedDirs = Get-ChildItem $jdkInstallDir -Directory | Where-Object { $_.Name -like "*jdk-21*" }
        if ($extractedDirs.Count -gt 0) {
            $actualExtractDir = $extractedDirs[0].FullName
            if ($actualExtractDir -ne $extractDir -and -not (Test-Path $extractDir)) {
                Rename-Item $actualExtractDir $extractDir
            }
        }
        
        # Limpiar archivo ZIP
        Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
        
        # Verificar instalaci�n
        $javaExePath = Join-Path $extractDir "bin\java.exe"
        if (Test-Path $javaExePath) {
            Write-AppLog "Java 21 instalado exitosamente en: $extractDir" "INFO"
            return $javaExePath
        } else {
            Write-AppLog "La instalaci�n de Java 21 fall� - ejecutable no encontrado en: $javaExePath" "ERROR"
            return $null
        }
        
    } catch {
        Write-AppLog "Error durante la instalaci�n de Java 21: $($_.Exception.Message)" "ERROR"
        return $null
    }
}

# Buscar Java de forma simple
$javaExe = $null

# Probar rutas conocidas
$knownPaths = @(
    "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8\bin\java.exe",
    "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
)

foreach ($knownPath in $knownPaths) {
    if ([System.IO.File]::Exists($knownPath)) {
        $javaExe = $knownPath
        Write-AppLog "Java encontrado en ubicaci�n conocida: $knownPath" "INFO"
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
        "C:\Program Files\Java\jdk-21*\bin\java.exe",
        "C:\Program Files (x86)\Java\jdk-21*\bin\java.exe",
        "C:\Users\$env:USERNAME\.jdk\jdk-21*\bin\java.exe"
    )
    
    foreach ($searchPath in $javaSearchPaths) {
        $parentDir = Split-Path (Split-Path $searchPath)
        if (Test-Path $parentDir) {
            $pattern = Split-Path (Split-Path $searchPath) -Leaf
            $javaDirs = Get-ChildItem $parentDir -Directory | Where-Object { $_.Name -like $pattern }
            foreach ($javaDir in $javaDirs) {
                $testPath = Join-Path $javaDir.FullName "bin\java.exe"
                if (Test-Path $testPath) {
                    $javaExe = $testPath
                    Write-AppLog "Java encontrado en b�squeda: $testPath" "INFO"
                    break
                }
            }
            if ($javaExe) { break }
        }
    }
}

# Si no encontramos Java, intentar instalaci�n autom�tica
if (-not $javaExe) {
    Write-AppLog "Java 21 no encontrado, iniciando instalaci�n autom�tica" "INFO"
    $javaExe = Install-Java21-Auto
}

# Verificar que encontramos Java
if (-not $javaExe) {
    $errorMsg = "No se encontr� Java 21+ y no se pudo instalar autom�ticamente. Por favor instale Java 21 manualmente."
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando Java: $javaExe" "INFO"

# Verificar que existe el JAR
$jarPath = Join-Path $ScriptDir "GestCoop.jar"
if (-not [System.IO.File]::Exists($jarPath)) {
    $errorMsg = "No se encontr� GestCoop.jar en $ScriptDir"
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando JAR: $jarPath" "INFO"

# Iniciar la aplicaci�n
try {
    Write-AppLog "Iniciando aplicaci�n GestCoop..." "INFO"
    
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaExe
    $startInfo.Arguments = "-jar `"$jarPath`""
    $startInfo.WorkingDirectory = $ScriptDir
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $true
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    
    if ($process) {
        Write-AppLog "GestCoop iniciado exitosamente. PID: $($process.Id)" "INFO"
        exit 0
    } else {
        $errorMsg = "No se pudo iniciar GestCoop - Process.Start() devolvi� null"
        Show-Error $errorMsg
        Write-AppLog $errorMsg "ERROR"
        exit 1
    }
} catch {
    $errorMsg = "Error al iniciar GestCoop: $($_.Exception.Message)"
    Show-Error $errorMsg
    Write-AppLog $errorMsg "ERROR"
    exit 1
}
