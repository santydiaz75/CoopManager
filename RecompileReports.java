import net.sf.jasperreports.engine.*;
import java.io.File;

public class RecompileReports {
    public static void main(String[] args) {
        String sourceDir = "src/reportsPackage/";
        String targetDir = "target/classes/reportsPackage/";
        
        // Ensure target directory exists
        new File(targetDir).mkdirs();
        
        String[] reports = {
            "ListadoCosecheros",
            "ListadoCosecherosKilos",
            "FacturaLiquidacion",
            "FacturaLiquidacionRetorno"
        };
        
        for (String reportName : reports) {
            try {
                System.out.println("Recompiling " + reportName + ".jrxml...");
                String sourceFile = sourceDir + reportName + ".jrxml";
                String destinationFile = targetDir + reportName + ".jasper";
                
                if (new File(sourceFile).exists()) {
                    JasperCompileManager.compileReportToFile(sourceFile, destinationFile);
                    System.out.println("Successfully recompiled: " + destinationFile);
                } else {
                    System.out.println("Source file not found: " + sourceFile);
                }
                
            } catch (Exception e) {
                System.out.println("Error recompiling " + reportName + ": " + e.getMessage());
                // Continue with next report
            }
        }
        
        System.out.println("Recompilation process completed.");
    }
}