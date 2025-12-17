// PARCHE PARA CORREGIR ERROR DE CONVERSIÓN DE FECHA
// Problema: createSearchQueryWhere() en FrmEntityView.java está comparando fechas con strings
// Error específico: "Conversion failed when converting date and/or time from character string"
// 
// ARCHIVO: src/winUIPackage/FrmEntityView.java
// MÉTODO: createSearchQueryWhere() - líneas 587-628
//
// PROBLEMA ACTUAL:
// Cuando usuario busca "1", se genera:
//   id.fecha = '1'  ? ESTO ES INVÁLIDO para campo fecha
//
// SOLUCIÓN:
// 1. Añadir tipo "DateType" a EntityType.java
// 2. Modificar createSearchQueryWhere() para manejar fechas correctamente
// 3. Solo buscar en campos de fecha si el input parece una fecha válida

// PASO 1: Añadir a EntityType.java después de línea 45
/*
final public static String DateType = "Date";
final public static String NumberType = "Number";
final public static String TextType = "Text";
// AÑADIR:
final public static String DateTimeType = "DateTime";  // Para campos de fecha/hora
*/

// PASO 2: Modificar createSearchQueryWhere() para manejar fechas:

/*
REEMPLAZAR este código (líneas ~605-625):

if ((mSearch != null) && (!mSearch.equals(""))) {
    resultwhere = resultwhere + " and (";
    
    for (int i = 0; i < filterfields.length; i++) {
        if (fieldtypes[i] == EntityType.TextType)
            resultwhere = resultwhere + "(" + filterfields[i] + " like '%" + mSearch + "%') or ";
        else {
            if ((fieldtypes[i] == EntityType.NumberType) && isNumeric(mSearch))
                resultwhere = resultwhere + "(" + filterfields[i] + " = " + mSearch + ") or ";
            else
                resultwhere = resultwhere + "(" + filterfields[i] + " = '" + mSearch + "') or ";  // ? PROBLEMA AQUÍ
        }
    }
    resultwhere = (String) resultwhere.subSequence(0, resultwhere.length() - 3);
    resultwhere = resultwhere + ")";
}

POR ESTE CÓDIGO CORREGIDO:

if ((mSearch != null) && (!mSearch.equals(""))) {
    resultwhere = resultwhere + " and (";
    
    for (int i = 0; i < filterfields.length; i++) {
        if (fieldtypes[i] == EntityType.TextType) {
            resultwhere = resultwhere + "(" + filterfields[i] + " like '%" + mSearch + "%') or ";
        }
        else if (fieldtypes[i] == EntityType.NumberType && isNumeric(mSearch)) {
            resultwhere = resultwhere + "(" + filterfields[i] + " = " + mSearch + ") or ";
        }
        else if (fieldtypes[i] == EntityType.DateType || fieldtypes[i] == EntityType.DateTimeType) {
            // Solo buscar en fechas si parece una fecha válida
            if (isValidDateFormat(mSearch)) {
                resultwhere = resultwhere + "(" + filterfields[i] + " = '" + mSearch + "') or ";
            }
            // Si no es fecha válida, OMITIR este campo (no generar condición)
        }
        else {
            // Para otros tipos, solo si es numérico
            if (isNumeric(mSearch)) {
                resultwhere = resultwhere + "(" + filterfields[i] + " = " + mSearch + ") or ";
            }
        }
    }
    
    // Quitar último " or " si existe
    if (resultwhere.endsWith(" or ")) {
        resultwhere = resultwhere.substring(0, resultwhere.length() - 4);
    }
    resultwhere = resultwhere + ")";
    
    // Si no hay condiciones de búsqueda válidas (solo "(1=1) and ()")
    if (resultwhere.endsWith("()")) {
        resultwhere = resultwhere.substring(0, resultwhere.length() - 6); // Quitar " and ()"
    }
}
*/

// PASO 3: Añadir método para validar formato de fecha:

/*
AÑADIR este método después de isNumeric():

private static boolean isValidDateFormat(String dateString) {
    try {
        // Intentar varios formatos de fecha comunes
        SimpleDateFormat[] formats = {
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("MM/dd/yyyy"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("dd-MM-yyyy")
        };
        
        for (SimpleDateFormat format : formats) {
            try {
                format.setLenient(false);
                format.parse(dateString);
                return true; // Si algún formato funciona, es fecha válida
            } catch (ParseException ignored) {
                // Continuar con siguiente formato
            }
        }
        return false; // Ningún formato funcionó
        
    } catch (Exception e) {
        return false;
    }
}
*/

// VERIFICAR TAMBIÉN:
// 1. Definición de fieldtypes[] en Viewentradasquery - asegurarse que campo fecha use DateType
// 2. Todos los usos de Viewentradasquery para marcar correctamente el tipo de campo fecha

Write-Host "?? RESUMEN DEL PROBLEMA:" -ForegroundColor Cyan
Write-Host "   ? Archivo: src/winUIPackage/FrmEntityView.java" -ForegroundColor White
Write-Host "   ? Método: createSearchQueryWhere() (líneas 587-628)" -ForegroundColor White
Write-Host "   ? Error: Comparación de fechas con strings inválidos" -ForegroundColor Red
Write-Host "   ? Solución: Validar formato antes de generar condición de búsqueda" -ForegroundColor Green

Write-Host "`n???  ACCIONES REQUERIDAS:" -ForegroundColor Yellow
Write-Host "   1. Editar EntityType.java - añadir DateTimeType" -ForegroundColor Gray
Write-Host "   2. Editar FrmEntityView.java - mejorar createSearchQueryWhere()" -ForegroundColor Gray
Write-Host "   3. Añadir método isValidDateFormat()" -ForegroundColor Gray
Write-Host "   4. Verificar definición de tipos en Viewentradasquery" -ForegroundColor Gray

Write-Host "`n?? WORKAROUND TEMPORAL:" -ForegroundColor Magenta
Write-Host "   ? Evite buscar solo números en pantallas con fechas" -ForegroundColor Gray
Write-Host "   ? Use formatos de fecha específicos: 2025-01-01" -ForegroundColor Gray
Write-Host "   ? Busque por texto en lugar de números para campos mixtos" -ForegroundColor Gray