# Script final de corrección de caracteres específicos

Write-Host "=== Corrección específica de caracteres restantes ===" -ForegroundColor Green

# Definir correcciones específicas por archivo
$specificCorrections = @(
    @{
        File = "src\reportsPackage\ControlESAlmacen.jrxml"
        Corrections = @{
            'CATEGORï¿½AS' = 'CATEGORÍAS'
            'Pï¿½gina' = 'Página'
        }
    },
    @{
        File = "src\reportsPackage\ControlCalidad.jrxml"
        Corrections = @{
            'Pï¿½gina' = 'Página'
        }
    },
    @{
        File = "src\reportsPackage\CartaCosecheros.jrxml"
        Corrections = @{
            'Jesï¿½s' = 'Jesús'
            'Dï¿½az' = 'Díaz'
            'Falcï¿½n' = 'Falcón'
        }
    },
    @{
        File = "src\reportsPackage\AyudasOCM.jrxml"
        Corrections = @{
            'RELACIï¿½N' = 'RELACIÓN'
            'Pï¿½gina' = 'Página'
        }
    },
    @{
        File = "src\reportsPackage\FacturaLiquidacion.jrxml"
        Corrections = @{
            'LIQUIDACIï¿½N' = 'LIQUIDACIÓN'
            'Cï¿½DIGO' = 'CÓDIGO'
        }
    }
)

$totalCorrected = 0

foreach ($fileConfig in $specificCorrections) {
    $filePath = $fileConfig.File
    
    if (Test-Path $filePath) {
        Write-Host "`nProcesando: $($filePath | Split-Path -Leaf)" -ForegroundColor Yellow
        
        try {
            # Leer contenido del archivo
            $content = Get-Content -Path $filePath -Raw -Encoding UTF8
            $originalContent = $content
            
            # Aplicar todas las correcciones para este archivo
            foreach ($correction in $fileConfig.Corrections.GetEnumerator()) {
                $oldString = $correction.Key
                $newString = $correction.Value
                
                if ($content -like "*$oldString*") {
                    $content = $content -replace [regex]::Escape($oldString), $newString
                    Write-Host "  ? '$oldString' ? '$newString'" -ForegroundColor Cyan
                }
            }
            
            # Guardar si hubo cambios
            if ($content -ne $originalContent) {
                $utf8NoBom = New-Object System.Text.UTF8Encoding $false
                [System.IO.File]::WriteAllText((Resolve-Path $filePath), $content, $utf8NoBom)
                Write-Host "  ? Archivo actualizado" -ForegroundColor Green
                $totalCorrected++
            }
            else {
                Write-Host "  ? No necesita cambios" -ForegroundColor Gray
            }
        }
        catch {
            Write-Host "  ? Error: $($_.Exception.Message)" -ForegroundColor Red
        }
    }
    else {
        Write-Host "`nArchivo no encontrado: $filePath" -ForegroundColor Red
    }
}

Write-Host "`n=== RESUMEN ===" -ForegroundColor Green
Write-Host "Archivos corregidos: $totalCorrected" -ForegroundColor White

if ($totalCorrected -gt 0) {
    Write-Host "`n? Correcciones específicas completadas" -ForegroundColor Green
} else {
    Write-Host "`n? Todos los archivos ya estaban correctos" -ForegroundColor Green
}