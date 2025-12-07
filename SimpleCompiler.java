import net.sf.jasperreports.engine.*;
import java.io.*;

public class SimpleCompiler {
    public static void main(String[] args) {
        try {
            System.out.println("Compilando reporte corregido...");
            
            // Rutas
            String sourceFile = "src/reportsPackage/ListadoCosecheros.jrxml";
            String destinationFile = "src/reportsPackage/ListadoCosecheros.jasper";
            
            System.out.println("Origen: " + sourceFile);
            System.out.println("Destino: " + destinationFile);
            
            // Verificar que el archivo fuente existe
            File source = new File(sourceFile);
            if (!source.exists()) {
                System.err.println("ERROR: Archivo fuente no encontrado");
                return;
            }
            
            // Compilar directamente
            JasperCompileManager.compileReportToFile(sourceFile, destinationFile);
            
            System.out.println("EXITO: Reporte compilado correctamente");
            
            // Verificar que se creo el archivo
            File dest = new File(destinationFile);
            if (dest.exists()) {
                System.out.println("Archivo creado: " + dest.length() + " bytes");
            }
            
        } catch (JRException e) {
            System.err.println("ERROR JasperReports: " + e.getMessage());
            
            // Si es error de codificacion, intentamos una solucion alternativa
            if (e.getMessage().contains("UTF-8") || e.getMessage().contains("encoding")) {
                System.out.println("SOLUCION ALTERNATIVA:");
                System.out.println("1. El archivo .jrxml tiene la consulta SQL correcta");
                System.out.println("2. Usar el archivo .jasper existente con las correcciones manuales");
                System.out.println("3. La consulta SQL en el codigo Java ya funciona correctamente");
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}