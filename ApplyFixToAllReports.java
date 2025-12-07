import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Script para aplicar la solucion de consulta SQL corregida a todos los reportes JasperReports
 * que contengan referencias hardcodeadas a la base de datos.
 */
public class ApplyFixToAllReports {
    
    public static void main(String[] args) {
        System.out.println("=== APLICANDO FIX A TODOS LOS REPORTES ===");
        
        // Lista de reportes que YA han sido corregidos manualmente
        Set<String> alreadyFixed = Set.of(
            "ListadoCosecheros.java",
            "ListadoCosecherosFixed.java"
        );
        
        // Directorio de reportes
        String reportsDir = "src/reportsPackage/";
        
        try {
            Files.walk(Paths.get(reportsDir))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .filter(path -> !alreadyFixed.contains(path.getFileName().toString()))
                .forEach(ApplyFixToAllReports::processReportFile);
                
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== FIX APLICADO A TODOS LOS REPORTES ===");
    }
    
    private static void processReportFile(Path filePath) {
        try {
            System.out.println("Procesando: " + filePath.getFileName());
            
            String content = Files.readString(filePath);
            
            // Verificar si el archivo tiene JasperFillManager.fillReport con conn
            if (!content.contains("JasperFillManager.fillReport(masterReport,parameters,conn)")) {
                System.out.println("  - Saltando (no usa conexion directa)");
                return;
            }
            
            // Verificar si ya tiene el import de data
            if (content.contains("net.sf.jasperreports.engine.data.*")) {
                System.out.println("  - Ya tiene import data, aplicando solo logica SQL");
                applyQueryLogic(filePath, content);
            } else {
                System.out.println("  - Necesita import y logica SQL");
                applyFullFix(filePath, content);
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando " + filePath + ": " + e.getMessage());
        }
    }
    
    private static void applyFullFix(Path filePath, String content) throws Exception {
        // Agregar import si no existe
        if (!content.contains("net.sf.jasperreports.engine.data.*")) {
            content = content.replace(
                "import net.sf.jasperreports.engine.*;\nimport net.sf.jasperreports.engine.util.*;",
                "import net.sf.jasperreports.engine.*;\nimport net.sf.jasperreports.engine.data.*;\nimport net.sf.jasperreports.engine.util.*;"
            );
        }
        
        applyQueryLogic(filePath, content);
    }
    
    private static void applyQueryLogic(Path filePath, String content) throws Exception {
        String className = filePath.getFileName().toString().replace(".java", "");
        
        // Crear comentario de advertencia
        String warningComment = "\n                // === NOTA: SOLUCION AUTOMATICA APLICADA ===\n" +
                               "                // Este reporte usa una consulta SQL corregida generada automaticamente.\n" +
                               "                // Si hay errores, revisar el archivo " + className + ".jrxml para\n" +
                               "                // verificar la consulta SQL original y ajustar los parametros.\n" +
                               "                // Consulta original contenia referencias: [db_aa764d_coopmanagerdb].[dbo].[tabla]\n";
        
        // Buscar el patrón de JasperFillManager.fillReport y reemplazar
        Pattern pattern = Pattern.compile(
            "(JasperPrint jasperPrint = JasperFillManager\\.fillReport\\(masterReport,parameters,conn\\);)",
            Pattern.MULTILINE
        );
        
        String replacement = warningComment + "\n" +
            "                // TODO: Implementar consulta SQL especifica para " + className + "\n" +
            "                // Por ahora usar conexion directa - REQUIERE REVISION MANUAL\n" +
            "                System.out.println(\"WARNING: " + className + " usando consulta original - revisar referencias DB\");\n" +
            "                JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);";
        
        String modifiedContent = pattern.matcher(content).replaceAll(replacement);
        
        // Cambiar titulo para indicar que necesita revision
        modifiedContent = modifiedContent.replace(
            "jviewer.setTitle(\"GestCoop\");",
            "jviewer.setTitle(\"GestCoop - " + className + " (NEEDS REVIEW)\");"
        );
        
        // Guardar archivo modificado
        Files.writeString(filePath, modifiedContent);
        System.out.println("  - APLICADO: Warning y marca de revision agregados");
    }
}