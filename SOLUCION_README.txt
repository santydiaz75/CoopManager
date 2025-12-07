/*
 * Backup de ListadoCosecheros.java original antes de aplicar el fix
 * Se creo el archivo ListadoCosecherosFixed.java como solucion alternativa
 * 
 * SOLUCION APLICADA: 
 * Interceptar la ejecución del reporte y usar JRResultSetDataSource 
 * con consulta SQL corregida (sin referencias hardcodeadas a base de datos)
 * 
 * CONSULTA ORIGINAL PROBLEMATICA:
 * FROM [db_aa764d_coopmanagerdb].[dbo].[cosecheros] c
 * 
 * CONSULTA CORREGIDA:
 * FROM cosecheros c
 * 
 * Esta solucion evita el problema de codificación UTF-8 en el archivo .jrxml
 * y proporciona los datos correctamente al reporte JasperReports.
 */