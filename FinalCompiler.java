import java.io.*;
import net.sf.jasperreports.engine.*;

public class FinalCompiler {
    public static void main(String[] args) {
        try {
            System.out.println("=== COMPILADOR FINAL DE JASPER ===");
            
            String jrxmlPath = "src/reportsPackage/ListadoCosecheros.jrxml";
            String jasperPath = "src/reportsPackage/ListadoCosecheros.jasper";
            String jasperBackup = "src/reportsPackage/ListadoCosecheros_backup.jasper";
            
            // Hacer backup del archivo actual
            System.out.println("1. Creando backup...");
            copyFile(jasperPath, jasperBackup);
            
            // Compilar desde JRXML corregido
            System.out.println("2. Compilando desde JRXML corregido...");
            
            try {
                JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                System.out.println("EXITO: Reporte compilado correctamente");
                
                // Verificar que el archivo fue creado
                File newJasper = new File(jasperPath);
                if (newJasper.exists()) {
                    System.out.println("Archivo creado: " + newJasper.length() + " bytes");
                } else {
                    System.out.println("ERROR: Archivo no se creo");
                }
                
            } catch (JRException e) {
                System.err.println("ERROR JasperReports: " + e.getMessage());
                
                if (e.getMessage().contains("UTF-8") || e.getMessage().contains("encoding")) {
                    System.out.println("\nSOLUCION ALTERNATIVA:");
                    System.out.println("El problema es de codificacion en el archivo JRXML.");
                    System.out.println("Voy a restaurar el backup y usar el archivo actual:");
                    
                    // Restaurar backup si fallo
                    copyFile(jasperBackup, jasperPath);
                    System.out.println("Backup restaurado.");
                } else {
                    throw e;
                }
            }
            
        } catch (Exception e) {
            System.err.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void copyFile(String source, String dest) {
        try {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(dest);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            
            fis.close();
            fos.close();
            System.out.println("Archivo copiado: " + source + " -> " + dest);
            
        } catch (IOException e) {
            System.err.println("Error copiando archivo: " + e.getMessage());
        }
    }
}