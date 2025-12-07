import net.sf.jasperreports.engine.*;
import java.io.*;

public class CompileResumenLiquidacion {
    public static void main(String[] args) {
        try {
            System.out.println("Compilando ResumenLiquidacion...");
            
            // Rutas - compilar directamente en target/classes para que la aplicacion lo use
            String sourceFile = "target/classes/reportsPackage/ResumenLiquidacion.jrxml";
            String destinationFile = "target/classes/reportsPackage/ResumenLiquidacion.jasper";
            
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
            
            System.out.println("EXITO: ResumenLiquidacion compilado correctamente");
            
            // Verificar que se creo el archivo
            File dest = new File(destinationFile);
            if (dest.exists()) {
                System.out.println("Archivo compilado creado: " + dest.length() + " bytes");
            } else {
                System.err.println("ERROR: No se pudo crear el archivo compilado");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR al compilar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}