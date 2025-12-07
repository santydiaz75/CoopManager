import java.sql.*;

/**
 * Test para verificar que datos hay disponibles en la tabla cosecheros
 */
public class TestDataAvailable {
    
    public static void main(String[] args) {
        System.out.println("=== VERIFICACION DE DATOS DISPONIBLES ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Verificar que empresas existen
            System.out.println("\n--- EMPRESAS DISPONIBLES ---");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT IdEmpresa, NombreComercial FROM empresas ORDER BY IdEmpresa");
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                System.out.println("Empresa ID: " + rs1.getInt("IdEmpresa") + " - " + rs1.getString("NombreComercial"));
            }
            rs1.close();
            pstmt1.close();
            
            // Verificar que ejercicios existen
            System.out.println("\n--- EJERCICIOS DISPONIBLES ---");
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT DISTINCT Ejercicio FROM cosecheros ORDER BY Ejercicio DESC");
            ResultSet rs2 = pstmt2.executeQuery();
            while (rs2.next()) {
                System.out.println("Ejercicio: " + rs2.getInt("Ejercicio"));
            }
            rs2.close();
            pstmt2.close();
            
            // Verificar combinaciones empresa-ejercicio
            System.out.println("\n--- COMBINACIONES EMPRESA-EJERCICIO ---");
            PreparedStatement pstmt3 = conn.prepareStatement("SELECT Empresa, Ejercicio, COUNT(*) as Total FROM cosecheros GROUP BY Empresa, Ejercicio ORDER BY Ejercicio DESC, Empresa");
            ResultSet rs3 = pstmt3.executeQuery();
            while (rs3.next()) {
                System.out.println("Empresa: " + rs3.getInt("Empresa") + 
                                  ", Ejercicio: " + rs3.getInt("Ejercicio") + 
                                  ", Cosecheros: " + rs3.getInt("Total"));
            }
            rs3.close();
            pstmt3.close();
            
            conn.close();
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DE LA VERIFICACION ===");
    }
}