# Script para configurar Java 21 como versión predeterminada del sistema
# Este script soluciona permanentemente el problema de UnsupportedClassVersionError

Write-Host "=== CONFIGURANDO JAVA 21 COMO PREDETERMINADO ===" -ForegroundColor Cyan

# Verificar si ejecuta como administrador
$currentPrincipal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
$isAdmin = $currentPrincipal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if ($isAdmin) {
    Write-Host "? Ejecutándose como Administrador" -ForegroundColor Green
    
    # Configurar variables de entorno del sistema
    [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-21", [EnvironmentVariableTarget]::Machine)
    
    # Obtener PATH actual del sistema
    $systemPath = [Environment]::GetEnvironmentVariable("PATH", [EnvironmentVariableTarget]::Machine)
    
    # Remover referencias a Java antiguas
    $pathParts = $systemPath -split ";" | Where-Object { $_ -notmatch "java|jdk|jre" -and $_ -ne "" }
    
    # Agregar Java 21 al inicio del PATH
    $newPath = "C:\Program Files\Java\jdk-21\bin;" + ($pathParts -join ";")
    [Environment]::SetEnvironmentVariable("PATH", $newPath, [EnvironmentVariableTarget]::Machine)
    
    Write-Host "? Variables de entorno del SISTEMA configuradas:" -ForegroundColor Green
    Write-Host "   JAVA_HOME: C:\Program Files\Java\jdk-21" -ForegroundColor Yellow
    Write-Host "   PATH actualizado con Java 21" -ForegroundColor Yellow
    Write-Host "" -ForegroundColor Yellow
    Write-Host "?? REINICIE SU COMPUTADORA para que los cambios surtan efecto completo" -ForegroundColor Cyan
    
} else {
    Write-Host "??  No se está ejecutando como Administrador" -ForegroundColor Yellow
    Write-Host "Configurando solo para el usuario actual..." -ForegroundColor Yellow
    
    # Configurar variables de entorno del usuario
    [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-21", [EnvironmentVariableTarget]::User)
    
    $userPath = [Environment]::GetEnvironmentVariable("PATH", [EnvironmentVariableTarget]::User)
    if (-not $userPath) { $userPath = "" }
    
    # Remover referencias a Java antiguas del PATH del usuario
    $pathParts = $userPath -split ";" | Where-Object { $_ -notmatch "java|jdk|jre" -and $_ -ne "" }
    
    # Agregar Java 21 al PATH del usuario
    $newUserPath = "C:\Program Files\Java\jdk-21\bin;" + ($pathParts -join ";")
    [Environment]::SetEnvironmentVariable("PATH", $newUserPath, [EnvironmentVariableTarget]::User)
    
    Write-Host "? Variables de entorno del USUARIO configuradas:" -ForegroundColor Green
    Write-Host "   JAVA_HOME: C:\Program Files\Java\jdk-21" -ForegroundColor Yellow
    Write-Host "   PATH actualizado con Java 21" -ForegroundColor Yellow
    Write-Host "" -ForegroundColor Yellow
    Write-Host "?? Para configuración del sistema, ejecute como Administrador" -ForegroundColor Cyan
}

# Actualizar variables de la sesión actual
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "C:\Program Files\Java\jdk-21\bin;" + $env:PATH

Write-Host "=== VERIFICANDO CONFIGURACIÓN ===" -ForegroundColor Cyan
Write-Host "Java versión actual:" -ForegroundColor Yellow
java -version

Write-Host "`n=== PROBANDO GESTCOOP ===" -ForegroundColor Cyan
if (Test-Path "target\dist\GestCoop.exe") {
    Write-Host "? GestCoop.exe encontrado" -ForegroundColor Green
    Write-Host "?? Iniciando GestCoop..." -ForegroundColor Green
    
    Start-Process -FilePath "target\dist\GestCoop.exe"
    Start-Sleep -Seconds 3
    
    $javaProcs = Get-Process java -ErrorAction SilentlyContinue
    if ($javaProcs) {
        Write-Host "? GestCoop está ejecutándose (procesos Java activos)" -ForegroundColor Green
        $javaProcs | Select-Object Id, StartTime | Format-Table
    }
} else {
    Write-Host "? GestCoop.exe no encontrado. Ejecute build_and_package.ps1 primero" -ForegroundColor Red
}

Write-Host "`n=== CONFIGURACIÓN COMPLETADA ===" -ForegroundColor Cyan
Write-Host "?? GestCoop ahora debería funcionar correctamente con Java 21" -ForegroundColor Green