# Debug Launcher para GestCoop - Diagnóstico de problemas
# Este script ayuda a diagnosticar por qué GestCoop.exe no se inicia correctamente

param(
    [string]$GestCoopPath = ""
)

$ErrorActionPreference = "Continue"

function Write-Debug {
    param([string]$Message, [string]$Color = "White")
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Write-Host "[$timestamp] $Message" -ForegroundColor $Color
}

function Test-JavaInstallation {
    Write-Debug "=== DIAGNÓSTICO DE JAVA ===" "Cyan"
    
    # Buscar Java en ubicaciones conocidas
    $javaLocations = @(
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8\bin\java.exe",
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
    )
    
    $foundJava = $false
    foreach ($location in $javaLocations) {
        if (Test-Path $location) {
            Write-Debug "? Java encontrado en: $location" "Green"
            try {
                $versionOutput = & $location -version 2>&1
                Write-Debug "Versión: $versionOutput" "Gray"
                $foundJava = $true
                return $location
            } catch {
                Write-Debug "? Error ejecutando Java en $location`: $_" "Red"
            }
        } else {
            Write-Debug "? Java no encontrado en: $location" "Red"
        }
    }
    
    # Buscar en PATH
    try {
        $javaCmd = Get-Command java -ErrorAction Stop
        Write-Debug "? Java encontrado en PATH: $($javaCmd.Source)" "Green"
        $versionOutput = & java -version 2>&1
        Write-Debug "Versión: $versionOutput" "Gray"
        return $javaCmd.Source
    } catch {
        Write-Debug "? Java no encontrado en PATH" "Red"
    }
    
    if (-not $foundJava) {
        Write-Debug "? PROBLEMA: No se encontró instalación de Java válida" "Red"
        return $null
    }
}

function Test-GestCoopFiles {
    param([string]$BasePath)
    
    Write-Debug "=== DIAGNÓSTICO DE ARCHIVOS GESTCOOP ===" "Cyan"
    Write-Debug "Directorio base: $BasePath" "White"
    
    # Verificar estructura de archivos
    $requiredFiles = @(
        "GestCoop.jar",
        "GestCoop.exe"
    )
    
    $requiredDirs = @(
        "GestCoop_lib",
        "reportsPackage"
    )
    
    foreach ($file in $requiredFiles) {
        $filePath = Join-Path $BasePath $file
        if (Test-Path $filePath) {
            $fileInfo = Get-Item $filePath
            Write-Debug "? $file encontrado ($([math]::Round($fileInfo.Length / 1KB, 2)) KB)" "Green"
        } else {
            Write-Debug "? $file NO encontrado" "Red"
        }
    }
    
    foreach ($dir in $requiredDirs) {
        $dirPath = Join-Path $BasePath $dir
        if (Test-Path $dirPath) {
            $fileCount = (Get-ChildItem $dirPath -File).Count
            Write-Debug "? $dir encontrado ($fileCount archivos)" "Green"
        } else {
            Write-Debug "? $dir NO encontrado" "Red"
        }
    }
}

function Test-Permissions {
    param([string]$BasePath)
    
    Write-Debug "=== DIAGNÓSTICO DE PERMISOS ===" "Cyan"
    
    try {
        $testFile = Join-Path $BasePath "test_permissions.tmp"
        "test" | Out-File -FilePath $testFile -ErrorAction Stop
        Remove-Item $testFile -ErrorAction SilentlyContinue
        Write-Debug "? Permisos de escritura: OK" "Green"
    } catch {
        Write-Debug "? Sin permisos de escritura: $_" "Red"
    }
    
    try {
        $jarPath = Join-Path $BasePath "GestCoop.jar"
        if (Test-Path $jarPath) {
            $acl = Get-Acl $jarPath
            Write-Debug "? JAR accesible para lectura" "Green"
        }
    } catch {
        Write-Debug "? Problema accediendo al JAR: $_" "Red"
    }
}

