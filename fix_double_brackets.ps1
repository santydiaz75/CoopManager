# Script para corregir dobles brackets en reportes
# Busca y corrige patrones como [[db_aa764d_coopmanagerdb].[dbo].[tabla]]

# Obtener todos los archivos .jrxml
$jrxmlFiles = Get-ChildItem -Path ".\src\reportsPackage\*.jrxml" -Recurse

Write-Host "Buscando y corrigiendo dobles brackets en reportes..."
Write-Host "Archivos a revisar: $($jrxmlFiles.Count)"

$totalFixed = 0

foreach ($file in $jrxmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    
    # Corregir dobles brackets que pueden haber aparecido después de aplicar el script anterior
    $content = $content -replace '\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[([^\]]+)\]\]', '[db_aa764d_coopmanagerdb].[dbo].[$1]'
    
    # Corregir cualquier patrón de dobles brackets que quede
    $content = $content -replace '\[\[([^\]]+)\]\]', '[$1]'
    
    # Corregir sintaxis de concatenación incorrecta específica
    $content = $content -replace '\(\(([^,]+) \+ ''([^'']+)''\), ([^)]+)\)', '($1 + ''$2'' + $3)'
    $content = $content -replace '\(concat\(([^,]+) \+ ''([^'']+)''\), ([^)]+)\)', '($1 + ''$2'' + $3)'
    
    # Corregir concatenaciones mal formadas
    $content = $content -replace '\(\(([^,]+), ([^)]+)\)', '($1 + $2)'
    
    # Solo escribir si hay cambios
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
        Write-Host "  ? Corregido: $($file.Name)"
        $totalFixed++
    } else {
        Write-Host "  - Sin cambios: $($file.Name)"
    }
}

Write-Host "`nTotal de archivos corregidos: $totalFixed"
Write-Host "Verificando sintaxis SQL específica..."

# Buscar problemas específicos en algunos archivos críticos
$criticalFiles = @("ListadoCosecheros.jrxml", "PreLiquidacion.jrxml", "FacturaLiquidacion.jrxml")

foreach ($fileName in $criticalFiles) {
    $filePath = ".\src\reportsPackage\$fileName"
    if (Test-Path $filePath) {
        $content = Get-Content -Path $filePath -Raw -Encoding UTF8
        
        # Buscar patrones problemáticos
        if ($content -match '\[\[.*\]\]') {
            Write-Host "??  ADVERTENCIA: $fileName aún contiene dobles brackets"
        }
        if ($content -match 'concat\s*\([^)]*\+[^)]*\)') {
            Write-Host "??  ADVERTENCIA: $fileName contiene sintaxis de concat incorrecta"
        }
        if ($content -match '\(\([^)]*\)') {
            Write-Host "??  ADVERTENCIA: $fileName contiene paréntesis dobles"
        }
    }
}

Write-Host "`nCorrección de dobles brackets completada."