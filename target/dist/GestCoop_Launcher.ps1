# GestCoop Launcher - Version Ultra Simple
$ErrorActionPreference = "SilentlyContinue"

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

# Cargar Windows Forms para mostrar mensajes
try {
    Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue
    Add-Type -AssemblyName System.Drawing -ErrorAction SilentlyContinue
} catch {
    # Ignorar errores
}

# Funci�n simple para mostrar mensajes
function Show-Error($msg) {
    try {
        [System.Windows.Forms.MessageBox]::Show($msg, "GestCoop Error", "OK", "Error")
    } catch {
        Write-Host "ERROR: $msg" -ForegroundColor Red
    }
}

# Buscar Java de forma simple
$javaExe = $null

# Probar ruta conocida
$knownPath = "C:\Users\$env:USERNAME\.jdk\jdk-21.0.8(1)\bin\java.exe"
if ([System.IO.File]::Exists($knownPath)) {
    $javaExe = $knownPath
}

# Si no se encuentra, probar en PATH
if (-not $javaExe) {
    try {
        $javaTest = Get-Command java -ErrorAction Stop 2>$null
        if ($javaTest) {
            $javaExe = $javaTest.Source
        }
    } catch {
        # Ignorar
    }
}

# Verificar que encontramos Java
if (-not $javaExe) {
    Show-Error "No se encontro Java 21+. Instale Java y vuelva a intentar."
    exit 1
}

# Verificar que existe el JAR
$jarPath = Join-Path $ScriptDir "GestCoop.jar"
if (-not [System.IO.File]::Exists($jarPath)) {
    Show-Error "No se encontro GestCoop.jar en $ScriptDir"
    exit 1
}

# Iniciar la aplicaci�n
try {
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaExe
    $startInfo.Arguments = "-jar `"$jarPath`""
    $startInfo.WorkingDirectory = $ScriptDir
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $true
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    
    if ($process) {
        # Aplicaci�n iniciada correctamente
        exit 0
    } else {
        Show-Error "No se pudo iniciar GestCoop"
        exit 1
    }
} catch {
    Show-Error "Error al iniciar GestCoop: $($_.Exception.Message)"
    exit 1
}
