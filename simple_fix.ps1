# Script simple para corregir caracteres ï¿½
$files = Get-ChildItem -Path "src" -Filter "*.java" -Recurse
$correctedCount = 0

foreach ($file in $files) {
    try {
        $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::GetEncoding("ISO-8859-1"))
        $originalContent = $content
        
        # Reemplazos específicos
        $content = $content -replace 'ï¿½', 'ó'
        $content = $content -replace 'aplicaciï¿½n', 'aplicación'
        $content = $content -replace 'configuraciï¿½n', 'configuración'
        $content = $content -replace 'liquidaciï¿½n', 'liquidación'
        $content = $content -replace 'producci7n', 'producción'
        $content = $content -replace 'Contabilizaciï¿½n', 'Contabilización'
        $content = $content -replace 'Preliquidaciï¿½n', 'Preliquidación'
        $content = $content -replace 'parï¿½metro', 'parámetro'
        $content = $content -replace 'diseï¿½ado', 'diseñado'
        $content = $content -replace 'vï¿½lido', 'válido'
        $content = $content -replace 'Cï¿½digo', 'Código'
        $content = $content -replace 'cï¿½digo', 'código'
        $content = $content -replace 'Nï¿½mero', 'Número'
        $content = $content -replace 'nï¿½mero', 'número'
        $content = $content -replace 'Polï¿½gono', 'Polígono'
        $content = $content -replace 'mï¿½s', 'más'
        
        if ($content -ne $originalContent) {
            Write-Host "Corrigiendo: $($file.Name)"
            $utf8WithoutBom = [System.Text.UTF8Encoding]::new($false)
            [System.IO.File]::WriteAllText($file.FullName, $content, $utf8WithoutBom)
            $correctedCount++
        }
    }
    catch {
        Write-Warning "Error en $($file.Name): $($_.Exception.Message)"
    }
}

Write-Host "Archivos corregidos: $correctedCount"