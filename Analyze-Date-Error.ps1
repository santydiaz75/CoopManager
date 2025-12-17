# Script para analizar y solucionar errores de conversión de fecha en Hibernate

Write-Host "?? ANALIZADOR DE ERRORES DE FECHA EN HIBERNATE" -ForegroundColor Red
Write-Host "=============================================" -ForegroundColor Red

Write-Host "`n?? ERROR DETECTADO:" -ForegroundColor Yellow
Write-Host "   'Conversion failed when converting date and/or time from character string'" -ForegroundColor Red

Write-Host "`n?? ANÁLISIS DEL PROBLEMA:" -ForegroundColor Cyan
Write-Host "   1. La consulta está intentando convertir '1' como fecha:" -ForegroundColor Gray
Write-Host "      or (id.fecha = '1')" -ForegroundColor Red
Write-Host "   2. El campo 'fecha' es de tipo DATE/DATETIME" -ForegroundColor Gray
Write-Host "   3. '1' no es un formato de fecha válido" -ForegroundColor Gray

Write-Host "`n???  POSIBLES SOLUCIONES:" -ForegroundColor Green

Write-Host "`n1. VERIFICAR CONFIGURACIÓN DE BASE DE DATOS:" -ForegroundColor Yellow
$sqlCheck = @"
-- Verificar estructura de la tabla
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'viewentradasquery' AND COLUMN_NAME = 'Fecha';

-- Verificar datos en el campo fecha
SELECT TOP 10 Fecha, COUNT(*) as cantidad
FROM viewentradasquery 
GROUP BY Fecha 
ORDER BY cantidad DESC;

-- Buscar registros con fechas problemáticas
SELECT * FROM viewentradasquery 
WHERE Fecha IS NULL OR ISDATE(Fecha) = 0;
"@

Write-Host "   Ejecute en SQL Server Management Studio:" -ForegroundColor Gray
Write-Host $sqlCheck -ForegroundColor Cyan

Write-Host "`n2. VERIFICAR MAPPING DE HIBERNATE:" -ForegroundColor Yellow
Write-Host "   Busque en el código Java el mapping para 'Viewentradasquery'" -ForegroundColor Gray
Write-Host "   El campo 'fecha' debe estar mapeado como:" -ForegroundColor Gray
Write-Host "   @Column(name = ""Fecha"")" -ForegroundColor Cyan
Write-Host "   @Temporal(TemporalType.DATE)" -ForegroundColor Cyan
Write-Host "   private Date fecha;" -ForegroundColor Cyan

Write-Host "`n3. PROBLEMA EN LA LÓGICA DE BÚSQUEDA:" -ForegroundColor Yellow
Write-Host "   La consulta actual compara:" -ForegroundColor Gray
Write-Host "   - Números con fechas: id.fecha = '1'" -ForegroundColor Red
Write-Host "   Debería ser:" -ForegroundColor Gray
Write-Host "   - Solo campos numéricos: id.idEntrada = 1" -ForegroundColor Green
Write-Host "   - Fechas con formato válido: id.fecha = '2025-01-01'" -ForegroundColor Green

Write-Host "`n4. CONFIGURACIÓN HIBERNATE MEJORADA:" -ForegroundColor Yellow
$hibernateConfig = @"
# En hibernate.cfg.xml o application.properties:
hibernate.dialect=org.hibernate.dialect.SQLServerDialect
hibernate.connection.driver_class=com.microsoft.sqlserver.jdbc.SQLServerDriver
hibernate.jdbc.time_zone=Europe/Madrid
hibernate.type.preferred_instant_jdbc_type=TIMESTAMP
"@
Write-Host $hibernateConfig -ForegroundColor Cyan

Write-Host "`n5. BUSCAR EN EL CÓDIGO JAVA:" -ForegroundColor Yellow
Write-Host "   Busque archivos que contengan 'Viewentradasquery' o consultas HQL/Criteria que:" -ForegroundColor Gray
Write-Host "   - Usen parámetros genéricos para búsqueda" -ForegroundColor Gray
Write-Host "   - Comparen todos los campos con el mismo valor '1'" -ForegroundColor Gray

# Buscar archivos Java relacionados
Write-Host "`n?? BUSCANDO ARCHIVOS JAVA RELACIONADOS:" -ForegroundColor Cyan
$javaFiles = Get-ChildItem -Path "src" -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | 
    Where-Object { (Get-Content $_.FullName -Raw) -match "Viewentradasquery|fecha.*=.*1" }

if ($javaFiles) {
    Write-Host "   Archivos encontrados:" -ForegroundColor Green
    foreach ($file in $javaFiles) {
        Write-Host "   ?? $($file.FullName)" -ForegroundColor Yellow
        
        # Mostrar líneas que contienen el patrón problemático
        $lines = Get-Content $file.FullName | Select-String -Pattern "(fecha.*=|Viewentradasquery)" -Context 1
        foreach ($line in $lines | Select-Object -First 3) {
            Write-Host "      Línea $($line.LineNumber): $($line.Line)" -ForegroundColor Gray
        }
    }
} else {
    Write-Host "   ? No se encontraron archivos Java en ./src" -ForegroundColor Red
    Write-Host "   ?? El problema puede estar en:" -ForegroundColor Yellow
    Write-Host "      - Consultas dinámicas generadas en runtime" -ForegroundColor Gray
    Write-Host "      - Código en una librería JAR externa" -ForegroundColor Gray
}

Write-Host "`n?? RECOMENDACIÓN INMEDIATA:" -ForegroundColor Magenta
Write-Host "   1. Modifique la lógica de búsqueda para NO comparar fechas con números" -ForegroundColor White
Write-Host "   2. Use validación de tipos antes de crear consultas dinámicas" -ForegroundColor White
Write-Host "   3. Configure el timezone correctamente en Hibernate" -ForegroundColor White

Write-Host "`n?? Para debugging adicional, ejecute:" -ForegroundColor Cyan
Write-Host "   .\Debug-Hibernate.ps1 -ShowSQL -LogFile ""hibernate-fecha-debug.log""" -ForegroundColor Gray