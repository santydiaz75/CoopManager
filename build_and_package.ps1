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

REQUISITOS:
    - Java 21 instalado
    - Maven 3.6+ instalado
    - ps2exe instalado (Install-Module ps2exe)

"@ "Cyan"
}

function Test-Prerequisites {
    Write-Step "Verificando prerequisitos"
    
    # Verificar Java
    $javaPath = $null
    $javaLocations = @(
        "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
    )
    
    foreach ($location in $javaLocations) {
        if (Test-Path $location) {
            $javaPath = $location
            break
        }
    }
    
    if (-not $javaPath) {
        # Buscar en directorios de Java con comodines
        $javaSearchPaths = @(
            "C:\Program Files\Java\jdk-21*",
            "C:\Program Files (x86)\Java\jdk-21*"
        )
        
        foreach ($searchPath in $javaSearchPaths) {
            try {
                $javaDir = Get-ChildItem (Split-Path $searchPath) -Directory -Name (Split-Path $searchPath -Leaf) -ErrorAction SilentlyContinue | Select-Object -First 1
                if ($javaDir) {
                    $testPath = Join-Path (Split-Path $searchPath) $javaDir "bin\java.exe"
                    if (Test-Path $testPath) {
                        $javaPath = $testPath
                        break
                    }
                }
            } catch {
                # Continuar buscando
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
                }
            }
        } catch {
            # Java no encontrado en PATH
        }
    }
    
    if (-not $javaPath) {
        Write-Error "Java 21+ no encontrado. Instalar Java 21 o superior."
        exit 1
    }
    Write-Success "Java encontrado: $javaPath"
    
    # Verificar Maven
    $mavenPath = $null
    $mavenLocations = @(
        "C:\Users\$env:USERNAME\.maven\maven-3.9.11(1)\bin\mvn.cmd"
    )
    
    foreach ($location in $mavenLocations) {
        if (Test-Path $location) {
            $mavenPath = $location
            break
        }
    }
    
    if (-not $mavenPath) {
        # Buscar Maven en ubicaciones comunes
        $mavenSearchPaths = @(
            "C:\Program Files\Apache\Maven",
            "C:\tools"
        )
        
        foreach ($searchPath in $mavenSearchPaths) {
            try {
                if (Test-Path $searchPath) {
                    $mavenDirs = Get-ChildItem $searchPath -Directory -Name "apache-maven-*", "maven-*" -ErrorAction SilentlyContinue
                    foreach ($mavenDir in $mavenDirs) {
                        $testPath = Join-Path $searchPath $mavenDir "bin\mvn.cmd"
                        if (Test-Path $testPath) {
                            $mavenPath = $testPath
                            break
                        }
                    }
                    if ($mavenPath) { break }
                }
            } catch {
                # Continuar buscando
            }
        }
    }
    
    if (-not $mavenPath) {
        try {
            $mavenVersion = & mvn --version 2>&1
            if ($mavenVersion) {
                $mavenPath = (Get-Command mvn).Source
            }
        } catch {
            # Maven no encontrado en PATH
        }
    }
    
    if (-not $mavenPath) {
        Write-Error "Maven no encontrado. Instalar Apache Maven 3.6+ o superior."
        exit 1
    }
    Write-Success "Maven encontrado: $mavenPath"
    
    # Verificar ps2exe
    try {
        Get-Command ps2exe -ErrorAction Stop | Out-Null
        Write-Success "ps2exe disponible"
    } catch {
        Write-Error "ps2exe no encontrado. Ejecutar: Install-Module ps2exe"
        exit 1
    }
    
    return @{ Java = $javaPath; Maven = $mavenPath }
}

function Set-Environment {
    param($JavaPath)
    
    Write-Step "Configurando variables de entorno"
    
    $javaHome = Split-Path (Split-Path $JavaPath)
    $env:JAVA_HOME = $javaHome
    $env:PATH = "$javaHome\bin;" + $env:PATH
    
    Write-Success "JAVA_HOME: $javaHome"
}

function Clean-Directories {
    Write-Step "Limpiando directorios de salida"
    
    $directories = @(
        "target",
        "bin"
    )
    
    foreach ($dir in $directories) {
        if (Test-Path $dir) {
            Remove-Item $dir -Recurse -Force
            Write-Success "Eliminado: $dir"
        }
    }
}

