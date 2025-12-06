# Corrección final de caracteres específicos
$files = Get-ChildItem -Path src -Filter "*.java" -Recurse
$corrections = @{
    'ï¿½' = 'ó'
    'parï¿½metro' = 'parámetro'
    'mï¿½s' = 'más'
    'diseï¿½ado' = 'diseñado'
    'aplicaciï¿½n' = 'aplicación'
    'configuraciï¿½n' = 'configuración'
}

$correctedCount = 0
foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::UTF8)
    $originalContent = $content
    
    foreach ($bad in $corrections.Keys) {
        $good = $corrections[$bad]
        $content = $content.Replace($bad, $good)
    }
    
    if ($content -ne $originalContent) {
        Write-Host "Corrigiendo: $($file.Name)"
        $utf8WithoutBom = [System.Text.UTF8Encoding]::new($false)
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8WithoutBom)
        $correctedCount++
    }
}

Write-Host "Corrección completada. Archivos corregidos: $correctedCount"