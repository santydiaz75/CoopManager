# Script para corregir todos los problemas SQL en reportes JasperReports
# Correcciones para SQL Server

# Obtener todos los archivos .jrxml
$jrxmlFiles = Get-ChildItem -Path ".\src\reportsPackage\*.jrxml" -Recurse

Write-Host "Iniciando corrección masiva de reportes SQL para SQL Server..."
Write-Host "Archivos encontrados: $($jrxmlFiles.Count)"

foreach ($file in $jrxmlFiles) {
    Write-Host "Procesando: $($file.Name)"
    
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    
    # 1. Corregir nombres de tabla sin brackets completos
    $content = $content -replace '\bCosecheros\b', '[db_aa764d_coopmanagerdb].[dbo].[cosecheros]'
    $content = $content -replace '\bEmpresas\b', '[db_aa764d_coopmanagerdb].[dbo].[empresas]'
    $content = $content -replace '\bEjercicios\b', '[db_aa764d_coopmanagerdb].[dbo].[ejercicios]'
    $content = $content -replace '\bCategorias\b', '[db_aa764d_coopmanagerdb].[dbo].[categorias]'
    $content = $content -replace '\bZonas\b', '[db_aa764d_coopmanagerdb].[dbo].[zonas]'
    $content = $content -replace '\bEntradasCabecera\b', '[db_aa764d_coopmanagerdb].[dbo].[entradascabecera]'
    $content = $content -replace '\bEntradasLineas\b', '[db_aa764d_coopmanagerdb].[dbo].[entradaslineas]'
    $content = $content -replace '\bVentas\b', '[db_aa764d_coopmanagerdb].[dbo].[ventas]'
    $content = $content -replace '\bVentasLineas\b', '[db_aa764d_coopmanagerdb].[dbo].[ventaslineas]'
    $content = $content -replace '\bLiquidacion\b', '[db_aa764d_coopmanagerdb].[dbo].[liquidacion]'
    $content = $content -replace '\bLiquidacionPagos\b', '[db_aa764d_coopmanagerdb].[dbo].[liquidacionpagos]'
    $content = $content -replace '\bFacturas\b', '[db_aa764d_coopmanagerdb].[dbo].[facturas]'
    $content = $content -replace '\bFacturasLineas\b', '[db_aa764d_coopmanagerdb].[dbo].[facturaslineas]'
    $content = $content -replace '\bRecibos\b', '[db_aa764d_coopmanagerdb].[dbo].[recibos]'
    $content = $content -replace '\bGastos\b', '[db_aa764d_coopmanagerdb].[dbo].[gastos]'
    $content = $content -replace '\bNominas\b', '[db_aa764d_coopmanagerdb].[dbo].[nominas]'
    $content = $content -replace '\bEmpleados\b', '[db_aa764d_coopmanagerdb].[dbo].[empleados]'
    $content = $content -replace '\bTiposCoste\b', '[db_aa764d_coopmanagerdb].[dbo].[tiposcoste]'
    $content = $content -replace '\bConceptos\b', '[db_aa764d_coopmanagerdb].[dbo].[conceptos]'
    $content = $content -replace '\bContadores\b', '[db_aa764d_coopmanagerdb].[dbo].[contadores]'
    $content = $content -replace '\bCalendario\b', '[db_aa764d_coopmanagerdb].[dbo].[calendario]'
    
    # 2. Reemplazar función CONCAT de MySQL por + de SQL Server
    $content = $content -replace 'concat\s*\(\s*([^,]+)\s*,\s*([^)]+)\)', '($1 + $2)'
    $content = $content -replace 'concat\s*\(\s*([^,]+)\s*,\s*''([^'']+)''\s*,\s*([^)]+)\)', '($1 + ''$2'' + $3)'
    $content = $content -replace 'concat\s*\(\s*''([^'']+)''\s*,\s*([^)]+)\)', '(''$1'' + $2)'
    
    # 3. Corregir comillas dobles por comillas simples para strings en SQL Server
    $content = $content -replace '"([^"]+)"', '''$1'''
    
    # 4. Corregir LIMIT por TOP en SQL Server (si existe)
    $content = $content -replace 'LIMIT\s+(\d+)', 'TOP $1'
    
    # 5. Corregir funciones de fecha específicas de MySQL
    $content = $content -replace 'NOW\(\)', 'GETDATE()'
    $content = $content -replace 'CURDATE\(\)', 'CAST(GETDATE() AS DATE)'
    
    # 6. Corregir nombres de campo con capitalización correcta para SQL Server
    $content = $content -replace '\bco\.empresa\b', 'co.Empresa'
    $content = $content -replace '\bco\.ejercicio\b', 'co.Ejercicio'
    $content = $content -replace '\bco\.idCosechero\b', 'co.IdCosechero'
    $content = $content -replace '\bco\.idcosechero\b', 'co.IdCosechero'
    $content = $content -replace '\bco\.nombre\b', 'co.Nombre'
    $content = $content -replace '\bco\.apellidos\b', 'co.Apellidos'
    $content = $content -replace '\bco\.direccion\b', 'co.Direccion'
    $content = $content -replace '\bco\.poblacion\b', 'co.Poblacion'
    $content = $content -replace '\bco\.provincia\b', 'co.Provincia'
    $content = $content -replace '\bco\.codigoPostal\b', 'co.CodigoPostal'
    $content = $content -replace '\bco\.nif\b', 'co.NIF'
    $content = $content -replace '\bco\.telefono\b', 'co.Telefono'
    
    # 7. Corregir agregaciones que pueden tener problemas
    $content = $content -replace 'GROUP_CONCAT', 'STRING_AGG'
    
    # Solo escribir si hay cambios
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
        Write-Host "  ? Corregido: $($file.Name)"
    } else {
        Write-Host "  - Sin cambios: $($file.Name)"
    }
}

Write-Host "`nCorrección completada. Compilando proyecto..."