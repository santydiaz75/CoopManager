import net.sf.jasperreports.engine.*;
import java.io.File;

public class CompileJRXML {
    public static void main(String[] args) {
        try {
            // Compilar FacturaLiquidacion.jrxml
            System.out.println("Compiling FacturaLiquidacion.jrxml...");
            JasperCompileManager.compileReportToFile(
                "src/reportsPackage/FacturaLiquidacion.jrxml",
                "src/reportsPackage/FacturaLiquidacion.jasper"
            );
            System.out.println("FacturaLiquidacion.jrxml compiled successfully!");
            
            // Compilar ResumenLiquidacion.jrxml
            System.out.println("Compiling ResumenLiquidacion.jrxml...");
            JasperCompileManager.compileReportToFile(
                "src/reportsPackage/ResumenLiquidacion.jrxml",
                "src/reportsPackage/ResumenLiquidacion.jasper"
            );
            System.out.println("ResumenLiquidacion.jrxml compiled successfully!");
            
        } catch (Exception e) {
            System.err.println("Error compiling report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}