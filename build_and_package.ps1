# =============================================================================
# Script de Compilación y Empaquetado para GestCoop (CoopManager)
# =============================================================================
# Este script automatiza la compilación del proyecto Java con Maven y 
# genera un ejecutable Windows (.exe) listo para distribución
#
# Autor: CoopManager Team
# Fecha: Diciembre 2025
# Versión: 1.0.0
# =============================================================================

param(
    [switch]$Clean = $false,          # Limpiar antes de compilar
    [switch]$SkipTests = $true,       # Omitir tests por defecto
    [switch]$Verbose = $false,        # Salida detallada
    [switch]$Help = $false            # Mostrar ayuda
)

# Configuración de colores para salida
$Host.UI.RawUI.ForegroundColor = "White"
$ErrorActionPreference = "Stop"

# =============================================================================
# FUNCIONES AUXILIARES
# =============================================================================

function Write-ColorOutput {
    param(
        [string]$Message,
        [string]$Color = "White"
    )
    $previousColor = $Host.UI.RawUI.ForegroundColor
    $Host.UI.RawUI.ForegroundColor = $Color
    Write-Output $Message
    $Host.UI.RawUI.ForegroundColor = $previousColor
}

function Write-Header {
    param([string]$Title)
    Write-ColorOutput "`n===============================================" "Cyan"
    Write-ColorOutput " $Title" "Cyan"
    Write-ColorOutput "===============================================" "Cyan"
}

function Write-Step {
    param([string]$Step)
    Write-ColorOutput "`n? $Step..." "Yellow"
}

function Write-Success {
    param([string]$Message)
    Write-ColorOutput "? $Message" "Green"
}

function Write-Error {
    param([string]$Message)
    Write-ColorOutput "? $Message" "Red"
}

function Show-Help {
    Write-ColorOutput @"

SCRIPT DE COMPILACIÓN Y EMPAQUETADO - GestCoop
============================================

USO:
    .\build_and_package.ps1 [OPCIONES]

OPCIONES:
    -Clean      Limpiar directorios de salida antes de compilar
    -SkipTests  Omitir la ejecución de tests (activado por defecto)
    -Verbose    Mostrar salida detallada del proceso
    -Help       Mostrar esta ayuda

EJEMPLOS:
    .\build_and_package.ps1                    # Compilación básica
    .\build_and_package.ps1 -Clean             # Limpiar y compilar
    .\build_and_package.ps1 -Clean -Verbose    # Compilación completa con detalles

CARACTERÍSTICAS NUEVAS:
    ? Instalación automática de Java 21 si no está disponible
    ? Logging completo en archivo GestCoop.log
    ? Detección inteligente de versiones Java existentes
    ? Manejo robusto de errores con logging detallado

REQUISITOS:
    - Java 21 (se instala automáticamente si no está presente)
    - Maven 3.6+ instalado
    - ps2exe instalado (Install-Module ps2exe)
    - Conexión a Internet (para descarga automática de Java 21)

ARCHIVOS DE LOG:
    - GestCoop.log: Log del script de compilación
    - target/dist/GestCoop.log: Log del ejecutable en tiempo de ejecución

"@ "Cyan"
}

function Write-Log {
    param(
        [string]$Message,
        [string]$Level = "INFO"
    )
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp] [$Level] $Message"
    Add-Content -Path "GestCoop.log" -Value $logEntry -ErrorAction SilentlyContinue
}

function Initialize-Log {
    # Limpiar log anterior si existe y es muy grande (> 5MB)
    if (Test-Path "GestCoop.log") {
        $logFile = Get-Item "GestCoop.log"
        if ($logFile.Length -gt 5MB) {
            # Mantener solo las últimas 1000 líneas
            $content = Get-Content "GestCoop.log" -Tail 1000
            $content | Set-Content "GestCoop.log"
            Write-Log "Log anterior truncado - mantenidas últimas 1000 líneas" "INFO"
        }
    }
}

