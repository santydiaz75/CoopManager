# Script para actualizar JAVA_HOME sin reiniciar
# Actualiza variables de entorno del sistema y notifica a todas las aplicaciones

param([string]$JavaPath = "C:\Program Files\Java\jdk-21")

Write-Host "=== ACTUALIZANDO JAVA_HOME SIN REINICIAR ===" -ForegroundColor Cyan

# Verificar que el directorio existe
if (-not (Test-Path $JavaPath)) {
    Write-Host "? Error: El directorio Java no existe: $JavaPath" -ForegroundColor Red
    exit 1
}

Write-Host "? Java encontrado: $JavaPath" -ForegroundColor Green

try {
    # 1. Actualizar variable de entorno del sistema
    [Environment]::SetEnvironmentVariable("JAVA_HOME", $JavaPath, [EnvironmentVariableTarget]::Machine)
    Write-Host "? JAVA_HOME actualizado en el registro del sistema" -ForegroundColor Green
    
    # 2. Actualizar PATH del sistema (remover Java antiguo y agregar nuevo)
    $systemPath = [Environment]::GetEnvironmentVariable("PATH", [EnvironmentVariableTarget]::Machine)
    $pathParts = $systemPath -split ";" | Where-Object { $_ -notmatch "java|jdk|jre" -and $_ -ne "" }
    $newPath = "$JavaPath\bin;" + ($pathParts -join ";")
    [Environment]::SetEnvironmentVariable("PATH", $newPath, [EnvironmentVariableTarget]::Machine)
    Write-Host "? PATH del sistema actualizado" -ForegroundColor Green
    
    # 3. Actualizar variables de la sesión actual
    $env:JAVA_HOME = $JavaPath
    $env:PATH = "$JavaPath\bin;" + $env:PATH
    Write-Host "? Variables de sesión actualizadas" -ForegroundColor Green
    
    # 4. Notificar a todas las aplicaciones usando WM_SETTINGCHANGE
    Add-Type -TypeDefinition @"
        using System;
        using System.Runtime.InteropServices;
        public class Win32 {
            [DllImport("user32.dll", SetLastError = true, CharSet = CharSet.Auto)]
            public static extern IntPtr SendMessageTimeout(
                IntPtr hWnd, uint Msg, UIntPtr wParam, string lParam,
                uint fuFlags, uint uTimeout, out UIntPtr lpdwResult);
        }
"@
    
    $HWND_BROADCAST = [IntPtr]0xffff
    $WM_SETTINGCHANGE = 0x1a
    $result = [UIntPtr]::Zero
    
    [Win32]::SendMessageTimeout($HWND_BROADCAST, $WM_SETTINGCHANGE, [UIntPtr]::Zero, "Environment", 2, 5000, [ref]$result)
    Write-Host "? Notificación enviada a todas las aplicaciones" -ForegroundColor Green
    
    # 5. Verificar resultado
    Write-Host "`n=== VERIFICACIÓN ===" -ForegroundColor Yellow
    Write-Host "JAVA_HOME del sistema: $([Environment]::GetEnvironmentVariable('JAVA_HOME', [EnvironmentVariableTarget]::Machine))" -ForegroundColor Cyan
    Write-Host "JAVA_HOME de sesión: $env:JAVA_HOME" -ForegroundColor Cyan
    
    # Probar Java
    Write-Host "`nProbando Java:" -ForegroundColor Yellow
    & "$JavaPath\bin\java.exe" -version
    
    Write-Host "`n?? ¡JAVA_HOME actualizado exitosamente sin reiniciar!" -ForegroundColor Green
    Write-Host "?? Las nuevas aplicaciones usarán automáticamente la nueva configuración" -ForegroundColor Cyan
    Write-Host "?? Aplicaciones ya abiertas necesitan reiniciarse para usar el nuevo Java" -ForegroundColor Cyan
    
} catch {
    Write-Host "? Error actualizando variables de entorno: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "?? Intente ejecutar como Administrador" -ForegroundColor Yellow
}