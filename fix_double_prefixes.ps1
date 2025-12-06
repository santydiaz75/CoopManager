# Script para corregir prefijos dobles específicos en funciones SQL
# Corrige [db_aa764d_coopmanagerdb].[dbo].[db_aa764d_coopmanagerdb].[dbo].

# Obtener todos los archivos .jrxml
$jrxmlFiles = Get-ChildItem -Path ".\src\reportsPackage\*.jrxml" -Recurse

Write-Host "Corrigiendo prefijos dobles en funciones SQL..."
Write-Host "Archivos encontrados: $($jrxmlFiles.Count)"

$totalFixed = 0

foreach ($file in $jrxmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    
    # Corregir prefijos dobles específicos
    $content = $content -replace '\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.', '[db_aa764d_coopmanagerdb].[dbo].'
    
    # Corregir cualquier triple prefijo que pueda haber quedado
    $content = $content -replace '\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.', '[db_aa764d_coopmanagerdb].[dbo].'
    
    # Solo escribir si hay cambios
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
        Write-Host "  ? Corregido prefijos dobles: $($file.Name)"
        $totalFixed++
    } else {
        Write-Host "  - Sin prefijos dobles: $($file.Name)"
    }
}

Write-Host "`nTotal de archivos con prefijos dobles corregidos: $totalFixed"

# Verificar que no queden prefijos dobles
Write-Host "`nVerificando que no queden prefijos dobles..."
$problemsFound = $false

foreach ($file in $jrxmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    
    if ($content -match '\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.\[db_aa764d_coopmanagerdb\]\.\[dbo\]\.') {
        Write-Host "??  $($file.Name) aún contiene prefijos dobles"
        $problemsFound = $true
    }
}

if (-not $problemsFound) {
    Write-Host "? No se encontraron más prefijos dobles."
} 

Write-Host "`nCorrección de prefijos dobles completada."