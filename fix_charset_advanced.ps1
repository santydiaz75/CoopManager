# Script avanzado para corregir codificación de caracteres españoles
# Incluye múltiples patrones de codificación incorrecta

Write-Host "=== Iniciando corrección avanzada de codificación española ===" -ForegroundColor Green

# Función para procesar un archivo
function Fix-FileEncodingAdvanced {
    param (
        [string]$filePath
    )
    
    try {
        Write-Host "Procesando: $($filePath | Split-Path -Leaf)" -ForegroundColor Yellow
        
        # Leer contenido del archivo como bytes
        $bytes = [System.IO.File]::ReadAllBytes($filePath)
        $content = [System.Text.Encoding]::UTF8.GetString($bytes)
        
        # Crear backup
        $backupPath = $filePath + ".charset2.bak"
        Copy-Item -Path $filePath -Destination $backupPath
        
        # Almacenar contenido original
        $originalContent = $content
        
        # Correcciones UTF-8 malformado (Ã seguido de caracteres)
        $content = $content -replace 'Ã±', 'ñ'
        $content = $content -replace 'Ã¡', 'á'
        $content = $content -replace 'Ã©', 'é'
        $content = $content -replace 'Ã­', 'í'
        $content = $content -replace 'Ã³', 'ó'
        $content = $content -replace 'Ãº', 'ú'
        $content = $content -replace 'Ã ', 'à'
        $content = $content -replace 'Ã¼', 'ü'
        
        # Correcciones de doble codificación (ï¿½)
        $content = $content -replace 'ï¿½', 'ñ'
        $content = $content -replace 'Aï¿½o', 'Año'
        $content = $content -replace 'aï¿½o', 'año'
        $content = $content -replace 'Pï¿½gina', 'Página'
        $content = $content -replace 'pï¿½gina', 'página'
        $content = $content -replace 'dï¿½a', 'día'
        $content = $content -replace 'Dï¿½a', 'Día'
        $content = $content -replace 'dï¿½as', 'días'
        $content = $content -replace 'niï¿½os', 'niños'
        $content = $content -replace 'sï¿½bado', 'sábado'
        $content = $content -replace 'Sï¿½bado', 'Sábado'
        $content = $content -replace 'mï¿½dico', 'médico'
        $content = $content -replace 'nï¿½mina', 'nómina'
        $content = $content -replace 'Miï¿½rcoles', 'Miércoles'
        $content = $content -replace 'cumpleaï¿½os', 'cumpleaños'
        $content = $content -replace 'bonificaciï¿½n', 'bonificación'
        $content = $content -replace 'acudiï¿½', 'acudió'
        $content = $content -replace 'pagï¿½', 'pagó'
        $content = $content -replace 'Hernï¿½ndez', 'Hernández'
        $content = $content -replace 'Mï¿½ndez', 'Méndez'
        $content = $content -replace 'Dï¿½vora', 'Dávora'
        $content = $content -replace 'Carbï¿½n', 'Carbón'
        
        # Correcciones de palabras completas específicas ya vistas
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
            # Guardar archivo corregido con codificación UTF-8
            $utf8NoBom = New-Object System.Text.UTF8Encoding $false
            [System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)
            Write-Host "  ? Archivo corregido con nuevas reglas" -ForegroundColor Green
            return 1
        }
        else {
            # Eliminar backup si no hubo cambios
            Remove-Item -Path $backupPath
            Write-Host "  ? No necesita correcciones adicionales" -ForegroundColor Gray
            return 0
        }
    }
    catch {
        Write-Host "  ? Error procesando archivo: $($_.Exception.Message)" -ForegroundColor Red
        return -1
    }
}

# Patrones de archivos a procesar
$filePatterns = @(
    "src\reportsPackage\*.jrxml",
    "target\classes\reportsPackage\*.jrxml",
    "Backup\BackupDB\*.sql",
    "src\winUIPackage\*.java",
    "bin\winUIPackage\*.class"
)

$totalFiles = 0
$correctedFiles = 0

foreach ($pattern in $filePatterns) {
    Write-Host "`n--- Procesando patrón: $pattern ---" -ForegroundColor Blue
    
    $files = Get-ChildItem -Path $pattern -ErrorAction SilentlyContinue
    
    if ($files) {
        $totalFiles += $files.Count
        
        foreach ($file in $files) {
            $result = Fix-FileEncodingAdvanced -filePath $file.FullName
            if ($result -eq 1) {
                $correctedFiles++
            }
        }
    }
    else {
        Write-Host "  No se encontraron archivos para el patrón: $pattern" -ForegroundColor Gray
    }
}

Write-Host "`n=== RESUMEN DE CORRECCIONES AVANZADAS ===" -ForegroundColor Green
Write-Host "Archivos procesados: $totalFiles" -ForegroundColor White
Write-Host "Archivos corregidos: $correctedFiles" -ForegroundColor White

if ($correctedFiles -gt 0) {
    Write-Host "`n? Corrección avanzada aplicada a $correctedFiles archivos" -ForegroundColor Green
    Write-Host "? Se crearon backups con extensión .charset2.bak" -ForegroundColor Green
} else {
    Write-Host "`n? No se encontraron caracteres adicionales que corregir" -ForegroundColor Green
}

Write-Host "`n=== Corrección avanzada de codificación completada ===" -ForegroundColor Green