function Build-Project {
    param($MavenPath, $SkipTests)
    
    Write-Step "Compilando proyecto con Maven"
    
    $goals = "clean package"
    if ($SkipTests) {
        $goals += " -DskipTests"
    }
    
    $verboseFlag = if ($Verbose) { "" } else { "-q" }
    
    try {
        & $MavenPath $goals.Split() $verboseFlag
        Write-Success "Compilación completada"
    } catch {
        Write-Error "Error en la compilación: $_"
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
# GestCoop Launcher - Version Ultra Simple
`$ErrorActionPreference = "SilentlyContinue"

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

# Cargar Windows Forms para mostrar mensajes
try {
    Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue
    Add-Type -AssemblyName System.Drawing -ErrorAction SilentlyContinue
} catch {
    # Ignorar errores
}

# Función simple para mostrar mensajes
function Show-Error(`$msg) {
    try {
        [System.Windows.Forms.MessageBox]::Show(`$msg, "GestCoop Error", "OK", "Error")
    } catch {
        Write-Host "ERROR: `$msg" -ForegroundColor Red
    }
}

# Buscar Java de forma simple
`$javaExe = `$null

# Probar ruta conocida
`$knownPath = "C:\Users\`$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
if ([System.IO.File]::Exists(`$knownPath)) {
    `$javaExe = `$knownPath
}

# Si no se encuentra, probar en PATH
if (-not `$javaExe) {
    try {
        `$javaTest = Get-Command java -ErrorAction Stop 2>`$null
        if (`$javaTest) {
            `$javaExe = `$javaTest.Source
        }
    } catch {
        # Ignorar
    }
}

# Verificar que encontramos Java
if (-not `$javaExe) {
    Show-Error "No se encontro Java 21+. Instale Java y vuelva a intentar."
    exit 1
}

# Verificar que existe el JAR
`$jarPath = Join-Path `$ScriptDir "GestCoop.jar"
if (-not [System.IO.File]::Exists(`$jarPath)) {
    Show-Error "No se encontro GestCoop.jar en `$ScriptDir"
    exit 1
}

# Iniciar la aplicación
try {
    `$startInfo = New-Object System.Diagnostics.ProcessStartInfo
    `$startInfo.FileName = `$javaExe
    `$startInfo.Arguments = "-jar ```"`$jarPath```""
    `$startInfo.WorkingDirectory = `$ScriptDir
    `$startInfo.UseShellExecute = `$false
    `$startInfo.CreateNoWindow = `$true
    
    `$process = [System.Diagnostics.Process]::Start(`$startInfo)
    
    if (`$process) {
        # Aplicación iniciada correctamente
        exit 0
    } else {
        Show-Error "No se pudo iniciar GestCoop"
        exit 1
    }
} catch {
    Show-Error "Error al iniciar GestCoop: `$(`$_.Exception.Message)"
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
            exit 1
        }
        Write-Success "Directorio de distribución confirmado: $distDir"
        
        # Crear script lanzador
        Write-Host "? Iniciando creación del script lanzador..." -ForegroundColor Yellow
        $launcherPath = Create-LauncherScript
        Write-Host "? Script lanzador completado: $launcherPath" -ForegroundColor Green
        
        # Generar ejecutable
        Write-Host "? Iniciando generación del ejecutable..." -ForegroundColor Yellow
        $exePath = Create-Executable -LauncherPath $launcherPath
        Write-Host "? Ejecutable completado: $exePath" -ForegroundColor Green
        
        # Crear paquete de reportes
        Write-Host "? Iniciando creación del paquete de reportes..." -ForegroundColor Yellow
        $reportsDir = Create-ReportsPackage -DistDir $distDir
        Write-Host "? Paquete de reportes completado: $reportsDir" -ForegroundColor Green
        
        # Verificar aplicación
        Test-Application -ExePath $exePath
        
        # Mostrar resumen
        Show-Summary -ExePath $exePath -StartTime $startTime
        
        Write-ColorOutput "`n?? ¡COMPILACIÓN COMPLETADA EXITOSAMENTE!" "Green"
        
    } catch {
        Write-Error "`n?? Error durante la compilación: $_"
        Write-ColorOutput "Revise los logs anteriores para más detalles." "Red"
        exit 1
    }
}

# =============================================================================
# EJECUCIÓN PRINCIPAL
# =============================================================================

# Ejecutar función principal
Main