# Script para iniciar GestCoop con GUI FORZADA y visible
param(
    [switch]$KillExisting = $false,
    [switch]$Verbose = $false
)

Write-Host "?? INICIADOR GESTCOOP CON GUI FORZADA" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan

# Terminar procesos existentes si se solicita
if ($KillExisting) {
    $existingJava = Get-Process -Name "java" -ErrorAction SilentlyContinue
    if ($existingJava) {
        Write-Host "?? Terminando procesos Java existentes..." -ForegroundColor Yellow
        $existingJava | Stop-Process -Force
        Start-Sleep 2
    }
}

# Verificar Java 21
$javaPath = "C:\Program Files\Java\jdk-21\bin\java.exe"
if (-not (Test-Path $javaPath)) {
    Write-Host "? Java 21 no encontrado en: $javaPath" -ForegroundColor Red
    exit 1
}

# Verificar JAR
$jarPath = "target\dist\GestCoop.jar"
if (-not (Test-Path $jarPath)) {
    Write-Host "? JAR no encontrado: $jarPath" -ForegroundColor Red
    Write-Host "?? Ejecute: .\build_and_package.ps1" -ForegroundColor Yellow
    exit 1
}

Write-Host "? Java 21: $javaPath" -ForegroundColor Green
Write-Host "? JAR: $jarPath" -ForegroundColor Green

# JVM Arguments optimizados para GUI FORZADA
$jvmArgs = @(
    # Debug
    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005",
    
    # Memoria conservadora para evitar problemas
    "-Xms512m",
    "-Xmx1536m",
    "-XX:+UseG1GC",
    "-XX:MaxGCPauseMillis=100",
    
    # Encoding y zona horaria
    "-Dfile.encoding=ISO-8859-1",
    "-Duser.timezone=Europe/Madrid",
    
    # GUI FORZADA - configuración crítica
    "-Djava.awt.headless=false",                    # NUNCA headless
    "-Dsun.java2d.d3d=false",                       # Deshabilitar Direct3D problemático
    "-Dsun.java2d.opengl=false",                    # Deshabilitar OpenGL problemático
    "-Dsun.java2d.noddraw=true",                    # Deshabilitar DirectDraw
    "-Dsun.java2d.pmoffscreen=false",               # Deshabilitar PixelMap offscreen
    "-Dprism.order=sw",                             # Usar software rendering
    "-Dsun.java2d.renderer=sun.java2d.pipe.RenderingEngine", # Renderer específico
    
    # Swing específico
    "-Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel", # Look&Feel confiable
    "-Djava.awt.Window.locationByPlatform=false",   # Posición manual, no automática
    "-Dawt.nativeDoubleBuffering=true",             # Double buffering nativo
    "-Dsun.awt.keepWorkingSetOnMinimize=true",      # Mantener memoria en minimize
    
    # Fuentes y rendering
    "-Dswing.aatext=true",                          # Anti-aliasing
    "-Dawt.useSystemAAFontSettings=lcd",            # Fuentes sistema LCD
    "-Dsun.java2d.renderer.verbose=false",          # Sin verbose renderer
    
    # Visibility FORZADA
    "-Djava.awt.Window.alwaysOnTop=false",          # No siempre encima
    "-Djava.awt.Window.autoRequestFocus=true",      # Auto-focus
    "-Dsun.awt.disablegrab=false",                  # Permitir grab de ventana
    
    # Hibernate silenciado
    "-Dhibernate.show_sql=false",
    "-Dorg.hibernate.SQL=ERROR",
    "-Dorg.hibernate.type=ERROR",
    "-Dorg.hibernate.tool.hbm2ddl=ERROR",
    
    # Logging reducido
    "-Djava.util.logging.config.level=WARNING",
    "-Dlog4j.configuration=log4j.properties"
)

# Argumentos adicionales para debugging si es necesario
if ($Verbose) {
    $jvmArgs += @(
        "-verbose:class",
        "-Djava.awt.debug=true",
        "-Dswing.debug=true"
    )
}

Write-Host "?? Configurando entorno GUI..." -ForegroundColor Yellow

# Cambiar al directorio target\dist
Push-Location "target\dist"

