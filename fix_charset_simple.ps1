# Script para corregir la codificación de caracteres españoles malformados

Write-Host "=== Iniciando corrección de codificación de caracteres españoles ===" -ForegroundColor Green

# Función para procesar un archivo
function Fix-FileEncoding {
    param (
        [string]$filePath
    )
    
    try {
        Write-Host "Procesando: $($filePath | Split-Path -Leaf)" -ForegroundColor Yellow
        
        # Leer contenido del archivo
        $content = Get-Content -Path $filePath -Raw -Encoding UTF8
        
        # Crear backup
        $backupPath = $filePath + ".charset.bak"
        Copy-Item -Path $filePath -Destination $backupPath
        
        # Aplicar correcciones básicas
        $originalContent = $content
        
        # Correcciones principales
        $content = $content -replace 'Ã±', 'ñ'
        $content = $content -replace 'Ã¡', 'á'
        $content = $content -replace 'Ã©', 'é'
        $content = $content -replace 'Ã­', 'í'
        $content = $content -replace 'Ã³', 'ó'
        $content = $content -replace 'Ãº', 'ú'
        $content = $content -replace 'Ã ', 'à'
        $content = $content -replace 'Ã¤', 'ä'
        $content = $content -replace 'Ã«', 'ë'
        $content = $content -replace 'Ã¯', 'ï'
        $content = $content -replace 'Ã¶', 'ö'
        $content = $content -replace 'Ã¼', 'ü'
        
        # Correcciones de palabras completas
        $content = $content -replace 'JosÃ©', 'José'
        $content = $content -replace 'transformaciÃ³n', 'transformación'
        $content = $content -replace 'plÃ¡tanos', 'plátanos'
        $content = $content -replace 'SelecciÃ³n', 'Selección'
        $content = $content -replace 'AÃ±o', 'Año'
        $content = $content -replace 'PÃ¡gina', 'Página'
        $content = $content -replace 'CÃ¡lculo', 'Cálculo'
        $content = $content -replace 'cÃ¡nones', 'cánones'
        $content = $content -replace 'pÃºblicas', 'públicas'
        $content = $content -replace 'explotaciÃ³n', 'explotación'
        $content = $content -replace 'DescripciÃ³n', 'Descripción'
        $content = $content -replace 'GestiÃ³n', 'Gestión'
        $content = $content -replace 'AgrÃ­colas', 'Agrícolas'
        
        # Verificar si hubo cambios
        if ($content -ne $originalContent) {
            # Guardar archivo corregido
            Set-Content -Path $filePath -Value $content -Encoding UTF8
            Write-Host "  ? Archivo corregido" -ForegroundColor Green
            return 1
        }
        else {
            # Eliminar backup si no hubo cambios
            Remove-Item -Path $backupPath
            Write-Host "  ? No necesita correcciones" -ForegroundColor Gray
            return 0
        }
    }
    catch {
        Write-Host "  ? Error procesando archivo: $($_.Exception.Message)" -ForegroundColor Red
        return -1
    }
}

# Obtener archivos a procesar
$filePatterns = @(
    "src\reportsPackage\*.jrxml",
    "target\classes\reportsPackage\*.jrxml",
    "Backup\BackupDB\*.sql"
)

$totalFiles = 0
$correctedFiles = 0

foreach ($pattern in $filePatterns) {
    Write-Host "`n--- Procesando patrón: $pattern ---" -ForegroundColor Blue
    
    $files = Get-ChildItem -Path $pattern -ErrorAction SilentlyContinue
    
    if ($files) {
        $totalFiles += $files.Count
        
        foreach ($file in $files) {
            $result = Fix-FileEncoding -filePath $file.FullName
            if ($result -eq 1) {
                $correctedFiles++
            }
        }
    }
    else {
        Write-Host "  No se encontraron archivos para el patrón: $pattern" -ForegroundColor Gray
    }
}

Write-Host "`n=== RESUMEN DE CORRECCIONES ===" -ForegroundColor Green
Write-Host "Archivos encontrados: $totalFiles" -ForegroundColor White
Write-Host "Archivos corregidos: $correctedFiles" -ForegroundColor White

if ($correctedFiles -gt 0) {
    Write-Host "`n? Se han corregido los caracteres españoles malformados en $correctedFiles archivos" -ForegroundColor Green
    Write-Host "? Se han creado backups con extensión .charset.bak" -ForegroundColor Green
} else {
    Write-Host "`n? No se encontraron caracteres que necesiten corrección" -ForegroundColor Green
}

Write-Host "`n=== Corrección de codificación completada ===" -ForegroundColor Green