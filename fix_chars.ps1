# Script para corregir caracteres corruptos en archivos Java
$replacements = @{
    'vï¿½lido' = 'válido'
    'nï¿½mero' = 'número'
    'Nï¿½mero' = 'Número'
    'categorï¿½a' = 'categoría'
    'Descripciï¿½n' = 'Descripción'
    'Aï¿½o' = 'Año'
    'aï¿½o' = 'año'
    'Talï¿½n' = 'Talón'
    'Comisiï¿½n' = 'Comisión'
    'descripciï¿½n' = 'descripción'
    'ï¿½xito' = 'éxito'
    'estï¿½' = 'está'
    'Direcciï¿½n' = 'Dirección'
    'Poblaciï¿½n' = 'Población'
    'Cï¿½digo' = 'Código'
    'Telï¿½fono' = 'Teléfono'
    'Dï¿½gito' = 'Dígito'
    'Importaciï¿½n' = 'Importación'
    'importaciï¿½n' = 'importación'
}

$count = 0
Get-ChildItem -Path "src\**\*.java" -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw -Encoding UTF8
    $originalContent = $content
    
    foreach ($key in $replacements.Keys) {
        $content = $content.Replace($key, $replacements[$key])
    }
    
    if ($content -ne $originalContent) {
        [System.IO.File]::WriteAllText($_.FullName, $content, [System.Text.Encoding]::UTF8)
        Write-Host "Fixed: $($_.Name)"
        $count++
    }
}

Write-Host "Total archivos corregidos: $count"