function Install-Java21 {
    Write-Step "Instalando Java 21"
    Write-Log "Iniciando instalación de Java 21" "INFO"
    
    try {
        $jdkInstallDir = "C:\Users\$env:USERNAME\.jdk"
        if (-not (Test-Path $jdkInstallDir)) {
            New-Item -ItemType Directory -Path $jdkInstallDir -Force | Out-Null
        }
        
        # URL de descarga de OpenJDK 21
        $jdkVersion = "21.0.8"
        $downloadUrl = "https://aka.ms/download-jdk/microsoft-jdk-21.0.8-windows-x64.zip"
        $zipFile = Join-Path $jdkInstallDir "jdk-21-windows.zip"
        $extractDir = Join-Path $jdkInstallDir "jdk-21.0.8"
        
        Write-ColorOutput "? Descargando OpenJDK 21 desde Microsoft..." "Yellow"
        Write-Log "Descargando Java 21 desde: $downloadUrl" "INFO"
        
        # Descargar Java 21
        try {
            Invoke-WebRequest -Uri $downloadUrl -OutFile $zipFile -UseBasicParsing
            Write-Success "Descarga completada"
            Write-Log "Java 21 descargado exitosamente" "INFO"
        } catch {
            Write-Error "Error al descargar Java 21: $_"
            Write-Log "Error al descargar Java 21: $_" "ERROR"
            throw
        }
        
        # Extraer el archivo ZIP
        Write-ColorOutput "? Extrayendo Java 21..." "Yellow"
        try {
            Expand-Archive -Path $zipFile -DestinationPath $jdkInstallDir -Force
            
            # Buscar el directorio extraído (puede tener un nombre ligeramente diferente)
            $extractedDirs = Get-ChildItem $jdkInstallDir -Directory | Where-Object { $_.Name -like "*jdk-21*" }
            if ($extractedDirs.Count -gt 0) {
                $actualExtractDir = $extractedDirs[0].FullName
                if ($actualExtractDir -ne $extractDir -and -not (Test-Path $extractDir)) {
                    Rename-Item $actualExtractDir $extractDir
                }
            }
            
            Write-Success "Extracción completada"
            Write-Log "Java 21 extraído exitosamente en: $extractDir" "INFO"
        } catch {
            Write-Error "Error al extraer Java 21: $_"
            Write-Log "Error al extraer Java 21: $_" "ERROR"
            throw
        }
        
        # Limpiar archivo ZIP
        Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
        
        # Verificar instalación
        $javaExePath = Join-Path $extractDir "bin\java.exe"
        if (Test-Path $javaExePath) {
            Write-Success "Java 21 instalado exitosamente en: $extractDir"
            Write-Log "Java 21 instalación completada: $javaExePath" "INFO"
            return $javaExePath
        } else {
            Write-Error "La instalación de Java 21 falló - ejecutable no encontrado"
            Write-Log "Instalación de Java 21 falló - ejecutable no encontrado en: $javaExePath" "ERROR"
            throw "Java installation failed"
        }
        
    } catch {
        Write-Error "Error durante la instalación de Java 21: $_"
        Write-Log "Error crítico durante instalación de Java 21: $_" "ERROR"
        throw
    }
}

function Test-Prerequisites {
    Write-Step "Verificando prerequisitos"
    Write-Log "Iniciando verificación de prerequisitos" "INFO"
    
    # Verificar Java
    $javaPath = $null
    $javaLocations = @(
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8\bin\java.exe",
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
    )
    
    foreach ($location in $javaLocations) {
        if (Test-Path $location) {
            $javaPath = $location
            Write-Log "Java encontrado en ubicación conocida: $location" "INFO"
            break
        }
    }
    
    if (-not $javaPath) {
        # Buscar en directorios de Java con comodines
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
                            $javaPath = $testPath
                            Write-Log "Java encontrado en búsqueda: $testPath" "INFO"
                            break
                        }
                    }
                    if ($javaPath) { break }
                }
            } catch {
                Write-Log "Error en búsqueda de Java en $searchPath`: $_" "WARNING"
            }
        }
    }
    
    if (-not $javaPath) {
        # Intentar encontrar java en PATH
        try {
            $javaVersion = & java -version 2>&1 | Select-String "version"
            if ($javaVersion -match '"(\d+)') {
                $majorVersion = [int]$Matches[1]
                if ($majorVersion -ge 21) {
                    $javaPath = (Get-Command java).Source
                    Write-Log "Java $majorVersion encontrado en PATH: $javaPath" "INFO"
                } else {
                    Write-Log "Java $majorVersion encontrado en PATH pero es menor a la versión 21" "WARNING"
                }
            }
        } catch {
            Write-Log "Java no encontrado en PATH" "WARNING"
        }
    }
    
    if (-not $javaPath) {
        Write-ColorOutput "? Java 21 no encontrado. Instalando automáticamente..." "Yellow"
        Write-Log "Java 21 no encontrado, iniciando instalación automática" "INFO"
        try {
            $javaPath = Install-Java21
        } catch {
            Write-Error "No se pudo instalar Java 21 automáticamente. Instale Java 21 manualmente."
            Write-Log "Falló la instalación automática de Java 21" "ERROR"
            exit 1
        }
    }
    
    Write-Success "Java encontrado: $javaPath"
    Write-Log "Java verificado exitosamente: $javaPath" "INFO"
    
    # Verificar Maven
    $mavenPath = $null
    $mavenLocations = @(
        "C:\Users\$env:USERNAME\.maven\maven-3.9.11(1)\bin\mvn.cmd"
    )
    
    foreach ($location in $mavenLocations) {
        if (Test-Path $location) {
            $mavenPath = $location
            Write-Log "Maven encontrado en ubicación conocida: $location" "INFO"
            break
        }
    }
    
    if (-not $mavenPath) {
        # Buscar Maven en ubicaciones comunes
        $mavenSearchPaths = @(
            "C:\Program Files\Apache\Maven",
            "C:\tools",
            "C:\Users\$env:USERNAME\.maven"
        )
        
        foreach ($searchPath in $mavenSearchPaths) {
            try {
                if (Test-Path $searchPath) {
                    $mavenDirs = Get-ChildItem $searchPath -Directory -Name "apache-maven-*", "maven-*" -ErrorAction SilentlyContinue
                    foreach ($mavenDir in $mavenDirs) {
                        $testPath = Join-Path $searchPath $mavenDir "bin\mvn.cmd"
                        if (Test-Path $testPath) {
                            $mavenPath = $testPath
                            Write-Log "Maven encontrado en búsqueda: $testPath" "INFO"
                            break
                        }
                    }
                    if ($mavenPath) { break }
                }
            } catch {
                Write-Log "Error en búsqueda de Maven en $searchPath`: $_" "WARNING"
            }
        }
    }
    
    if (-not $mavenPath) {
        try {
            $mavenVersion = & mvn --version 2>&1
            if ($mavenVersion) {
                $mavenPath = (Get-Command mvn).Source
                Write-Log "Maven encontrado en PATH: $mavenPath" "INFO"
            }
        } catch {
            Write-Log "Maven no encontrado en PATH" "WARNING"
        }
    }
    
    if (-not $mavenPath) {
        Write-Error "Maven no encontrado. Instalar Apache Maven 3.6+ o superior."
        Write-Log "Maven no encontrado - instalación requerida" "ERROR"
        exit 1
    }
    Write-Success "Maven encontrado: $mavenPath"
    Write-Log "Maven verificado exitosamente: $mavenPath" "INFO"
    
    # Verificar ps2exe
    try {
        Get-Command ps2exe -ErrorAction Stop | Out-Null
        Write-Success "ps2exe disponible"
        Write-Log "ps2exe verificado exitosamente" "INFO"
    } catch {
        Write-Error "ps2exe no encontrado. Ejecutar: Install-Module ps2exe"
        Write-Log "ps2exe no encontrado - instalación requerida" "ERROR"
        exit 1
    }
    
    Write-Log "Verificación de prerequisitos completada exitosamente" "INFO"
    return @{ Java = $javaPath; Maven = $mavenPath }
}

