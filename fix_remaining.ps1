$encoding = [System.Text.Encoding]::GetEncoding("ISO-8859-1")

function Fix-RemainingCharacters {
    param([string]$filePath)
    
    if (Test-Path $filePath) {
        try {
            $content = Get-Content $filePath -Raw -Encoding $encoding
            $originalContent = $content
            
            # Reemplazos adicionales
            $content = $content -replace 'Comisiï¿½n', 'Comisión'
            $content = $content -replace 'selecciï¿½n', 'selección'
            $content = $content -replace 'lï¿½nea', 'línea'
            $content = $content -replace 'lï¿½neas', 'líneas'
            $content = $content -replace 'Talï¿½n', 'Talón'
            $content = $content -replace 'Categorï¿½as', 'Categorías'
            $content = $content -replace 'Vehï¿½culos', 'Vehículos'
            $content = $content -replace 'Añadir', 'Añadir'
            $content = $content -replace 'creación', 'creación'
            $content = $content -replace 'información', 'información'
            $content = $content -replace 'configuración', 'configuración'
            $content = $content -replace 'Descripción', 'Descripción'
            $content = $content -replace 'Posición', 'Posición'
            $content = $content -replace 'Razón', 'Razón'
            $content = $content -replace 'Función', 'Función'
            
            if ($content -ne $originalContent) {
                Set-Content -Path $filePath -Value $content -Encoding $encoding
                Write-Host "Corregido: $filePath"
            }
        } catch {
            Write-Warning "Error procesando $filePath : $_"
        }
    }
}

Write-Host "Corrigiendo caracteres españoles restantes..."
Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object {
    Fix-RemainingCharacters $_.FullName
}

Write-Host "Proceso completado."