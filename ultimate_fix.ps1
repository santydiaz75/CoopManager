$encoding = [System.Text.Encoding]::GetEncoding("ISO-8859-1")

function Fix-AllRemainingCharacters {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            $content = Get-Content $filePath -Raw -Encoding $encoding
            $originalContent = $content
            
            # Patrones generales más amplios
            $content = $content -replace 'vï¿½lido', 'válido'
            $content = $content -replace 'vï¿½lida', 'válida'
            $content = $content -replace 'relaciï¿½n', 'relación'
            $content = $content -replace 'Relaciï¿½n', 'Relación'
            $content = $content -replace 'campaï¿½a', 'campaña'
            $content = $content -replace 'selecciï¿½n', 'selección'
            $content = $content -replace 'orgï¿½nica', 'orgánica'
            $content = $content -replace 'Orgï¿½nica', 'Orgánica'
            $content = $content -replace 'protecciï¿½n', 'protección'
            $content = $content -replace 'Protecciï¿½n', 'Protección'
            
            if ($content -ne $originalContent) {
                Set-Content -Path $filePath -Value $content -Encoding $encoding
                Write-Host "Corregido: $filePath"
            }
        } catch {
            Write-Warning "Error procesando $filePath : $_"
        }
    }
}

Write-Host "Corrección final completa..."
Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object {
    Fix-AllRemainingCharacters $_.FullName
}

Write-Host "Proceso completado."