function Set-Environment {
    param($JavaPath)
    
    Write-Step "Configurando variables de entorno"
    Write-Log "Configurando variables de entorno para Java: $JavaPath" "INFO"
    
    $javaHome = Split-Path (Split-Path $JavaPath)
    $env:JAVA_HOME = $javaHome
    $env:PATH = "$javaHome\bin;" + $env:PATH
    
    Write-Success "JAVA_HOME: $javaHome"
    Write-Log "JAVA_HOME configurado: $javaHome" "INFO"
}

function Clean-Directories {
    Write-Step "Limpiando directorios de salida"
    Write-Log "Iniciando limpieza de directorios" "INFO"
    
    $directories = @(
        "target",
        "bin"
    )
    
    foreach ($dir in $directories) {
        if (Test-Path $dir) {
            Remove-Item $dir -Recurse -Force
            Write-Success "Eliminado: $dir"
            Write-Log "Directorio eliminado: $dir" "INFO"
        }
    }
}

function Build-Project {
    param($MavenPath, $SkipTests)
    
    Write-Step "Compilando proyecto con Maven"
    Write-Log "Iniciando compilación del proyecto" "INFO"
    
    $goals = "clean package"
    if ($SkipTests) {
        $goals += " -DskipTests"
    }
    
    $verboseFlag = if ($Verbose) { "" } else { "-q" }
    
    try {
        & $MavenPath $goals.Split() $verboseFlag
        Write-Success "Compilación completada"
        Write-Log "Compilación del proyecto completada exitosamente" "INFO"
    } catch {
        Write-Error "Error en la compilación: $_"
        Write-Log "Error en la compilación del proyecto: $_" "ERROR"
        exit 1
    }
}

function Create-LauncherScript {
    # Suprimir salida en el pipeline
    [void](Write-Step "Creando script lanzador")
    
    $distDir = "target\dist"
    
    # Verificar que el directorio existe
    if (-not (Test-Path $distDir)) {
        Write-Error "Directorio de distribución no encontrado: $distDir"
        exit 1
    }
    $launcherScript = @"
# GestCoop Launcher - Version Ultra Simple con Logging
`$ErrorActionPreference = "SilentlyContinue"

# Función para escribir al log con mejor manejo de errores
function Write-AppLog(`$message, `$level = "INFO") {
    `$timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    `$logEntry = "[`$timestamp] [`$level] `$message"
    try {
        `$logPath = Join-Path `$ScriptDir "GestCoop.log"
        Add-Content -Path `$logPath -Value `$logEntry -Encoding UTF8 -ErrorAction SilentlyContinue
        # También escribir a la consola en modo debug
        if (`$env:GESTCOOP_DEBUG -eq "1") {
            Write-Host `$logEntry -ForegroundColor Gray
        }
    } catch {
        # Intentar escribir al directorio temporal como fallback
        try {
            `$tempLogPath = Join-Path `$env:TEMP "GestCoop_error.log"
            Add-Content -Path `$tempLogPath -Value `$logEntry -Encoding UTF8 -ErrorAction SilentlyContinue
        } catch {
            # Si todo falla, ignorar
        }
    }
}

