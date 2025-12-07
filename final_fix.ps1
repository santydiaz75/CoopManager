$encoding = [System.Text.Encoding]::GetEncoding("ISO-8859-1")

function Fix-FinalCharacters {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            $content = Get-Content $filePath -Raw -Encoding $encoding
            $originalContent = $content
            
            # Reemplazos finales
            $content = $content -replace 'vï¿½lido', 'válido'
            $content = $content -replace 'vï¿½lida', 'válida'
            $content = $content -replace 'Relaciï¿½n', 'Relación'
            $content = $content -replace 'relaciï¿½n', 'relación'
            $content = $content -replace 'campaï¿½a', 'campaña'
            $content = $content -replace 'nï¿½minas', 'nóminas'
            $content = $content -replace 'Importaciï¿½n', 'Importación'
            $content = $content -replace 'aplicaciï¿½n', 'aplicación'
            $content = $content -replace 'Informaciï¿½n', 'Información'
            $content = $content -replace 'informaciï¿½n', 'información'
            $content = $content -replace 'operaciï¿½n', 'operación'
            $content = $content -replace 'Operaciï¿½n', 'Operación'
            $content = $content -replace 'configuraciï¿½n', 'configuración'
            $content = $content -replace 'Configuraciï¿½n', 'Configuración'
            $content = $content -replace 'versión', 'versión'
            $content = $content -replace 'Versión', 'Versión'
            
            if ($content -ne $originalContent) {
                Set-Content -Path $filePath -Value $content -Encoding $encoding
                Write-Host "Corregido: $filePath"
            }
        } catch {
            Write-Warning "Error procesando $filePath : $_"
        }
    }
}

Write-Host "Corrección final de caracteres españoles..."
Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object {
    Fix-FinalCharacters $_.FullName
}

Write-Host "Proceso completado."