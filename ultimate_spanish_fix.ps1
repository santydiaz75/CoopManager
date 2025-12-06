# Script definitivo para limpiar TODOS los caracteres problemáticos
param(
    [string]$Directory = "src"
)

# Función para hacer reemplazo definitivo
function Fix-AllBadCharacters {
    param([string]$text)
    
    # Secuencias largas específicas primero
    $text = $text -replace 'ï¿½ï¿½ï¿½', 'í'
    $text = $text -replace 'ï¿½ï¿½', 'í' 
    $text = $text -replace 'ï¿½', 'ó'
    
    # Palabras específicas completas
    $replacements = @{
        'Direcciï¿½n' = 'Dirección'
        'Cï¿½digo' = 'Código'
        'Pobliciï¿½n' = 'Población'
        'Telï¿½fono' = 'Teléfono'
        'Nï¿½mero' = 'Número'
        'vï¿½lido' = 'válido'
        'Contabilizaciï¿½n' = 'Contabilización'
        'liquidaciï¿½n' = 'liquidación'
        'Polï¿½gono' = 'Polígono'
        'especificaciï¿½n' = 'especificación'
        'validaciï¿½n' = 'validación'
        'informaciï¿½n' = 'información'
        'configuraciï¿½n' = 'configuración'
        'autorizaciï¿½n' = 'autorización'
        'modificaciï¿½n' = 'modificación'
        'creaciï¿½n' = 'creación'
        'selecciï¿½n' = 'selección'
        'eliminaciï¿½n' = 'eliminación'
        'operaciï¿½n' = 'operación'
        'transacciï¿½n' = 'transacción'
        'conexiï¿½n' = 'conexión'
        'vï¿½ï¿½ï¿½lido' = 'válido'
        'cï¿½ï¿½ï¿½digo' = 'código'
        'nï¿½ï¿½ï¿½mero' = 'número'
    }
    
    foreach ($bad in $replacements.Keys) {
        $good = $replacements[$bad]
        $text = $text -replace [regex]::Escape($bad), $good
    }
    
    # Limpiezas finales de caracteres sueltos
    $text = $text -replace 'Â½', ''
    $text = $text -replace 'Â¿', ''
    $text = $text -replace 'Âº', 'º'
    $text = $text -replace 'Â«', '«'
    $text = $text -replace 'Â»', '»'
    
    return $text
}

# Función para procesar archivos
function Clean-File {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            # Leer como UTF-8
            $content = [System.IO.File]::ReadAllText($filePath, [System.Text.Encoding]::UTF8)
            $originalContent = $content
            
            # Aplicar limpieza
            $content = Fix-AllBadCharacters $content
            
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

Write-Host "=== LIMPIEZA FINAL DE CARACTERES ESPAÑOLES ==="
Write-Host "Procesando $totalFiles archivos Java..."

foreach ($file in $javaFiles) {
    if (Clean-File -filePath $file.FullName) {
        $changedFiles++
    }
}

Write-Host ""
Write-Host "=== RESULTADO FINAL ==="
Write-Host "Archivos procesados: $totalFiles"
Write-Host "Archivos corregidos: $changedFiles"

if ($changedFiles -gt 0) {
    Write-Host "¡ÉXITO! Los caracteres españoles han sido corregidos completamente."
} else {
    Write-Host "Todos los archivos ya están limpios."
}