# Configuración del ejecutable
Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

function Show-Message {
    param([string]$Message, [string]$Title = "GestCoop", [string]$Type = "Information")
    
    $icon = switch($Type) {
        "Error" { [System.Windows.Forms.MessageBoxIcon]::Error }
        "Warning" { [System.Windows.Forms.MessageBoxIcon]::Warning }
        default { [System.Windows.Forms.MessageBoxIcon]::Information }
    }
    
    [System.Windows.Forms.MessageBox]::Show($Message, $Title, [System.Windows.Forms.MessageBoxButtons]::OK, $icon)
}

function Find-Java {
    # Lista de ubicaciones específicas donde buscar Java (Java 21 primero)
    $javaLocations = @(
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"  # Java 21 específico instalado
    )
    
    # Buscar en ubicaciones específicas primero
    foreach ($location in $javaLocations) {
        if (Test-Path $location) {
            return $location
        }
    }
    
    # Buscar en directorios de Java estándar
    $javaDirs = @(
        "$env:ProgramFiles\Java",
        "$env:ProgramFiles(x86)\Java"
    )
    
    foreach ($javaDir in $javaDirs) {
        if (Test-Path $javaDir) {
            # Buscar JDK 21 primero
            $jdk21 = Get-ChildItem -Path $javaDir -Filter "jdk-21*" -Directory -ErrorAction SilentlyContinue | 
                Select-Object -First 1
            if ($jdk21) {
                $javaExe = Join-Path $jdk21.FullName "bin\java.exe"
                if (Test-Path $javaExe) {
                    return $javaExe
                }
            }
            
            # Buscar cualquier JDK
            $anyJdk = Get-ChildItem -Path $javaDir -Filter "jdk*" -Directory -ErrorAction SilentlyContinue | 
                Select-Object -First 1
            if ($anyJdk) {
                $javaExe = Join-Path $anyJdk.FullName "bin\java.exe"
                if (Test-Path $javaExe) {
                    return $javaExe
                }
            }
        }
    }
    
    # Probar JAVA_HOME
    if ($env:JAVA_HOME) {
        $javaHome = Join-Path $env:JAVA_HOME "bin\java.exe"
        if (Test-Path $javaHome) {
            return $javaHome
        }
    }
    
    # Como último recurso, probar java en PATH
    try {
        $null = Get-Command java -ErrorAction Stop
        return "java"
    } catch { }
    
    return $null
}

# Cambiar al directorio del script
Set-Location $ScriptDir

# Verificar archivos necesarios
if (-not (Test-Path "GestCoop.jar")) {
    Show-Message "No se encontró GestCoop.jar en el directorio actual.`n`nDirectorio: $ScriptDir" "Error" "Error"
    exit 1
}

if (-not (Test-Path "GestCoop_lib")) {
    Show-Message "No se encontró la carpeta GestCoop_lib.`n`nDirectorio: $ScriptDir" "Error" "Error"
    exit 1
}

# Buscar Java
$javaExe = Find-Java

if (-not $javaExe) {
    $message = @"
No se encontró Java en el sistema.

Para ejecutar GestCoop necesita Java 11 o superior.

Opciones:
? Descargar Java desde: https://adoptium.net/
? Configurar la variable JAVA_HOME
? Agregar Java al PATH del sistema

Directorio de GestCoop: $ScriptDir
"@
    Show-Message $message "Error - Java no encontrado" "Error"
    exit 1
}

# Verificar versión de Java
try {
    $versionOutput = & $javaExe -version 2>&1
    $versionFound = $false
    
    foreach ($line in $versionOutput) {
        if ($line -match 'version "(\d+)\.') {
            $majorVersion = [int]$matches[1]
            if ($majorVersion -lt 11) {
                Show-Message "Java $majorVersion encontrado, pero GestCoop requiere Java 11 o superior.`n`nJava encontrado: $javaExe`n`nPor favor instale Java 21 desde: https://adoptium.net/" "Error - Versión de Java" "Error"
                exit 1
            }
            $versionFound = $true
            break
        } elseif ($line -match 'version "1\.(\d+)\.') {
            $version = [int]$matches[1]
            if ($version -lt 8) {
                Show-Message "Java 1.$version encontrado, pero GestCoop requiere Java 11 o superior.`n`nJava encontrado: $javaExe`n`nPor favor instale Java 21 desde: https://adoptium.net/" "Error - Versión de Java" "Error"
                exit 1
            }
            $versionFound = $true
            break
        }
    }
    
    if (-not $versionFound) {
        Write-Host "Advertencia: No se pudo determinar la versión de Java, intentando continuar..."
    }
} catch {
    Show-Message "Error al verificar la versión de Java: $($_.Exception.Message)" "Error" "Error"
    exit 1
}

# Ejecutar GestCoop
try {
    Write-Host "Iniciando GestCoop con Java: $javaExe"
    & $javaExe -jar "GestCoop.jar"
    
    if ($LASTEXITCODE -ne 0 -and $LASTEXITCODE -ne $null) {
        Show-Message "GestCoop terminó con código de error: $LASTEXITCODE" "Aviso" "Warning"
    }
} catch {
    Show-Message "Error al ejecutar GestCoop:`n`n$($_.Exception.Message)" "Error" "Error"
}