# Obtener directorio del script
`$ScriptDir = if (`$MyInvocation.MyCommand.Path) { 
    Split-Path -Parent `$MyInvocation.MyCommand.Path 
} elseif (`$PSScriptRoot) { 
    `$PSScriptRoot 
} else {
    # Para ejecutables ps2exe, usar la ubicación del ejecutable
    try {
        `$exeLocation = [System.Reflection.Assembly]::GetExecutingAssembly().Location
        if (`$exeLocation) {
            Split-Path -Parent `$exeLocation
        } else {
            Get-Location | Select-Object -ExpandProperty Path
        }
    } catch {
        # Fallback al directorio actual
        Get-Location | Select-Object -ExpandProperty Path
    }
}

Write-AppLog "Iniciando GestCoop desde: `$ScriptDir" "INFO"

# Cargar Windows Forms para mostrar mensajes
try {
    Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue
    Add-Type -AssemblyName System.Drawing -ErrorAction SilentlyContinue
} catch {
    Write-AppLog "Error al cargar Windows Forms: `$(`$_.Exception.Message)" "WARNING"
}

# Función mejorada para mostrar mensajes con debug
function Show-Error(`$msg) {
    Write-AppLog "ERROR: `$msg" "ERROR"
    Write-AppLog "Stack trace: `$(`$Error[0].ScriptStackTrace)" "ERROR"
    Write-AppLog "Exception details: `$(`$Error[0].Exception.ToString())" "ERROR"
    
    try {
        # Crear mensaje más detallado para debug
        `$debugMsg = `$msg
        if (`$env:GESTCOOP_DEBUG -eq "1") {
            `$debugMsg += "`n`nDetalles técnicos:`n"
            `$debugMsg += "- Directorio de trabajo: `$ScriptDir`n"
            `$debugMsg += "- Usuario: `$env:USERNAME`n"
            `$debugMsg += "- Máquina: `$env:COMPUTERNAME`n"
            `$debugMsg += "- PowerShell: `$(`$PSVersionTable.PSVersion)`n"
            `$debugMsg += "`nVer log completo en: `$(Join-Path `$ScriptDir 'GestCoop.log')"
        }
        
        [System.Windows.Forms.MessageBox]::Show(`$debugMsg, "GestCoop Error", "OK", "Error")
    } catch {
        Write-Host "ERROR: `$msg" -ForegroundColor Red
        Write-Host "Log ubicado en: `$(Join-Path `$ScriptDir 'GestCoop.log')" -ForegroundColor Yellow
    }
}

