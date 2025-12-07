import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Aplica el fix basico a todos los reportes que usan conexion directa
 */
public class ApplyBasicFixToReports {
    
    // Reportes que YA han sido corregidos manualmente
    private static final Set<String> ALREADY_FIXED = Set.of(
        "ListadoCosecheros.java",
        "ListadoCosecherosFixed.java",
        "ListadoCosecherosKilos.java",
        "ListadoPersonal.java",
        "ListadoPersonalBanco.java",
        "ListadoPersonalSalarioMedio.java"
    );
    
    public static void main(String[] args) {
        System.out.println("=== APLICANDO FIX BASICO A REPORTES RESTANTES ===");
        
        File reportsDir = new File("src/reportsPackage/");
        if (!reportsDir.exists()) {
            System.err.println("Directorio no encontrado: " + reportsDir.getAbsolutePath());
            return;
        }
        
        File[] javaFiles = reportsDir.listFiles((dir, name) -> 
            name.endsWith(".java") && !ALREADY_FIXED.contains(name));
        
        if (javaFiles == null) {
            System.err.println("No se pudieron listar los archivos");
            return;
        }
        
        for (File file : javaFiles) {
            processReport(file);
        }
        
        System.out.println("=== FIX BASICO APLICADO ===");
    }
    
    private static void processReport(File file) {
        try {
            System.out.println("Procesando: " + file.getName());
            
            String content = Files.readString(file.toPath());
            
            // Solo procesar archivos que usen JasperFillManager con conn
            if (!content.contains("JasperFillManager.fillReport(masterReport,parameters,conn)")) {
                System.out.println("  - Saltando (no usa conexion directa)");
                return;
            }
            
            boolean modified = false;
            
            // 1. Asegurar que tenga el import data
            if (!content.contains("net.sf.jasperreports.engine.data.*")) {
                content = content.replace(
                    "import net.sf.jasperreports.engine.*;",
                    "import net.sf.jasperreports.engine.*;\nimport net.sf.jasperreports.engine.data.*;"
                );
                modified = true;
                System.out.println("  - Import agregado");
            }
            
            // 2. Agregar warning antes de fillReport
            String reportName = file.getName().replace(".java", "");
            Pattern fillReportPattern = Pattern.compile(
                "(\\s*)(//Informe.*\\s*JasperPrint jasperPrint = JasperFillManager\\.fillReport\\(masterReport,parameters,conn\\);)"
            );
            
            Matcher matcher = fillReportPattern.matcher(content);
            if (matcher.find()) {
                String indent = matcher.group(1);
                String originalCall = matcher.group(2);
                
                String replacement = indent + "// === ADVERTENCIA: CONSULTA SQL SIN CORREGIR ===\n" +
                                   indent + "// Este reporte puede tener referencias hardcodeadas a la base de datos\n" +
                                   indent + "// En archivo " + reportName + ".jrxml\n" +
                                   indent + "// TODO: Implementar solucion especifica si hay errores SQL\n" +
                                   indent + "System.out.println(\"WARNING: " + reportName + " - verificar referencias DB en .jrxml\");\n" +
                                   indent + originalCall;
                
                content = matcher.replaceFirst(replacement);
                modified = true;
                System.out.println("  - Warning agregado");
            }
            
            // 3. Cambiar titulo para indicar estado
            content = content.replace(
                "jviewer.setTitle(\"GestCoop\");",
                "jviewer.setTitle(\"GestCoop - " + reportName + " (CHECK SQL)\");"
            );
            modified = true;
            
            // 4. Guardar si hubo cambios
            if (modified) {
                Files.writeString(file.toPath(), content);
                System.out.println("  - MODIFICADO: Warning y marca agregados");
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando " + file.getName() + ": " + e.getMessage());
        }
    }
}