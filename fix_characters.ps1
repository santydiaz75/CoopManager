$encoding = [System.Text.Encoding]::GetEncoding("ISO-8859-1")

function Fix-File {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            $content = Get-Content $filePath -Raw -Encoding $encoding
            $originalContent = $content
            
            # Reemplazos específicos
            $content = $content -replace 'Duraciï¿½n', 'Duración'
            $content = $content -replace 'liquidaciï¿½n', 'liquidación'
            $content = $content -replace 'Aï¿½o', 'Año'
            $content = $content -replace 'Autï¿½nomo', 'Autónomo'
            $content = $content -replace 'lï¿½quido', 'líquido'
            $content = $content -replace 'Bonificaciï¿½n', 'Bonificación'
            $content = $content -replace 'Sï¿½bado', 'Sábado'
            $content = $content -replace 'Nï¿½mero', 'Número'
            $content = $content -replace 'dï¿½as', 'días'
            $content = $content -replace 'vï¿½lido', 'válido'
            $content = $content -replace 'vï¿½lida', 'válida'
            $content = $content -replace 'aï¿½o', 'año'
            $content = $content -replace 'Poblaciï¿½n', 'Población'
            
            if ($content -ne $originalContent) {
                Set-Content -Path $filePath -Value $content -Encoding $encoding
                Write-Host "Corregido: $filePath"
            }
        } catch {
            Write-Warning "Error procesando $filePath : $_"
        }
    }
}

Write-Host "Corrigiendo caracteres españoles..."
Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object {
    Fix-File $_.FullName
}

Write-Host "Proceso completado."