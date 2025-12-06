# Script final para corregir caracteres problemáticos restantes

Write-Host "=== Corrección final de caracteres problemáticos ===" -ForegroundColor Green

# Función para corrección manual específica
function Fix-SpecificIssues {
    param (
        [string]$filePath
    )
    
    try {
        Write-Host "Procesando: $($filePath | Split-Path -Leaf)" -ForegroundColor Yellow
        
        # Leer contenido del archivo
        $content = Get-Content -Path $filePath -Raw -Encoding UTF8
        
        # Crear backup
        $backupPath = $filePath + ".final.bak"
        Copy-Item -Path $filePath -Destination $backupPath
        
        # Almacenar contenido original
        $originalContent = $content
        
        # Correcciones específicas que quedaron pendientes
        $content = $content -replace 'Josï¿½', 'José'
        $content = $content -replace 'Jesï¿½s', 'Jesús'
        $content = $content -replace 'Dï¿½az', 'Díaz'
        $content = $content -replace 'Falcï¿½n', 'Falcón'
        $content = $content -replace 'Pï¿½gina', 'Página'
        $content = $content -replace 'RELACIï¿½N', 'RELACIÓN'
        $content = $content -replace 'ï¿½', 'ñ'  # Reemplazo genérico de último recurso
        
        # Verificar si hubo cambios
        if ($content -ne $originalContent) {
            # Guardar archivo corregido
            $utf8NoBom = New-Object System.Text.UTF8Encoding $false
            [System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)
            Write-Host "  ? Correcciones finales aplicadas" -ForegroundColor Green
            return 1
        }
        else {
            # Eliminar backup si no hubo cambios
            Remove-Item -Path $backupPath
            Write-Host "  ? Ya está correcto" -ForegroundColor Gray
            return 0
        }
    }
    catch {
        Write-Host "  ? Error: $($_.Exception.Message)" -ForegroundColor Red
        return -1
    }
}

# Archivos específicos con problemas restantes
$problemFiles = @(
    "target\classes\reportsPackage\AyudasOCM.jrxml",
    "target\classes\reportsPackage\ControlCalidad.jrxml",
    "target\classes\reportsPackage\CartaCosecheros.jrxml"
)

$correctedFiles = 0

foreach ($pattern in $problemFiles) {
    if (Test-Path $pattern) {
        $result = Fix-SpecificIssues -filePath (Resolve-Path $pattern).Path
        if ($result -eq 1) {
            $correctedFiles++
        }
    }
    else {
        Write-Host "Archivo no encontrado: $pattern" -ForegroundColor Yellow
    }
}

Write-Host "`n=== LIMPIEZA DE ARCHIVOS BACKUP ===" -ForegroundColor Blue

# Limpiar archivos backup redundantes
$backupFiles = Get-ChildItem -Path . -Recurse -Filter "*.charset.bak" -ErrorAction SilentlyContinue
Write-Host "Eliminando $($backupFiles.Count) archivos backup redundantes..." -ForegroundColor Yellow

foreach ($backup in $backupFiles) {
    Remove-Item -Path $backup.FullName -Force
    Write-Host "  Eliminado: $($backup.Name)" -ForegroundColor Gray
}

Write-Host "`n=== RESUMEN FINAL ===" -ForegroundColor Green
Write-Host "Archivos con correcciones finales: $correctedFiles" -ForegroundColor White
Write-Host "Archivos backup limpiados: $($backupFiles.Count)" -ForegroundColor White

Write-Host "`n? Corrección final de caracteres completada" -ForegroundColor Green