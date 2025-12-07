# Script para corregir todas las apariciones de ï¿½ en archivos Java
$rootPath = "src"

# Mapeo de correcciones específicas
$corrections = @{
    'ï¿½' = 'ó'
    'aplicaciï¿½n' = 'aplicación'
    'configuraciï¿½n' = 'configuración'
    'Contabilizaciï¿½n' = 'Contabilización'
    'liquidaciï¿½n' = 'liquidación'
    'Preliquidaciï¿½n' = 'Preliquidación'
    'producci7n' = 'producción'
    'informaci7n' = 'información'
    'validaci7n' = 'validación'
    'creaciï¿½n' = 'creación'
    'eliminaciï¿½n' = 'eliminación'
    'modificaciï¿½n' = 'modificación'
    'Nï¿½mero' = 'Número'
    'nï¿½mero' = 'número'
    'parï¿½metro' = 'parámetro'
    'mï¿½s' = 'más'
    'diseï¿½ado' = 'diseñado'
    'vï¿½lido' = 'válido'
    'Cï¿½digo' = 'Código'
    'cï¿½digo' = 'código'
    'Polï¿½gono' = 'Polígono'
}

function Fix-File {
    param([string]$filePath)
    
    try {
        $content = [System.IO.File]::ReadAllText($filePath, [System.Text.Encoding]::GetEncoding("ISO-8859-1"))
        $originalContent = $content
        
        # Aplicar todas las correcciones
        foreach ($bad in $corrections.Keys) {
            $good = $corrections[$bad]
            $content = $content -replace [regex]::Escape($bad), $good
        }
        
        if ($content -ne $originalContent) {
            Write-Host "Corrigiendo: $(Split-Path $filePath -Leaf)"
            $utf8WithoutBom = [System.Text.UTF8Encoding]::new($false)
            [System.IO.File]::WriteAllText($filePath, $content, $utf8WithoutBom)
            return $true
        }
        return $false
    }
    catch {
        Write-Warning "Error procesando $filePath : $($_.Exception.Message)"
        return $false
    }
}

# Buscar todos los archivos Java
$javaFiles = Get-ChildItem -Path $rootPath -Filter "*.java" -Recurse
$totalFiles = $javaFiles.Count
$correctedFiles = 0

Write-Host "=== CORRECCIÓN DE CARACTERES ï¿½ ==="
Write-Host "Procesando $totalFiles archivos Java..."

foreach ($file in $javaFiles) {
    if (Fix-File -filePath $file.FullName) {
        $correctedFiles++
    }
}

Write-Host ""
Write-Host "=== RESULTADO ==="
Write-Host "Archivos procesados: $totalFiles"
Write-Host "Archivos corregidos: $correctedFiles"
Write-Host "¡Corrección de caracteres ï¿½ completada!"