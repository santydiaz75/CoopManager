# Script para corregir la codificación de caracteres españoles malformados
# Convierte caracteres UTF-8 mal interpretados a sus equivalentes correctos

Write-Host "=== Iniciando corrección de codificación de caracteres españoles ===" -ForegroundColor Green

# Mapeo de caracteres mal codificados a caracteres correctos
$charMappings = @{
    'Ã±' = 'ñ'
    'Ã¡' = 'á'
    'Ã©' = 'é'
    'Ã­' = 'í'
    'Ã³' = 'ó'
    'Ãº' = 'ú'
    'Ã'  = 'Ñ'
    'Ã ' = 'à'
    'ÃÃ' = 'ÁÁ'
    'Ã¤' = 'ä'
    'Ã«' = 'ë'
    'Ã¯' = 'ï'
    'Ã¶' = 'ö'
    'Ã¼' = 'ü'
    'Ã°' = 'ð'
    'JosÃ©' = 'José'
    'transformaciÃ³n' = 'transformación'
    'plÃ¡tanos' = 'plátanos'
    'SelecciÃ³n' = 'Selección'
    'AÃ±o' = 'Año'
    'PÃ¡gina' = 'Página'
    'CÃ¡lculo' = 'Cálculo'
    'aÃ±o' = 'año'
    'cÃ¡nones' = 'cánones'
    'pÃºblicas' = 'públicas'
    'explotaciÃ³n' = 'explotación'
    'DescripciÃ³n' = 'Descripción'
    'GestiÃ³n' = 'Gestión'
    'AgrÃ­colas' = 'Agrícolas'
}

# Función para procesar un archivo
function Fix-FileEncoding {
    param (
        [string]$filePath
    )
    
    try {
        Write-Host "Procesando: $($filePath | Split-Path -Leaf)" -ForegroundColor Yellow
        
        # Leer contenido del archivo
        $content = Get-Content -Path $filePath -Raw -Encoding UTF8
        
        # Verificar si hay caracteres malformados
        $hasChanges = $false
        foreach ($mapping in $charMappings.GetEnumerator()) {
            if ($content -like "*$($mapping.Key)*") {
                $hasChanges = $true
                break
            }
        }
        
        if (-not $hasChanges) {
            Write-Host "  ? No necesita correcciones" -ForegroundColor Gray
            return 0
        }
        
        # Crear backup
        $backupPath = $filePath + ".charset.bak"
        Copy-Item -Path $filePath -Destination $backupPath
        
        # Aplicar todas las correcciones
        $corrections = 0
        foreach ($mapping in $charMappings.GetEnumerator()) {
            $oldContent = $content
            $content = $content -replace [regex]::Escape($mapping.Key), $mapping.Value
            if ($content -ne $oldContent) {
                $corrections++
                Write-Host "    ? Corrigió: '$($mapping.Key)' ? '$($mapping.Value)'" -ForegroundColor Cyan
            }
        }
        
        # Guardar archivo corregido
        Set-Content -Path $filePath -Value $content -Encoding UTF8
        
        Write-Host "  ? Aplicadas $corrections correcciones" -ForegroundColor Green
        return $corrections
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
    "Backup\BackupDB\*.sql",
    "src\**\*.java",
    "bin\*.properties",
    "src\**\*.properties",
    "Data\*.sql"
)

$totalFiles = 0
$totalCorrections = 0
$processedFiles = 0

foreach ($pattern in $filePatterns) {
    Write-Host "`n--- Procesando patrón: $pattern ---" -ForegroundColor Blue
    
    $files = Get-ChildItem -Path $pattern -ErrorAction SilentlyContinue
    
    if ($files) {
        $totalFiles += $files.Count
        
        foreach ($file in $files) {
            $corrections = Fix-FileEncoding -filePath $file.FullName
            if ($corrections -ge 0) {
                $totalCorrections += $corrections
                $processedFiles++
            }
        }
    }
    else {
        Write-Host "  No se encontraron archivos para el patrón: $pattern" -ForegroundColor Gray
    }
}

Write-Host "`n=== RESUMEN DE CORRECCIONES ===" -ForegroundColor Green
Write-Host "Archivos encontrados: $totalFiles" -ForegroundColor White
Write-Host "Archivos procesados: $processedFiles" -ForegroundColor White
Write-Host "Total de correcciones aplicadas: $totalCorrections" -ForegroundColor White

if ($totalCorrections -gt 0) {
    Write-Host "`n? Se han corregido los caracteres españoles malformados" -ForegroundColor Green
    Write-Host "? Se han creado backups con extensión .charset.bak" -ForegroundColor Green
} else {
    Write-Host "`n? No se encontraron caracteres que necesiten corrección" -ForegroundColor Green
}

Write-Host "`n=== Corrección de codificación completada ===" -ForegroundColor Green