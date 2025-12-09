# Script para descargar e instalar Java 21 SDK
# Ubicación: C:\Development\CoopManager\CoopManager
# Autor: Script automatizado para GestCoop

param(
    [string]$InstallPath = "C:\Development\Java\jdk-21",
    [switch]$SetJavaHome = $true,
    [switch]$UpdatePath = $true,
    [switch]$Verbose = $false
)

# Configurar preferencias
if ($Verbose) { $VerbosePreference = "Continue" }
$ErrorActionPreference = "Stop"

# Colores para output
function Write-ColorOutput {
    param([string]$Text, [string]$Color = "White")
    
    switch ($Color) {
        "Green" { Write-Host $Text -ForegroundColor Green }
        "Red" { Write-Host $Text -ForegroundColor Red }
        "Yellow" { Write-Host $Text -ForegroundColor Yellow }
        "Cyan" { Write-Host $Text -ForegroundColor Cyan }
        default { Write-Host $Text }
    }
}

# Función para verificar privilegios de administrador
function Test-IsAdmin {
    $currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

# Función para verificar Java existente
function Test-JavaInstalled {
    try {
        $javaVersion = java -version 2>&1 | Out-String
        if ($javaVersion -match "21\.|21\.0\.") {
            Write-ColorOutput "? Java 21 ya está instalado y configurado en PATH" "Green"
            Write-ColorOutput "Versión: $($javaVersion.Split("`n")[0])" "Cyan"
            return $true
        }
        elseif ($javaVersion -match "version") {
            Write-ColorOutput "??  Java está instalado pero no es versión 21:" "Yellow"
            Write-ColorOutput "Versión actual: $($javaVersion.Split("`n")[0])" "Cyan"
            return $false
        }
    }
    catch {
        Write-ColorOutput "? Java no está instalado o no está en PATH" "Red"
        return $false
    }
    return $false
}

# Función principal de instalación
function Install-Java21SDK {
    Write-ColorOutput "?? INSTALADOR DE JAVA 21 SDK" "Cyan"
    Write-ColorOutput "====================================" "Cyan"
    Write-ColorOutput "Directorio de instalación: $InstallPath" "Yellow"
    Write-ColorOutput ""

    # Verificar si ya existe Java 21
    if (Test-JavaInstalled) {
        $response = Read-Host "¿Desea reinstalar Java 21? (S/N)"
        if ($response -notmatch "^[Ss]") {
            Write-ColorOutput "Instalación cancelada por el usuario" "Yellow"
            return
        }
    }

    # Verificar privilegios de administrador
    if (-not (Test-IsAdmin) -and $SetJavaHome) {
        Write-ColorOutput "??  Se recomienda ejecutar como administrador para configurar variables de entorno del sistema" "Yellow"
        $response = Read-Host "¿Continuar con configuración solo para usuario actual? (S/N)"
        if ($response -notmatch "^[Ss]") {
            Write-ColorOutput "Instalación cancelada. Ejecute como administrador para configuración completa." "Red"
            return
        }
    }

    try {
        # URL de descarga de Microsoft OpenJDK 21
        $downloadUrl = "https://aka.ms/download-jdk/microsoft-jdk-21.0.8-windows-x64.zip"
        $zipFileName = "microsoft-jdk-21.0.8-windows-x64.zip"
        $downloadPath = Join-Path $env:TEMP $zipFileName
        
        Write-ColorOutput "?? Descargando Java 21 SDK..." "Cyan"
        Write-ColorOutput "URL: $downloadUrl" "Yellow"
        
        # Crear directorio de instalación si no existe
        if (-not (Test-Path $InstallPath)) {
            New-Item -Type Directory -Path $InstallPath -Force | Out-Null
            Write-ColorOutput "? Directorio de instalación creado: $InstallPath" "Green"
        }

        # Descargar archivo
        $webClient = New-Object System.Net.WebClient
        $webClient.DownloadFile($downloadUrl, $downloadPath)
        
        # Verificar descarga
        if (-not (Test-Path $downloadPath) -or (Get-Item $downloadPath).Length -lt 1MB) {
            throw "Error en la descarga: archivo no encontrado o corrupto"
        }
        
        $fileSize = [Math]::Round((Get-Item $downloadPath).Length / 1MB, 2)
        Write-ColorOutput "? Descarga completada: $fileSize MB" "Green"

        # Extraer archivo
        Write-ColorOutput "?? Extrayendo archivos..." "Cyan"
        
        Add-Type -AssemblyName System.IO.Compression.FileSystem
        [System.IO.Compression.ZipFile]::ExtractToDirectory($downloadPath, $InstallPath)
        
        # Buscar directorio JDK extraído
        $jdkDir = Get-ChildItem $InstallPath -Directory | Where-Object { $_.Name -match "jdk" } | Select-Object -First 1
        
        if (-not $jdkDir) {
            throw "No se encontró el directorio JDK después de la extracción"
        }
        
        $javaHome = $jdkDir.FullName
        Write-ColorOutput "? Extracción completada en: $javaHome" "Green"

        # Configurar variables de entorno
        if ($SetJavaHome) {
            Write-ColorOutput "??  Configurando variables de entorno..." "Cyan"
            
            try {
                if (Test-IsAdmin) {
                    # Configurar para todo el sistema
                    [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, [EnvironmentVariableTarget]::Machine)
                    Write-ColorOutput "? JAVA_HOME configurado para el sistema: $javaHome" "Green"
                } else {
                    # Configurar solo para usuario actual
                    [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, [EnvironmentVariableTarget]::User)
                    Write-ColorOutput "? JAVA_HOME configurado para usuario: $javaHome" "Green"
                }
            }
            catch {
                Write-ColorOutput "??  Error configurando JAVA_HOME: $($_.Exception.Message)" "Yellow"
            }
        }

        if ($UpdatePath) {
            Write-ColorOutput "?? Configurando PATH..." "Cyan"
            
            try {
                $javaBinPath = Join-Path $javaHome "bin"
                $target = if (Test-IsAdmin) { [EnvironmentVariableTarget]::Machine } else { [EnvironmentVariableTarget]::User }
                
                $currentPath = [Environment]::GetEnvironmentVariable("PATH", $target)
                
                # Eliminar referencias previas a Java
                $pathParts = $currentPath -split ";" | Where-Object { $_ -notmatch "java|jdk|jre" -and $_ -ne "" }
                
                # Agregar nueva ruta de Java al inicio
                $newPath = "$javaBinPath;" + ($pathParts -join ";")
                [Environment]::SetEnvironmentVariable("PATH", $newPath, $target)
                
                # Actualizar PATH de la sesión actual
                $env:PATH = "$javaBinPath;" + $env:PATH
                
                Write-ColorOutput "? PATH actualizado con: $javaBinPath" "Green"
            }
            catch {
                Write-ColorOutput "??  Error configurando PATH: $($_.Exception.Message)" "Yellow"
            }
        }

        # Limpiar archivo temporal
        if (Test-Path $downloadPath) {
            Remove-Item $downloadPath -Force
            Write-ColorOutput "?? Archivo temporal eliminado" "Green"
        }

        # Verificar instalación
        Write-ColorOutput "?? Verificando instalación..." "Cyan"
        
        # Reiniciar variables de entorno en la sesión actual
        $env:JAVA_HOME = $javaHome
        
        Start-Sleep -Seconds 2
        
        try {
            $javaExe = Join-Path $javaHome "bin\java.exe"
            if (Test-Path $javaExe) {
                $versionOutput = & $javaExe -version 2>&1 | Out-String
                Write-ColorOutput "? INSTALACIÓN EXITOSA!" "Green"
                Write-ColorOutput "====================================" "Green"
                Write-ColorOutput "?? Ubicación: $javaHome" "Cyan"
                Write-ColorOutput "???  Versión instalada:" "Cyan"
                Write-ColorOutput $versionOutput.Trim() "Yellow"
                Write-ColorOutput ""
                Write-ColorOutput "?? Para usar inmediatamente en esta terminal:" "Cyan"
                Write-ColorOutput "`$env:PATH = `"$javaBinPath;`" + `$env:PATH" "Yellow"
                Write-ColorOutput ""
                Write-ColorOutput "?? Reinicie su terminal o IDE para que los cambios surtan efecto completo" "Cyan"
            } else {
                throw "El archivo java.exe no se encontró en la ubicación esperada"
            }
        }
        catch {
            Write-ColorOutput "? Error verificando la instalación: $($_.Exception.Message)" "Red"
            Write-ColorOutput "Java podría estar instalado pero requiere configuración manual" "Yellow"
        }

    }
    catch {
        Write-ColorOutput "? ERROR EN LA INSTALACIÓN: $($_.Exception.Message)" "Red"
        Write-ColorOutput "Intente ejecutar el script como administrador o instalar manualmente" "Yellow"
        
        # Limpiar en caso de error
        if (Test-Path $downloadPath) {
            Remove-Item $downloadPath -Force -ErrorAction SilentlyContinue
        }
    }
}

# Función para mostrar ayuda
function Show-Help {
    Write-ColorOutput "?? AYUDA - INSTALADOR JAVA 21 SDK" "Cyan"
    Write-ColorOutput "====================================" "Cyan"
    Write-ColorOutput ""
    Write-ColorOutput "PARÁMETROS DISPONIBLES:" "Yellow"
    Write-ColorOutput "  -InstallPath    : Directorio donde instalar Java (Default: C:\Development\Java\jdk-21)" "White"
    Write-ColorOutput "  -SetJavaHome    : Configurar variable JAVA_HOME (Default: true)" "White"
    Write-ColorOutput "  -UpdatePath     : Actualizar variable PATH (Default: true)" "White"
    Write-ColorOutput "  -Verbose        : Mostrar información detallada (Default: false)" "White"
    Write-ColorOutput ""
    Write-ColorOutput "EJEMPLOS DE USO:" "Yellow"
    Write-ColorOutput "  .\Install-Java21-SDK.ps1" "Green"
    Write-ColorOutput "  .\Install-Java21-SDK.ps1 -InstallPath 'C:\Java\jdk21'" "Green"
    Write-ColorOutput "  .\Install-Java21-SDK.ps1 -Verbose" "Green"
    Write-ColorOutput "  .\Install-Java21-SDK.ps1 -SetJavaHome:`$false -UpdatePath:`$false" "Green"
    Write-ColorOutput ""
    Write-ColorOutput "REQUISITOS:" "Yellow"
    Write-ColorOutput "  ? Conexión a Internet para descargar Java" "White"
    Write-ColorOutput "  ? Privilegios de administrador (recomendado)" "White"
    Write-ColorOutput "  ? PowerShell 5.0 o superior" "White"
}

# Punto de entrada principal
if ($args -contains "-help" -or $args -contains "--help" -or $args -contains "/?") {
    Show-Help
} else {
    Install-Java21SDK
}