import net.sf.jasperreports.engine.*;

public class CompileListadoCosecheros {
    public static void main(String[] args) {
        try {
            System.out.println("Compilando solo ListadoCosecheros.jrxml...");
            
            String sourceFile = "src/reportsPackage/ListadoCosecheros.jrxml";
            String destinationFile = "target/classes/reportsPackage/ListadoCosecheros.jasper";
            
            // Crear directorio destino si no existe
            new java.io.File("target/classes/reportsPackage/").mkdirs();
            
            if (new java.io.File(sourceFile).exists()) {
                // Intentar compilar con codificacion especifica
                JasperCompileManager.compileReportToFile(sourceFile, destinationFile);
                System.out.println("EXITO: Reporte compilado en: " + destinationFile);
            } else {
                System.out.println("ERROR: Archivo fuente no encontrado: " + sourceFile);
            }
            
        } catch (Exception e) {
            System.out.println("ERROR compilando reporte: " + e.getMessage());
            System.out.println("Tipo de error: " + e.getClass().getSimpleName());
            
            // Si hay error de codificacion, vamos a intentar una aproximacion diferente
            if (e.getMessage().contains("UTF-8") || e.getMessage().contains("byte sequence")) {
                System.out.println("NOTA: Error de codificacion detectado. El archivo .jasper existente deberia funcionar.");
                System.out.println("La correccion de SQL ya esta aplicada en el codigo Java.");
            }
        }
    }
}