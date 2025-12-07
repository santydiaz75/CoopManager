# Script para corregir todos los caracteres españoles corruptos
$encoding = [System.Text.Encoding]::GetEncoding("ISO-8859-1")

# Mapeo completo de caracteres corruptos a correctos
$characterMap = @{
    'Duraciï¿½n' = 'Duración'
    'liquidaciï¿½n' = 'liquidación'
    'Aï¿½o' = 'Año'
    'Autï¿½nomo' = 'Autónomo'
    'lï¿½quido' = 'líquido'
    'Bonificaciï¿½n' = 'Bonificación'
    'Sï¿½bado' = 'Sábado'
    'Nï¿½mero' = 'Número'
    'dï¿½as' = 'días'
    'vï¿½lido' = 'válido'
    'vï¿½lida' = 'válida'
    'aï¿½o' = 'año'
    'Poblaciï¿½n' = 'Población'
    'Cï¿½digo' = 'Código'
    'cï¿½digo' = 'código'
    'Direcciï¿½n' = 'Dirección'
    'direcciï¿½n' = 'dirección'
    'Telï¿½fono' = 'Teléfono'
    'telï¿½fono' = 'teléfono'
}

# Función para procesar archivos
function Fix-SpanishCharacters {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            $content = Get-Content $filePath -Raw -Encoding $encoding
            $originalContent = $content
            
            foreach ($corrupt in $characterMap.Keys) {
                $correct = $characterMap[$corrupt]
                $content = $content -replace [regex]::Escape($corrupt), $correct
            }
            
            if ($content -ne $originalContent) {
                Set-Content -Path $filePath -Value $content -Encoding $encoding
                Write-Host "Corregido: $filePath"
            }
        } catch {
            Write-Warning "Error procesando $filePath : $_"
        }
    }
}

# Procesar todos los archivos Java en src
Write-Host "Corrigiendo caracteres españoles en archivos Java..."
Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object {
    Fix-SpanishCharacters $_.FullName
}

# Procesar archivos de reportes
Write-Host "Corrigiendo caracteres españoles en archivos de reportes..."
Get-ChildItem -Path "src" -Filter "*.jrxml" -Recurse | ForEach-Object {
    Fix-SpanishCharacters $_.FullName
}

Write-Host "Proceso completado."