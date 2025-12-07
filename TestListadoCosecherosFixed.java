import reportsPackage.ListadoCosecherosFixed;
import javax.swing.JFrame;

/**
 * Test para verificar que ListadoCosecherosFixed funciona correctamente
 * con la consulta SQL corregida.
 */
public class TestListadoCosecherosFixed {
    
    public static void main(String[] args) {
        System.out.println("=== TEST LISTADO COSECHEROS FIXED ===");
        
        try {
            // Crear un JFrame simple para el test
            JFrame testFrame = new JFrame("Test Frame");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(400, 300);
            testFrame.setVisible(false); // No mostrar la ventana de test
            
            // Crear instancia de la clase corregida
            System.out.println("Creando instancia de ListadoCosecherosFixed...");
            ListadoCosecherosFixed listado = new ListadoCosecherosFixed(testFrame);
            
            // Ejecutar reporte con parámetros de prueba
            System.out.println("Ejecutando reporte con Empresa=2, Ejercicio=2024...");
            listado.runReporte(2, 2024);
            
            System.out.println("SUCCESS: El reporte se ejecutó sin errores!");
            System.out.println("Deberías ver la ventana del reporte JasperViewer.");
            
            // Cerrar conexión
            listado.cerrar();
            System.out.println("Conexión cerrada correctamente.");
            
        } catch (Exception e) {
            System.err.println("ERROR durante la ejecución del test:");
            e.printStackTrace();
        }
        
        System.out.println("=== FIN DEL TEST ===");
    }
}