# Función para instalar Java 21 automáticamente
function Install-Java21-Auto {
    Write-AppLog "Iniciando instalación automática de Java 21" "INFO"
    
    try {
        `$jdkInstallDir = "C:\Users\`$env:USERNAME\.jdk"
        if (-not (Test-Path `$jdkInstallDir)) {
            New-Item -ItemType Directory -Path `$jdkInstallDir -Force | Out-Null
        }
        
        `$downloadUrl = "https://aka.ms/download-jdk/microsoft-jdk-21.0.8-windows-x64.zip"
        `$zipFile = Join-Path `$jdkInstallDir "jdk-21-windows.zip"
        `$extractDir = Join-Path `$jdkInstallDir "jdk-21.0.8"
        
        Write-AppLog "Descargando Java 21 desde: `$downloadUrl" "INFO"
        
        # Mostrar progreso al usuario
        try {
            [System.Windows.Forms.MessageBox]::Show("Descargando Java 21. Esto puede tomar unos minutos...", "GestCoop", "OK", "Information")
        } catch {}
        
        # Descargar Java 21
        Invoke-WebRequest -Uri `$downloadUrl -OutFile `$zipFile -UseBasicParsing
        Write-AppLog "Java 21 descargado exitosamente" "INFO"
        
        # Extraer el archivo ZIP
        Write-AppLog "Extrayendo Java 21..." "INFO"
        Expand-Archive -Path `$zipFile -DestinationPath `$jdkInstallDir -Force
        
        # Buscar el directorio extraído
        `$extractedDirs = Get-ChildItem `$jdkInstallDir -Directory | Where-Object { `$_.Name -like "*jdk-21*" }
        if (`$extractedDirs.Count -gt 0) {
            `$actualExtractDir = `$extractedDirs[0].FullName
            if (`$actualExtractDir -ne `$extractDir -and -not (Test-Path `$extractDir)) {
                Rename-Item `$actualExtractDir `$extractDir
            }
        }
        
        # Limpiar archivo ZIP
        Remove-Item `$zipFile -Force -ErrorAction SilentlyContinue
        
        # Verificar instalación
        `$javaExePath = Join-Path `$extractDir "bin\java.exe"
        if (Test-Path `$javaExePath) {
            Write-AppLog "Java 21 instalado exitosamente en: `$extractDir" "INFO"
            return `$javaExePath
        } else {
            Write-AppLog "La instalación de Java 21 falló - ejecutable no encontrado en: `$javaExePath" "ERROR"
            return `$null
        }
        
    } catch {
        Write-AppLog "Error durante la instalación de Java 21: `$(`$_.Exception.Message)" "ERROR"
        return `$null
    }
}

# Buscar Java de forma simple
`$javaExe = `$null

# Probar rutas conocidas
`$knownPaths = @(
    "C:\Users\`$env:USERNAME\.jdk\jdk-21.0.8\bin\java.exe",
    "C:\Users\`$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
)

foreach (`$knownPath in `$knownPaths) {
    if ([System.IO.File]::Exists(`$knownPath)) {
        `$javaExe = `$knownPath
        Write-AppLog "Java encontrado en ubicación conocida: `$knownPath" "INFO"
        break
    }
}

# Si no se encuentra, probar en PATH
if (-not `$javaExe) {
    try {
        `$javaTest = Get-Command java -ErrorAction Stop 2>`$null
        if (`$javaTest) {
            `$javaExe = `$javaTest.Source
            Write-AppLog "Java encontrado en PATH: `$javaExe" "INFO"
        }
    } catch {
        Write-AppLog "Java no encontrado en PATH" "WARNING"
    }
}

