# Script avanzado para limpiar completamente los caracteres españoles
param(
    [string]$Directory = "src"
)

# Función para limpiar caracteres problemáticos
function Clean-SpanishText {
    param([string]$text)
    
    # Primero, reemplazamos las secuencias específicas más largas
    $text = $text -replace 'DirecciÃ¯ï¿½Â½n', 'Dirección'
    $text = $text -replace 'DirecciÃ³n', 'Dirección'
    $text = $text -replace 'Direcciï¿½n', 'Dirección'
    $text = $text -replace 'Direcciï¿½Â½n', 'Dirección'
    $text = $text -replace 'Direcciï¿½n', 'Dirección'
    
    $text = $text -replace 'CÃ¯ï¿½Â½digo', 'Código'
    $text = $text -replace 'CÃ³digo', 'Código'
    $text = $text -replace 'Cï¿½digo', 'Código'
    $text = $text -replace 'Cï¿½Â½digo', 'Código'
    
    $text = $text -replace 'PoblaciÃ¯ï¿½Â½n', 'Población'
    $text = $text -replace 'PoblaciÃ³n', 'Población'
    $text = $text -replace 'Poblaciï¿½n', 'Población'
    
    $text = $text -replace 'TelÃ¯ï¿½Â½fono', 'Teléfono'
    $text = $text -replace 'TelÃ©fono', 'Teléfono'
    $text = $text -replace 'Telï¿½fono', 'Teléfono'
    
    # Luego caracteres individuales problemáticos
    $text = $text -replace 'ï¿½', 'ó'
    $text = $text -replace 'Â½', ''
    $text = $text -replace 'Â¿', ''
    $text = $text -replace 'Âº', 'º'
    $text = $text -replace 'Â«', '«'
    $text = $text -replace 'Â»', '»'
    $text = $text -replace 'Ã¡', 'á'
    $text = $text -replace 'Ã©', 'é'
    $text = $text -replace 'Ã­', 'í'
    $text = $text -replace 'Ã³', 'ó'
    $text = $text -replace 'Ãº', 'ú'
    $text = $text -replace 'Ã±', 'ñ'
    $text = $text -replace 'Ã¯', 'í'
    $text = $text -replace 'Ã ', 'à'
    $text = $text -replace 'Ã¨', 'è'
    $text = $text -replace 'Ã¹', 'ù'
    
    # Mayúsculas con codificación problemática
    $text = $text -replace 'C[ÓóÂ]digo', 'Código'
    $text = $text -replace 'Direcci[ÓóÂ]n', 'Dirección'  
    $text = $text -replace 'Poblaci[ÓóÂ]n', 'Población'
    $text = $text -replace 'Tel[ÉéÂ]fono', 'Teléfono'
    
    return $text
}

# Función para procesar archivos
function Fix-SpanishCharacters {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            # Leer como UTF-8
            $content = [System.IO.File]::ReadAllText($filePath, [System.Text.Encoding]::UTF8)
            $originalContent = $content
            
            # Aplicar limpieza
            $content = Clean-SpanishText $content
            
            # Si hubo cambios, escribir el archivo
            if ($content -ne $originalContent) {
                Write-Host "Limpiando: $(Split-Path $filePath -Leaf)"
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
    return $false
}

# Obtener todos los archivos Java
$javaFiles = Get-ChildItem -Path $Directory -Filter "*.java" -Recurse

$totalFiles = $javaFiles.Count
$changedFiles = 0

Write-Host "Limpiando caracteres españoles en $totalFiles archivos Java..."

foreach ($file in $javaFiles) {
    if (Fix-SpanishCharacters -filePath $file.FullName) {
        $changedFiles++
    }
}

Write-Host ""
Write-Host "=== RESUMEN FINAL ==="
Write-Host "Archivos procesados: $totalFiles"
Write-Host "Archivos limpiados: $changedFiles"

if ($changedFiles -gt 0) {
    Write-Host "¡Caracteres españoles corregidos completamente!"
} else {
    Write-Host "No se encontraron caracteres que necesiten corrección."
}