try {
    Write-Host "? Iniciando GestCoop..." -ForegroundColor Green
    
    # Crear proceso con configuración específica para GUI
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = $javaPath
    $startInfo.Arguments = ($jvmArgs + @("-jar", "GestCoop.jar")) -join ' '
    $startInfo.WorkingDirectory = (Get-Location).Path
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $false
    $startInfo.WindowStyle = [System.Diagnostics.ProcessWindowStyle]::Normal
    
    # Configurar variables de entorno para GUI
    $startInfo.EnvironmentVariables["DISPLAY"] = ":0.0"  # Para X11 si es necesario
    $startInfo.EnvironmentVariables["JAVA_TOOL_OPTIONS"] = "-Djava.awt.headless=false"
    
    $process = [System.Diagnostics.Process]::Start($startInfo)
    
    if ($process) {
        Write-Host "? Proceso iniciado con PID: $($process.Id)" -ForegroundColor Green
        Write-Host "?? Debug disponible en puerto: 5005" -ForegroundColor Cyan
        
        # Monitoreo activo del proceso
        Write-Host "? Monitoreando inicio de GUI..." -ForegroundColor Yellow
        
        $maxWait = 45  # Esperar hasta 45 segundos
        $checkInterval = 3
        $checks = [math]::Floor($maxWait / $checkInterval)
        
        for ($i = 1; $i -le $checks; $i++) {
            Start-Sleep $checkInterval
            
            try {
                $currentProcess = Get-Process -Id $process.Id -ErrorAction SilentlyContinue
                if (-not $currentProcess) {
                    Write-Host "? Proceso terminó inesperadamente" -ForegroundColor Red
                    break
                }
                
                $memoryMB = [math]::Round($currentProcess.WorkingSet64 / 1MB, 1)
                Write-Host "   Verificación $i/$checks - Memoria: $memoryMB MB" -ForegroundColor Gray
                
                # Actualizar información del proceso
                $currentProcess.Refresh()
                
                # Verificar ventana principal
                if ($currentProcess.MainWindowTitle -and $currentProcess.MainWindowTitle.Trim() -ne "") {
                    Write-Host "?? ¡VENTANA GUI DETECTADA!" -ForegroundColor Green
                    Write-Host "   Título: '$($currentProcess.MainWindowTitle)'" -ForegroundColor Yellow
                    Write-Host "? ¡GestCoop iniciado correctamente con GUI!" -ForegroundColor Green
                    break
                } elseif ($i -eq $checks) {
                    Write-Host "??  GUI no aparece - verificando ventanas ocultas..." -ForegroundColor Yellow
                    
                    # Intentar forzar visibilidad
                    & ".\Force-Visible-GestCoop.ps1"
                }
                
            } catch {
                Write-Host "   Error en verificación: $($_.Exception.Message)" -ForegroundColor Red
            }
        }
        
        # Estado final
        Write-Host "`n?? ESTADO FINAL:" -ForegroundColor Cyan
        
        $finalProcess = Get-Process -Id $process.Id -ErrorAction SilentlyContinue
        if ($finalProcess) {
            $finalMemory = [math]::Round($finalProcess.WorkingSet64 / 1MB, 1)
            Write-Host "   ? Proceso activo (PID: $($process.Id), Memoria: $finalMemory MB)" -ForegroundColor Green
            
            if ($finalProcess.MainWindowTitle) {
                Write-Host "   ?? Ventana: '$($finalProcess.MainWindowTitle)'" -ForegroundColor Green
            } else {
                Write-Host "   ??  Sin ventana visible" -ForegroundColor Yellow
                Write-Host "   ?? Pruebe Alt+Tab o ejecute: .\Force-Visible-GestCoop.ps1" -ForegroundColor Cyan
            }
        } else {
            Write-Host "   ? Proceso terminó" -ForegroundColor Red
        }
        
    } else {
        Write-Host "? No se pudo iniciar el proceso" -ForegroundColor Red
    }
    
} catch {
    Write-Host "? Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Stack: $($_.ScriptStackTrace)" -ForegroundColor Red
} finally {
    Pop-Location
}

Write-Host "`n?? COMANDOS ÚTILES:" -ForegroundColor Magenta
Write-Host "   .\Check-GestCoop-Status.ps1    # Verificar estado" -ForegroundColor Gray
Write-Host "   .\Force-Visible-GestCoop.ps1   # Forzar visibilidad" -ForegroundColor Gray
Write-Host "   Get-Process java | Stop-Process -Force  # Terminar todos" -ForegroundColor Gray