# Buscar en directorios comunes
if (-not `$javaExe) {
    `$javaSearchPaths = @(
        "C:\Program Files\Java\jdk-21*\bin\java.exe",
        "C:\Program Files (x86)\Java\jdk-21*\bin\java.exe",
        "C:\Users\`$env:USERNAME\.jdk\jdk-21*\bin\java.exe"
    )
    
    foreach (`$searchPath in `$javaSearchPaths) {
        `$parentDir = Split-Path (Split-Path `$searchPath)
        if (Test-Path `$parentDir) {
            `$pattern = Split-Path (Split-Path `$searchPath) -Leaf
            `$javaDirs = Get-ChildItem `$parentDir -Directory | Where-Object { `$_.Name -like `$pattern }
            foreach (`$javaDir in `$javaDirs) {
                `$testPath = Join-Path `$javaDir.FullName "bin\java.exe"
                if (Test-Path `$testPath) {
                    `$javaExe = `$testPath
                    Write-AppLog "Java encontrado en búsqueda: `$testPath" "INFO"
                    break
                }
            }
            if (`$javaExe) { break }
        }
    }
}

# Si no encontramos Java, intentar instalación automática
if (-not `$javaExe) {
    Write-AppLog "Java 21 no encontrado, iniciando instalación automática" "INFO"
    `$javaExe = Install-Java21-Auto
}

# Verificar que encontramos Java
if (-not `$javaExe) {
    `$errorMsg = "No se encontró Java 21+ y no se pudo instalar automáticamente. Por favor instale Java 21 manualmente."
    Show-Error `$errorMsg
    Write-AppLog `$errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando Java: `$javaExe" "INFO"

# Verificar que existe el JAR
`$jarPath = Join-Path `$ScriptDir "GestCoop.jar"
if (-not [System.IO.File]::Exists(`$jarPath)) {
    `$errorMsg = "No se encontró GestCoop.jar en `$ScriptDir"
    Show-Error `$errorMsg
    Write-AppLog `$errorMsg "ERROR"
    exit 1
}

Write-AppLog "Usando JAR: `$jarPath" "INFO"

# Validar requisitos antes del inicio
Write-AppLog "Validando requisitos previos al inicio..." "INFO"

# Verificar versión de Java
try {
    `$javaVersionOutput = & `$javaExe -version 2>&1
    Write-AppLog "Salida de versión Java: `$javaVersionOutput" "INFO"
    
    if (`$javaVersionOutput -match 'version "(\d+)') {
        `$javaVersion = `$Matches[1]
        Write-AppLog "Versión de Java detectada: `$javaVersion" "INFO"
        if ([int]`$javaVersion -lt 21) {
            `$errorMsg = "Java `$javaVersion detectado, pero se requiere Java 21 o superior"
            Show-Error `$errorMsg
            Write-AppLog `$errorMsg "ERROR"
            exit 1
        }
    }
} catch {
    Write-AppLog "Error verificando versión de Java: `$(`$_.Exception.Message)" "ERROR"
}

# Verificar permisos de directorio
try {
    `$testFile = Join-Path `$ScriptDir "test_permissions.tmp"
    "test" | Out-File -FilePath `$testFile -ErrorAction Stop
    Remove-Item `$testFile -ErrorAction SilentlyContinue
    Write-AppLog "Permisos de escritura en directorio verificados" "INFO"
} catch {
    Write-AppLog "Advertencia: Posibles problemas de permisos en directorio `$ScriptDir`: `$(`$_.Exception.Message)" "WARNING"
}

# Iniciar la aplicación con manejo robusto de errores
try {
    Write-AppLog "Iniciando aplicación GestCoop..." "INFO"
    Write-AppLog "Comando: `$javaExe -jar `"`$jarPath`"" "INFO"
    Write-AppLog "Directorio de trabajo: `$ScriptDir" "INFO"
    
    `$startInfo = New-Object System.Diagnostics.ProcessStartInfo
    `$startInfo.FileName = `$javaExe
    `$startInfo.Arguments = "-jar ```"`$jarPath```""
    `$startInfo.WorkingDirectory = `$ScriptDir
    `$startInfo.UseShellExecute = `$false
    `$startInfo.CreateNoWindow = `$true
    `$startInfo.RedirectStandardOutput = `$true
    `$startInfo.RedirectStandardError = `$true
    
    Write-AppLog "Configuración de proceso completada" "INFO"
    
    `$process = [System.Diagnostics.Process]::Start(`$startInfo)
    
    if (`$process) {
        Write-AppLog "Proceso iniciado exitosamente. PID: `$(`$process.Id)" "INFO"
        
        # Esperar un poco y verificar que el proceso sigue ejecutándose
        Start-Sleep -Milliseconds 2000
        
        if (-not `$process.HasExited) {
            Write-AppLog "GestCoop ejecutándose correctamente después de 2 segundos" "INFO"
            exit 0
        } else {
            # El proceso terminó prematuramente, capturar salida de error
            `$stdout = `$process.StandardOutput.ReadToEnd()
            `$stderr = `$process.StandardError.ReadToEnd()
            `$exitCode = `$process.ExitCode
            
            Write-AppLog "Proceso terminó prematuramente con código: `$exitCode" "ERROR"
            Write-AppLog "STDOUT: `$stdout" "ERROR"
            Write-AppLog "STDERR: `$stderr" "ERROR"
            
            `$errorMsg = "GestCoop se cerró inesperadamente.`nCódigo de salida: `$exitCode"
            if (`$stderr) {
                `$errorMsg += "`nError: `$stderr"
            }
            if (`$stdout) {
                `$errorMsg += "`nSalida: `$stdout"
            }
            
            Show-Error `$errorMsg
            exit 1
        }
    } else {
        `$errorMsg = "No se pudo iniciar GestCoop - Process.Start() devolvió null"
        Show-Error `$errorMsg
        Write-AppLog `$errorMsg "ERROR"
        exit 1
    }
} catch {
    `$errorMsg = "Error crítico al iniciar GestCoop: `$(`$_.Exception.Message)"
    Write-AppLog "Excepción completa: `$(`$_.Exception.ToString())" "ERROR"
    Write-AppLog "Información del sistema: PowerShell `$(`$PSVersionTable.PSVersion), .NET `$(`$PSVersionTable.CLRVersion)" "ERROR"
    Show-Error `$errorMsg
    exit 1
}
"@
    
    $launcherPath = Join-Path $distDir "GestCoop_Launcher.ps1"
    $launcherScript | Out-File -FilePath $launcherPath -Encoding UTF8
    Write-Host "? Script lanzador creado: $launcherPath" -ForegroundColor Green
    
    return $launcherPath
}

function Create-Executable {
    param($LauncherPath)
    
    # Validar que el launcher path existe
    if ([string]::IsNullOrEmpty($LauncherPath) -or -not (Test-Path $LauncherPath)) {
        Write-Error "Script lanzador no encontrado: $LauncherPath"
        exit 1
    }
    
    $distDir = Split-Path $LauncherPath
    $exePath = Join-Path $distDir "GestCoop.exe"
    
    # Eliminar ejecutable anterior si existe
    if (Test-Path $exePath) {
        Remove-Item $exePath -Force
    }
    
    try {
        # Ejecutar ps2exe y capturar la salida
        $result = ps2exe -inputFile $LauncherPath -outputFile $exePath `
               -title "GestCoop" `
               -description "GestCoop - Sistema de Gestión Cooperativa" `
               -company "CoopManager" `
               -product "GestCoop" `
               -version "1.0.1" `
               -noConsole:$true 2>&1
               
        # Verificar que el ejecutable se creó
        if (Test-Path $exePath) {
            Write-Host "? Ejecutable generado: $exePath" -ForegroundColor Green
            return $exePath
        } else {
            Write-Error "ps2exe no generó el archivo ejecutable. Salida: $result"
            exit 1
        }
    } catch {
        Write-Error "Error al generar ejecutable: $_"
        exit 1
    }
}

function Create-ReportsPackage {
    param($DistDir)
    
    Write-Step "Creando paquete de reportes"
    
    $reportsDestDir = Join-Path $DistDir "reportsPackage"
    
    # Crear directorio de reportes si no existe
    if (-not (Test-Path $reportsDestDir)) {
        New-Item -ItemType Directory -Path $reportsDestDir -Force | Out-Null
        Write-Host "? Directorio creado: $reportsDestDir" -ForegroundColor Green
    }
    
    # Buscar archivos de reportes en las ubicaciones fuente
    $sourceLocations = @(
        "src\reportsPackage",
        "target\classes\reportsPackage"
    )
    
    $copiedFiles = @()
    
    foreach ($sourceLocation in $sourceLocations) {
        if (Test-Path $sourceLocation) {
            # Buscar archivos .jrxml, .jasper y .jpg
            $reportFiles = Get-ChildItem $sourceLocation -Include "*.jrxml", "*.jasper", "*.jpg" -Recurse
            
            foreach ($reportFile in $reportFiles) {
                $destFile = Join-Path $reportsDestDir $reportFile.Name
                
                # Solo copiar si el archivo no existe o es más nuevo
                $shouldCopy = $true
                if (Test-Path $destFile) {
                    $existingFile = Get-Item $destFile
                    if ($existingFile.LastWriteTime -ge $reportFile.LastWriteTime) {
                        $shouldCopy = $false
                    }
                }
                
                if ($shouldCopy) {
                    try {
                        Copy-Item $reportFile.FullName $destFile -Force
                        if ($copiedFiles -notcontains $reportFile.Name) {
                            $copiedFiles += $reportFile.Name
                        }
                    } catch {
                        Write-Warning "No se pudo copiar $($reportFile.Name): $_"
                    }
                }
            }
        }
    }
    
    # Mostrar resumen
    if ($copiedFiles.Count -gt 0) {
        Write-Host "? Archivos de reportes copiados: $($copiedFiles.Count)" -ForegroundColor Green
        foreach ($file in ($copiedFiles | Sort-Object)) {
            Write-Host "  - $file" -ForegroundColor Gray
        }
    } else {
        Write-Host "! No se encontraron nuevos archivos de reportes para copiar" -ForegroundColor Yellow
    }
    
    # Verificar contenido final del directorio
    $finalFiles = Get-ChildItem $reportsDestDir -File
    $jrxmlFiles = $finalFiles | Where-Object { $_.Extension -eq '.jrxml' }
    $jasperFiles = $finalFiles | Where-Object { $_.Extension -eq '.jasper' }
    $jpgFiles = $finalFiles | Where-Object { $_.Extension -eq '.jpg' }
    Write-Host "? Total de archivos en reportsPackage: $($finalFiles.Count) (.jrxml: $($jrxmlFiles.Count), .jasper: $($jasperFiles.Count), .jpg: $($jpgFiles.Count))" -ForegroundColor Green
    
    return $reportsDestDir
}

function Test-Application {
    param($ExePath)
    
    [void](Write-Step "Verificando aplicación generada")
    
    $distDir = Split-Path $ExePath
    $jarFile = Join-Path $distDir "GestCoop.jar"
    $libDir = Join-Path $distDir "GestCoop_lib"
    $reportsDir = Join-Path $distDir "reportsPackage"
    
    # Verificar archivos necesarios
    $requiredFiles = @(
        @{ Path = $jarFile; Description = "JAR principal" },
        @{ Path = $libDir; Description = "Directorio de dependencias" },
        @{ Path = $reportsDir; Description = "Paquete de reportes" },
        @{ Path = $ExePath; Description = "Ejecutable" }
    )
    
    foreach ($file in $requiredFiles) {
        if (Test-Path $file.Path) {
            Write-Host "? $($file.Description): ?" -ForegroundColor Green
        } else {
            Write-Host "? $($file.Description): ? No encontrado" -ForegroundColor Red
        }
    }
    
    # Mostrar información del ejecutable
    if (Test-Path $ExePath) {
        $fileInfo = Get-Item $ExePath
        Write-Host "? Tamaño del ejecutable: $([math]::Round($fileInfo.Length / 1KB, 2)) KB" -ForegroundColor Green
        Write-Host "? Fecha de creación: $($fileInfo.CreationTime)" -ForegroundColor Green
    }
}

function Show-Summary {
    param($ExePath, $StartTime)
    
    $duration = (Get-Date) - $StartTime
    $distDir = Split-Path $ExePath
    
    Write-Header "RESUMEN DE COMPILACIÓN"
    
    Write-ColorOutput "?? Directorio de salida: $distDir" "Green"
    Write-ColorOutput "?? Ejecutable: $(Split-Path $ExePath -Leaf)" "Green"
    Write-ColorOutput "??  Tiempo de compilación: $([math]::Round($duration.TotalSeconds, 1)) segundos" "Green"
    
    Write-ColorOutput "`n?? ARCHIVOS GENERADOS:" "Yellow"
    Get-ChildItem $distDir | ForEach-Object {
        $size = if ($_.PSIsContainer) { "<DIR>" } else { "$([math]::Round($_.Length / 1KB, 1)) KB" }
        Write-ColorOutput "   $($_.Name) [$size]" "White"
    }
    
    Write-ColorOutput "`n?? SIGUIENTE PASO:" "Cyan"
    Write-ColorOutput "   Ejecutar: $ExePath" "White"
    Write-ColorOutput "   O hacer doble clic en GestCoop.exe desde el explorador" "White"
}

# =============================================================================
# FUNCIÓN PRINCIPAL
# =============================================================================

function Main {
    $startTime = Get-Date
    
    # Inicializar logging (limpiar log si es muy grande)
    Initialize-Log
    
    # Inicializar logging
    Write-Log "===========================================" "INFO"
    Write-Log "Iniciando script de compilación GestCoop v1.0.0" "INFO"
    Write-Log "Fecha/Hora: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')" "INFO"
    Write-Log "Usuario: $env:USERNAME" "INFO"
    Write-Log "Máquina: $env:COMPUTERNAME" "INFO"
    Write-Log "===========================================" "INFO"
    
    if ($Help) {
        Show-Help
        return
    }
    
    Write-Header "SCRIPT DE COMPILACIÓN Y EMPAQUETADO - GestCoop v1.0.0"
    
    try {
        # Cambiar al directorio raíz del proyecto (desde el directorio raíz, no desde src)
        $projectRoot = $PSScriptRoot
        Set-Location $projectRoot
        Write-Success "Directorio de trabajo: $projectRoot"
        Write-Log "Directorio de trabajo establecido: $projectRoot" "INFO"
        
        # Verificar prerequisitos
        $tools = Test-Prerequisites
        
        # Configurar entorno
        Set-Environment -JavaPath $tools.Java
        
        # Limpiar si se solicita
        if ($Clean) {
            Clean-Directories
        }
        
        # Compilar proyecto
        Build-Project -MavenPath $tools.Maven -SkipTests $SkipTests
        
        # Verificar que el directorio de distribución existe
        $distDir = "target\dist"
        if (-not (Test-Path $distDir)) {
            Write-Error "El directorio de distribución no se creó: $distDir"
            Write-Log "ERROR: Directorio de distribución no creado: $distDir" "ERROR"
            exit 1
        }
        Write-Success "Directorio de distribución confirmado: $distDir"
        Write-Log "Directorio de distribución confirmado: $distDir" "INFO"
        
        # Crear script lanzador
        Write-Host "? Iniciando creación del script lanzador..." -ForegroundColor Yellow
        $launcherPath = Create-LauncherScript
        Write-Host "? Script lanzador completado: $launcherPath" -ForegroundColor Green
        Write-Log "Script lanzador creado exitosamente: $launcherPath" "INFO"
        
        # Generar ejecutable
        Write-Host "? Iniciando generación del ejecutable..." -ForegroundColor Yellow
        $exePath = Create-Executable -LauncherPath $launcherPath
        Write-Host "? Ejecutable completado: $exePath" -ForegroundColor Green
        Write-Log "Ejecutable generado exitosamente: $exePath" "INFO"
        
        # Crear paquete de reportes
        Write-Host "? Iniciando creación del paquete de reportes..." -ForegroundColor Yellow
        $reportsDir = Create-ReportsPackage -DistDir $distDir
        Write-Host "? Paquete de reportes completado: $reportsDir" -ForegroundColor Green
        Write-Log "Paquete de reportes creado exitosamente: $reportsDir" "INFO"
        
        # Verificar aplicación
        Test-Application -ExePath $exePath
        
        # Mostrar resumen
        Show-Summary -ExePath $exePath -StartTime $startTime
        
        Write-ColorOutput "`n?? ¡COMPILACIÓN COMPLETADA EXITOSAMENTE!" "Green"
        Write-Log "Compilación completada exitosamente en $([math]::Round(((Get-Date) - $startTime).TotalSeconds, 1)) segundos" "INFO"
        Write-Log "===========================================" "INFO"
        
    } catch {
        Write-Error "`n?? Error durante la compilación: $_"
        Write-ColorOutput "Revise los logs anteriores para más detalles." "Red"
        Write-Log "ERROR CRÍTICO durante la compilación: $_" "ERROR"
        Write-Log "Stack trace: $($_.ScriptStackTrace)" "ERROR"
        Write-Log "===========================================" "ERROR"
        exit 1
    }
}

# =============================================================================
# EJECUCIÓN PRINCIPAL
# =============================================================================

# Ejecutar función principal
Main