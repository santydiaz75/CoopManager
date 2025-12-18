package reportsPackage;

import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileRentabilidad {
    public static void main(String[] args) {
        try {
            String sourceFile = "src/reportsPackage/Rentabilidad.jrxml";
            String destFile = "src/reportsPackage/Rentabilidad.jasper";
            
            System.out.println("Compilando " + sourceFile + " -> " + destFile);
            JasperCompileManager.compileReportToFile(sourceFile, destFile);
            System.out.println("Reporte compilado exitosamente!");
            
            String targetSourceFile = "target/classes/reportsPackage/Rentabilidad.jrxml";
            String targetDestFile = "target/classes/reportsPackage/Rentabilidad.jasper";
            
            System.out.println("Compilando " + targetSourceFile + " -> " + targetDestFile);
            JasperCompileManager.compileReportToFile(targetSourceFile, targetDestFile);
            System.out.println("Reporte en target compilado exitosamente!");
            
        } catch (Exception e) {
            System.err.println("Error compilando el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
