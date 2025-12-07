import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileFacturaLiquidacion {
    public static void main(String[] args) {
        try {
            System.out.println("=== COMPILANDO ARCHIVOS FACTURA LIQUIDACION ===");
            
            String[] files = {
                "src/reportsPackage/FacturaLiquidacion.jrxml",
                "src/reportsPackage/FacturaLiquidacionPrecios.jrxml", 
                "src/reportsPackage/FacturaLiquidacionAcumulado.jrxml",
                "src/reportsPackage/FacturaLiquidacionBonificacion.jrxml"
            };
            
            for (String file : files) {
                System.out.println("Compilando: " + file);
                String jasperFile = file.replace(".jrxml", ".jasper");
                JasperCompileManager.compileReportToFile(file, jasperFile);
                System.out.println("Compilado: " + jasperFile);
                
                // Tambien copiar a target
                String targetFile = jasperFile.replace("src/", "target/classes/");
                java.nio.file.Files.copy(
                    java.nio.file.Paths.get(jasperFile),
                    java.nio.file.Paths.get(targetFile),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                System.out.println("Copiado a: " + targetFile);
            }
            
            System.out.println("=== COMPILACION COMPLETADA ===");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}