function Test-JavaJarExecution {
    param([string]$JavaPath, [string]$JarPath)
    
    Write-Debug "=== PRUEBA DE EJECUCIÓN JAVA ===" "Cyan"
    Write-Debug "Java: $JavaPath" "White"
    Write-Debug "JAR: $JarPath" "White"
    
    if (-not (Test-Path $JavaPath)) {
        Write-Debug "? Java no encontrado en ruta especificada" "Red"
        return
    }
    
    if (-not (Test-Path $JarPath)) {
        Write-Debug "? JAR no encontrado" "Red"
        return
    }
    
    try {
        Write-Debug "Ejecutando: $JavaPath -version" "Yellow"
        $versionOutput = & $JavaPath -version 2>&1
        Write-Debug "Salida de versión: $versionOutput" "Gray"
        
        Write-Debug "Ejecutando: $JavaPath -jar `"$JarPath`" (con timeout de 5 segundos)" "Yellow"
        
        $job = Start-Job -ScriptBlock {
            param($java, $jar)
            & $java -jar $jar 2>&1
        } -ArgumentList $JavaPath, $JarPath
        
        $completed = Wait-Job $job -Timeout 5
        
        if ($completed) {
            $output = Receive-Job $job
            Write-Debug "Salida del JAR: $output" "Gray"
        } else {
            Write-Debug "?? El proceso no terminó en 5 segundos (posiblemente iniciando GUI)" "Yellow"
        }
        
        Stop-Job $job -ErrorAction SilentlyContinue
        Remove-Job $job -ErrorAction SilentlyContinue
        
    } catch {
        Write-Debug "? Error ejecutando Java: $_" "Red"
    }
}

function Test-PowerShellExeExecution {
    param([string]$ExePath)
    
    Write-Debug "=== PRUEBA DEL EJECUTABLE POWERSHELL ===" "Cyan"
    Write-Debug "Ejecutable: $ExePath" "White"
    
    if (-not (Test-Path $ExePath)) {
        Write-Debug "? Ejecutable no encontrado" "Red"
        return
    }
    
    try {
        Write-Debug "Información del archivo:" "Yellow"
        $fileInfo = Get-Item $ExePath
        Write-Debug "  Tamaño: $([math]::Round($fileInfo.Length / 1KB, 2)) KB" "Gray"
        Write-Debug "  Creado: $($fileInfo.CreationTime)" "Gray"
        Write-Debug "  Modificado: $($fileInfo.LastWriteTime)" "Gray"
        
        Write-Debug "Ejecutando con timeout de 10 segundos..." "Yellow"
        
        $processInfo = New-Object System.Diagnostics.ProcessStartInfo
        $processInfo.FileName = $ExePath
        $processInfo.UseShellExecute = $false
        $processInfo.RedirectStandardOutput = $true
        $processInfo.RedirectStandardError = $true
        $processInfo.CreateNoWindow = $false
        
        $process = [System.Diagnostics.Process]::Start($processInfo)
        
        $completed = $process.WaitForExit(10000) # 10 segundos
        
        if ($completed) {
            $stdout = $process.StandardOutput.ReadToEnd()
            $stderr = $process.StandardError.ReadToEnd()
            Write-Debug "Código de salida: $($process.ExitCode)" "Gray"
            if ($stdout) { Write-Debug "STDOUT: $stdout" "Gray" }
            if ($stderr) { Write-Debug "STDERR: $stderr" "Red" }
        } else {
            Write-Debug "?? El proceso no terminó en 10 segundos" "Yellow"
            $process.Kill()
        }
        
    } catch {
        Write-Debug "? Error ejecutando PowerShell EXE: $_" "Red"
    }
}

# ===== EJECUCIÓN PRINCIPAL =====

Write-Debug "INICIANDO DIAGNÓSTICO DE GESTCOOP" "Cyan"
Write-Debug "Fecha: $(Get-Date)" "White"
Write-Debug "Usuario: $env:USERNAME" "White"
Write-Debug "Máquina: $env:COMPUTERNAME" "White"
Write-Debug "PowerShell: $($PSVersionTable.PSVersion)" "White"
Write-Debug "" "White"

# Determinar directorio de GestCoop
if (-not $GestCoopPath) {
    # Buscar en ubicaciones comunes
    $commonPaths = @(
        "C:\Program Files (x86)\GestCoopInSQL",
        "target\dist",
        "."
    )
    
    foreach ($path in $commonPaths) {
        $testExe = Join-Path $path "GestCoop.exe"
        if (Test-Path $testExe) {
            $GestCoopPath = $path
            Write-Debug "GestCoop encontrado en: $GestCoopPath" "Green"
            break
        }
    }
}

if (-not $GestCoopPath -or -not (Test-Path $GestCoopPath)) {
    Write-Debug "? No se pudo encontrar el directorio de GestCoop" "Red"
    Write-Debug "Especifique la ruta con: .\debug_launcher.ps1 -GestCoopPath 'C:\ruta\a\gestcoop'" "Yellow"
    exit 1
}

# Ejecutar diagnósticos
$javaPath = Test-JavaInstallation
Test-GestCoopFiles -BasePath $GestCoopPath
Test-Permissions -BasePath $GestCoopPath

if ($javaPath) {
    $jarPath = Join-Path $GestCoopPath "GestCoop.jar"
    Test-JavaJarExecution -JavaPath $javaPath -JarPath $jarPath
}

$exePath = Join-Path $GestCoopPath "GestCoop.exe"
Test-PowerShellExeExecution -ExePath $exePath

Write-Debug "" "White"
Write-Debug "DIAGNÓSTICO COMPLETADO" "Cyan"
Write-Debug "Revise los mensajes anteriores para identificar problemas" "Yellow"