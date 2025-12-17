# Script para verificar el estado de GestCoop
Write-Host "?? VERIFICANDO ESTADO DE GESTCOOP" -ForegroundColor Cyan
Write-Host "=================================" -ForegroundColor Cyan

# Verificar procesos Java
$javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue
if ($javaProcesses) {
    Write-Host "? Procesos Java encontrados:" -ForegroundColor Green
    foreach ($proc in $javaProcesses) {
        $startTime = $proc.StartTime.ToString("HH:mm:ss")
        $windowTitle = if ($proc.MainWindowTitle) { $proc.MainWindowTitle } else { "(Sin ventana)" }
        Write-Host "   PID: $($proc.Id) | Inicio: $startTime | Ventana: $windowTitle" -ForegroundColor Yellow
    }
} else {
    Write-Host "? No hay procesos Java ejecutándose" -ForegroundColor Red
}

# Verificar puertos de debug
Write-Host "`n?? Verificando puertos de debug:" -ForegroundColor Cyan
$debugPorts = @(5005, 5006, 8000)
foreach ($port in $debugPorts) {
    try {
        $connection = Test-NetConnection -ComputerName "localhost" -Port $port -WarningAction SilentlyContinue
        if ($connection.TcpTestSucceeded) {
            Write-Host "   Puerto $port`: ? ABIERTO" -ForegroundColor Green
        } else {
            Write-Host "   Puerto $port`: ? Cerrado" -ForegroundColor Gray
        }
    } catch {
        Write-Host "   Puerto $port`: ? Cerrado" -ForegroundColor Gray
    }
}

# Verificar archivos de GestCoop
Write-Host "`n?? Verificando archivos:" -ForegroundColor Cyan
$files = @("target\dist\GestCoop.jar", "target\dist\GestCoop.exe")
foreach ($file in $files) {
    if (Test-Path $file) {
        $size = [math]::Round((Get-Item $file).Length / 1MB, 2)
        Write-Host "   $file`: ? ($size MB)" -ForegroundColor Green
    } else {
        Write-Host "   $file`: ? No encontrado" -ForegroundColor Red
    }
}

# Verificar logs
Write-Host "`n?? Verificando logs:" -ForegroundColor Cyan
$logFiles = @("GestCoop.log", "target\dist\GestCoop.log", "gc.log")
foreach ($logFile in $logFiles) {
    if (Test-Path $logFile) {
        $lastWrite = (Get-Item $logFile).LastWriteTime.ToString("HH:mm:ss")
        Write-Host "   $logFile`: ? (Modificado: $lastWrite)" -ForegroundColor Green
        
        # Mostrar últimas líneas
        Write-Host "     Últimas líneas:" -ForegroundColor Gray
        Get-Content $logFile -Tail 3 | ForEach-Object { Write-Host "       $_" -ForegroundColor Gray }
    } else {
        Write-Host "   $logFile`: ? No encontrado" -ForegroundColor Gray
    }
}

# Verificar ventanas con GestCoop en el título
Write-Host "`n?? Buscando ventanas de GestCoop:" -ForegroundColor Cyan
$allProcesses = Get-Process | Where-Object { $_.MainWindowTitle -ne "" }
$gestcoopWindows = $allProcesses | Where-Object { $_.MainWindowTitle -like "*GestCoop*" -or $_.MainWindowTitle -like "*Coop*" }

if ($gestcoopWindows) {
    Write-Host "? Ventanas relacionadas encontradas:" -ForegroundColor Green
    foreach ($window in $gestcoopWindows) {
        Write-Host "   Proceso`: $($window.ProcessName) | Título: $($window.MainWindowTitle)" -ForegroundColor Yellow
    }
} else {
    Write-Host "? No se encontraron ventanas de GestCoop" -ForegroundColor Red
}

# Mostrar todas las ventanas Java
$javaWindows = $allProcesses | Where-Object { $_.ProcessName -eq "java" }
if ($javaWindows) {
    Write-Host "`n? Ventanas Java activas:" -ForegroundColor Cyan
    foreach ($window in $javaWindows) {
        Write-Host "   PID`: $($window.Id) | Título: $($window.MainWindowTitle)" -ForegroundColor Yellow
    }
}

Write-Host "`n=== VERIFICACIÓN COMPLETADA ===" -ForegroundColor Cyan

# Sugerencias
Write-Host "`n?? SUGERENCIAS:" -ForegroundColor Yellow
if (-not $javaProcesses) {
    Write-Host "   ? Ejecute .\Debug-GestCoop.ps1 para iniciar la aplicación" -ForegroundColor Gray
}
if ($javaProcesses -and -not $gestcoopWindows) {
    Write-Host "   ? La aplicación podría estar iniciando - espere unos segundos" -ForegroundColor Gray
    Write-Host "   ? Verifique los logs para errores de GUI" -ForegroundColor Gray
    Write-Host "   ? Pruebe: Alt+Tab para ver si la ventana está minimizada" -ForegroundColor Gray
}