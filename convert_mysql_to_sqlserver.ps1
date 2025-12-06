# Script para convertir sintaxis MySQL a SQL Server en archivos .jrxml

# Obtener todos los archivos .jrxml en el directorio src (no target)
$jrxmlFiles = Get-ChildItem -Path "c:\Development\CoopManager\CoopManager\src\reportsPackage\*.jrxml" -Recurse

Write-Host "Encontrados $($jrxmlFiles.Count) archivos .jrxml para procesar"

foreach ($file in $jrxmlFiles) {
    Write-Host "Procesando: $($file.Name)"
    
    # Leer el contenido del archivo
    $content = Get-Content $file.FullName -Raw -Encoding UTF8
    
    # Realizar los reemplazos
    $originalContent = $content
    
    # 1. Reemplazar referencias de esquema y tabla con backticks por brackets
    $content = $content -replace '`coopmanagerdb`\.`([^`]+)`', '[db_aa764d_coopmanagerdb].[dbo].[$1]'
    
    # 2. Reemplazar funciones MySQL por SQL Server
    $content = $content -replace '`coopmanagerdb`\.([a-zA-Z_][a-zA-Z0-9_]*)\(', '[db_aa764d_coopmanagerdb].[dbo].[$1]('
    
    # 3. Reemplazar nombres de tabla simples con backticks
    $content = $content -replace '`([a-zA-Z_][a-zA-Z0-9_]*)`', '[$1]'
    
    # 4. Operador de concatenación MySQL (+) por SQL Server (CONCAT o +)
    # Para concatenaciones simples usamos + en SQL Server
    $content = $content -replace 'concat\(concat\(([^,]+),\s*([^,]+)\),\s*([^)]+)\)', 'CONCAT($1, $2, $3)'
    
    # 5. Función YEAR de MySQL a SQL Server (ya es compatible)
    # year() funciona en ambos
    
    # Si hubo cambios, escribir el archivo
    if ($content -ne $originalContent) {
        $content | Set-Content -Path $file.FullName -Encoding UTF8
        Write-Host "  -> Actualizado" -ForegroundColor Green
    } else {
        Write-Host "  -> Sin cambios" -ForegroundColor Yellow
    }
}

Write-Host "`nConversión completada!" -ForegroundColor Cyan