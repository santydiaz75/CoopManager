import java.sql.*;

/**
 * Test simple para verificar que la consulta SQL corregida funciona
 */
public class TestQueryFixed {
    
    public static void main(String[] args) {
        System.out.println("=== TEST CONSULTA SQL CORREGIDA ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Consulta SQL corregida (sin referencias hardcodeadas a la base de datos)
            String sqlQuery = "SELECT c.Empresa, c.Ejercicio, c.IdCosechero, c.Apellidos, c.Nombre, " +
                             "c.Nif, c.Telefono1, c.Email, e.Lopd " +
                             "FROM cosecheros c " +
                             "INNER JOIN empresas e ON c.Empresa = e.IdEmpresa " +
                             "WHERE c.Ejercicio = ? AND c.Empresa = ? " +
                             "ORDER BY c.Apellidos";
            
            System.out.println("Ejecutando consulta SQL corregida...");
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, 1995); // Ejercicio  
            pstmt.setInt(2, 1);    // Empresa
            
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            System.out.println("Resultados encontrados:");
            while (rs.next() && count < 10) { // Mostrar solo los primeros 10
                System.out.println("ID: " + rs.getInt("IdCosechero") + 
                                  ", Apellidos: " + rs.getString("Apellidos") + 
                                  ", Nombre: " + rs.getString("Nombre"));
                count++;
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
            System.out.println("SUCCESS: La consulta SQL corregida funciona perfectamente!");
            System.out.println("Total de registros mostrados: " + count);
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== FIN DEL TEST ===");
    }
}