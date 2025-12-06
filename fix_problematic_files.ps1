# Fix all problematic files that still have illegal characters
$problematicFiles = @(
    "src/winUIPackage/FrmConcepto.java",
    "src/winUIPackage/FrmConductor.java", 
    "src/winUIPackage/FrmContaCobros.java",
    "src/winUIPackage/FrmContaFacturas.java",
    "src/winUIPackage/FrmContaLiquidaciones.java",
    "src/winUIPackage/FrmContaLiquidacionesPagos.java",
    "src/winUIPackage/FrmContaNominas.java",
    "src/winUIPackage/FrmContaPagos.java",
    "src/winUIPackage/FrmControlCalidad.java",
    "src/winUIPackage/FrmCosechero.java"
)

foreach ($file in $problematicFiles) {
    if (Test-Path $file) {
        Write-Host "Fixing $file"
        
        # Read content as bytes
        $bytes = [System.IO.File]::ReadAllBytes($file)
        
        # Convert problematic bytes
        $content = [System.Text.Encoding]::UTF8.GetString($bytes)
        
        # Remove BOM if present
        if ($content.StartsWith([char]0xFEFF)) {
            $content = $content.Substring(1)
        }
        
        # Remove specific problematic Unicode characters
        $content = $content -replace [char]0x00BB, ""  # » character
        $content = $content -replace [char]0x00BF, ""  # ¿ character
        $content = $content -replace [char]0xFEFF, ""  # BOM
        
        # Write back as UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file, $content, $utf8NoBom)
        
        Write-Host "Fixed $file"
    }
}

Write-Host "Done fixing problematic files"