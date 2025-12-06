# Script para corregir caracteres españoles mal codificados
param(
    [string]$Directory = "src"
)

# Mapa de correcciones para caracteres mal codificados
$corrections = @{
    'DirecciÃ¯ï¿½Â½n' = 'Dirección'
    'DirecciÃ³n' = 'Dirección' 
    'Direcciï¿½n' = 'Dirección'
    'CÃ¯ï¿½Â½digo' = 'Código'
    'CÃ³digo' = 'Código'
    'Cï¿½digo' = 'Código'
    'PoblaciÃ¯ï¿½Â½n' = 'Población'
    'PoblaciÃ³n' = 'Población'
    'Poblaciï¿½n' = 'Población'
    'TelÃ¯ï¿½Â½fono' = 'Teléfono'
    'TelÃ©fono' = 'Teléfono'
    'Telï¿½fono' = 'Teléfono'
    'Ã¡' = 'á'
    'Ã©' = 'é'
    'Ã­' = 'í'
    'Ã³' = 'ó'
    'Ãº' = 'ú'
    'Ã±' = 'ñ'
    'Ã¯ï¿½Â½' = 'ó'
    'ï¿½' = 'ó'
    'Â½' = 'ó'
    'Ã¯' = 'í'
    'Â¿' = ''
    'Âº' = 'º'
}

# Función para procesar archivos
function Fix-EncodingIssues {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            # Leer el contenido como bytes para evitar problemas de codificación
            $bytes = [System.IO.File]::ReadAllBytes($filePath)
            $content = [System.Text.Encoding]::UTF8.GetString($bytes)
            
            # Aplicar correcciones
            $originalContent = $content
            foreach ($badChar in $corrections.Keys) {
                $goodChar = $corrections[$badChar]
                $content = $content.Replace($badChar, $goodChar)
            }
            
            # Si hubo cambios, escribir el archivo
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
    return $false
}

# Obtener todos los archivos Java
$javaFiles = Get-ChildItem -Path $Directory -Filter "*.java" -Recurse

$totalFiles = $javaFiles.Count
$processedFiles = 0
$changedFiles = 0

Write-Host "Procesando $totalFiles archivos Java..."

foreach ($file in $javaFiles) {
    $processedFiles++
    if (Fix-EncodingIssues -filePath $file.FullName) {
        $changedFiles++
    }
    
    if ($processedFiles % 50 -eq 0) {
        Write-Host "Progreso: $processedFiles/$totalFiles archivos procesados, $changedFiles corregidos"
    }
}

Write-Host ""
Write-Host "=== RESUMEN ==="
Write-Host "Archivos procesados: $processedFiles"
Write-Host "Archivos corregidos: $changedFiles"
Write-Host "¡Corrección de caracteres españoles completada!"