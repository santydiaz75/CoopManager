# Script avanzado para corregir problemas SQL específicos de SQL Server
# Corrige funciones personalizadas, concatenaciones mal formadas y otros problemas

# Obtener todos los archivos .jrxml
$jrxmlFiles = Get-ChildItem -Path ".\src\reportsPackage\*.jrxml" -Recurse

Write-Host "Iniciando corrección avanzada de problemas SQL específicos..."
Write-Host "Archivos encontrados: $($jrxmlFiles.Count)"

$totalFixed = 0

foreach ($file in $jrxmlFiles) {
    Write-Host "Procesando: $($file.Name)"
    
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    
    # 1. Normalizar todas las funciones personalizadas para usar sintaxis completa
    $content = $content -replace '\[CategoriaGetGrupo\]', '[db_aa764d_coopmanagerdb].[dbo].[CategoriaGetGrupo]'
    $content = $content -replace '\[EntradasKilosGrupoCosecheroCategoria\]', '[db_aa764d_coopmanagerdb].[dbo].[EntradasKilosGrupoCosecheroCategoria]'
    $content = $content -replace '\[PreLiquidacionGetBaseImponible\]', '[db_aa764d_coopmanagerdb].[dbo].[PreLiquidacionGetBaseImponible]'
    $content = $content -replace '\[PreLiquidacionGetNumKilos\]', '[db_aa764d_coopmanagerdb].[dbo].[PreLiquidacionGetNumKilos]'
    $content = $content -replace '\[PreLiquidacionGetNumPinas\]', '[db_aa764d_coopmanagerdb].[dbo].[PreLiquidacionGetNumPinas]'
    $content = $content -replace '\[PreLiquidacionKilosInutilizadosCosechero\]', '[db_aa764d_coopmanagerdb].[dbo].[PreLiquidacionKilosInutilizadosCosechero]'
    $content = $content -replace '\[CosecheroGetGrupo\]', '[db_aa764d_coopmanagerdb].[dbo].[CosecheroGetGrupo]'
    $content = $content -replace '\[ImporteGastosByTipoCoste\]', '[db_aa764d_coopmanagerdb].[dbo].[ImporteGastosByTipoCoste]'
    $content = $content -replace '\[ImporteNominas\]', '[db_aa764d_coopmanagerdb].[dbo].[ImporteNominas]'
    $content = $content -replace '\[ImporteGastosTotal\]', '[db_aa764d_coopmanagerdb].[dbo].[ImporteGastosTotal]'
    $content = $content -replace '\[EntradaGetNumKilosTotal\]', '[db_aa764d_coopmanagerdb].[dbo].[EntradaGetNumKilosTotal]'
    $content = $content -replace '\[ImporteIngresosExplotacion\]', '[db_aa764d_coopmanagerdb].[dbo].[ImporteIngresosExplotacion]'
    $content = $content -replace '\[ImporteLiquidaciones\]', '[db_aa764d_coopmanagerdb].[dbo].[ImporteLiquidaciones]'
    
    # 2. Corregir concatenaciones mal formadas específicas
    $content = $content -replace '\(co\.Apellidos \+ '' \+ '' \+ co\.Nombre\)', '(co.Apellidos + '', '' + co.Nombre)'
    $content = $content -replace '\(e\.nombre \+ '' '', coalesce\(e\.apellidos, ''''\)\)', '(e.nombre + '' '' + ISNULL(e.apellidos, ''''))'
    $content = $content -replace '\(co\.Apellidos \+ '' \+ '', co\.Nombre\)', '(co.Apellidos + '', '' + co.Nombre)'
    
    # 3. Corregir funciones de agregación específicas de MySQL
    $content = $content -replace 'GROUP_CONCAT\s*\(([^)]+)\s+ORDER BY\s+([^)]+)\s+SEPARATOR\s+''([^'']+)''\)', 'STRING_AGG($1, ''$3'') WITHIN GROUP (ORDER BY $2)'
    $content = $content -replace 'GROUP_CONCAT\s*\(([^)]+)\s+SEPARATOR\s+''([^'']+)''\)', 'STRING_AGG($1, ''$2'')'
    
    # 4. Corregir operadores específicos
    $content = $content -replace '\bDIV\b', '/'
    $content = $content -replace '\bMOD\b', '%'
    
    # 5. Corregir funciones de fecha específicas
    $content = $content -replace 'YEAR\s*\(\s*([^)]+)\s*\)', 'DATEPART(YEAR, $1)'
    $content = $content -replace 'MONTH\s*\(\s*([^)]+)\s*\)', 'DATEPART(MONTH, $1)'
    $content = $content -replace 'DAY\s*\(\s*([^)]+)\s*\)', 'DATEPART(DAY, $1)'
    $content = $content -replace 'WEEK\s*\(\s*([^)]+)\s*\)', 'DATEPART(WEEK, $1)'
    
    # 6. Corregir tipos de datos específicos de MySQL
    $content = $content -replace '\bTINYINT\b', 'SMALLINT'
    $content = $content -replace '\bMEDIUMINT\b', 'INT'
    $content = $content -replace '\bBIGINT UNSIGNED\b', 'BIGINT'
    
    # 7. Corregir subconsultas con EXISTS
    $content = $content -replace 'EXISTS\s*\(\s*SELECT\s+\*\s+FROM', 'EXISTS (SELECT 1 FROM'
    
    # 8. Corregir CASE WHEN con sintaxis específica
    $content = $content -replace 'CASE\s+([^W][^H][^E][^N])', 'CASE WHEN $1'
    
    # 9. Corregir uso de IFNULL por ISNULL
    $content = $content -replace 'IFNULL\s*\(', 'ISNULL('
    
    # 10. Corregir REGEXP por LIKE con patrones
    $content = $content -replace '([^'']+)\s+REGEXP\s+''([^'']+)''', '$1 LIKE ''%$2%'''
    
    # Solo escribir si hay cambios
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
        Write-Host "  ? Corregido: $($file.Name)"
        $totalFixed++
    } else {
        Write-Host "  - Sin cambios: $($file.Name)"
    }
}

Write-Host "`nTotal de archivos corregidos en esta pasada: $totalFixed"

# Verificar problemas específicos que podrían quedar
Write-Host "`nVerificando patrones problemáticos restantes..."

$problemFiles = @()

foreach ($file in $jrxmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    
    # Buscar patrones problemáticos
    $problems = @()
    
    if ($content -match '\[\w+\]\s*\(') {
        $problems += "Funciones sin prefijo completo"
    }
    if ($content -match '\(\w+\.\w+ \+ ''[^'']*'' \+ ''[^'']*'', \w+\.\w+\)') {
        $problems += "Concatenación mal formada"
    }
    if ($content -match 'GROUP_CONCAT|IFNULL|REGEXP') {
        $problems += "Funciones MySQL no convertidas"
    }
    if ($content -match '\bLIMIT\s+\d+') {
        $problems += "LIMIT no convertido a TOP"
    }
    
    if ($problems.Count -gt 0) {
        $problemFiles += @{
            File = $file.Name
            Problems = $problems
        }
    }
}

if ($problemFiles.Count -gt 0) {
    Write-Host "`n??  Archivos con posibles problemas restantes:"
    foreach ($pf in $problemFiles) {
        Write-Host "  $($pf.File): $($pf.Problems -join ', ')"
    }
} else {
    Write-Host "`n? No se detectaron problemas adicionales."
}

Write-Host "`nCorrección avanzada completada."