import java.awt.Frame;
import java.io.File;

import reportsPackage.ListadoCosecheros;

public class TestReporteCompleto {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== TEST COMPLETO DEL REPORTE ListadoCosecheros ===");
            
            // Crear un Frame dummy para el test
            Frame parentFrame = new Frame();
            
            // Crear instancia del reporte
            ListadoCosecheros reporte = new ListadoCosecheros(parentFrame);
            
            // Verificar que el archivo .jasper existe
            String jasperPath = "target/classes/reportsPackage/ListadoCosecheros.jasper";
            File jasperFile = new File(jasperPath);
            
            if (jasperFile.exists()) {
                System.out.println("OK - Archivo .jasper encontrado: " + jasperPath);
                System.out.println("Size del archivo: " + jasperFile.length() + " bytes");
            } else {
                System.out.println("AVISO - Archivo .jasper no encontrado en: " + jasperPath);
                // Buscar en otras ubicaciones posibles
                String[] otherPaths = {
                    "src/reportsPackage/ListadoCosecheros.jasper",
                    "bin/reportsPackage/ListadoCosecheros.jasper"
                };
                
                for (String path : otherPaths) {
                    File f = new File(path);
                    if (f.exists()) {
                        System.out.println("ENCONTRADO - Archivo .jasper en: " + path);
                        break;
                    }
                }
            }
            
            System.out.println("\n--- Intentando ejecutar el reporte ---");
            System.out.println("Empresa: 1, Ejercicio: 2024");
            
            // Intentar ejecutar el reporte con parametros de test
            reporte.runReporte(1, 2024);
            
            System.out.println("\nOK - El reporte se ejecuto sin errores fatales");
            
            // Cerrar la conexion
            reporte.cerrar();
            
            System.out.println("OK - Conexion cerrada correctamente");
            System.out.println("\n=== TEST COMPLETADO EXITOSAMENTE ===");
            
        } catch (Exception e) {
            System.err.println("ERROR en el test: " + e.getMessage());
            System.err.println("Tipo: " + e.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}