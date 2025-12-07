# Script PowerShell para reemplazar referencias de BD en el archivo .jasper
$jasperFile = "src\reportsPackage\ListadoCosecheros.jasper"
$backupFile = "src\reportsPackage\ListadoCosecheros_original.jasper"

Write-Host "Creando backup del archivo original..."
Copy-Item $jasperFile $backupFile -Force

Write-Host "Leyendo archivo .jasper como bytes..."
$bytes = [System.IO.File]::ReadAllBytes($jasperFile)
$content = [System.Text.Encoding]::UTF8.GetString($bytes)

# Buscar y reemplazar las referencias a la BD
$oldPattern1 = "[db_aa764d_coopmanagerdb].[dbo].[cosecheros]"
$newPattern1 = "cosecheros"

$oldPattern2 = "[db_aa764d_coopmanagerdb].[dbo].[empresas]"
$newPattern2 = "empresas"

Write-Host "Buscando patrones a reemplazar..."
$hasChanges = $false

if ($content.Contains($oldPattern1)) {
    Write-Host "Encontrado: $oldPattern1"
    $content = $content.Replace($oldPattern1, $newPattern1)
    $hasChanges = $true
}

if ($content.Contains($oldPattern2)) {
    Write-Host "Encontrado: $oldPattern2"
    $content = $content.Replace($oldPattern2, $newPattern2)
    $hasChanges = $true
}

if ($hasChanges) {
    Write-Host "Guardando archivo modificado..."
    $newBytes = [System.Text.Encoding]::UTF8.GetBytes($content)
    [System.IO.File]::WriteAllBytes($jasperFile, $newBytes)
    Write-Host "Archivo .jasper actualizado correctamente"
} else {
    Write-Host "No se encontraron patrones para reemplazar"
}

Write-Host "Proceso completado."