import java.sql.*;

/**
 * Test para verificar la estructura de la tabla categorias
 */
public class TestCategorias {
    
    public static void main(String[] args) {
        System.out.println("=== VERIFICACION ESTRUCTURA TABLA CATEGORIAS ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Verificar estructura tabla categorias
            System.out.println("\n--- ESTRUCTURA TABLA CATEGORIAS ---");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT TOP 1 * FROM categorias");
            ResultSet rs1 = pstmt1.executeQuery();
            ResultSetMetaData metaData1 = rs1.getMetaData();
            int columnCount1 = metaData1.getColumnCount();
            for (int i = 1; i <= columnCount1; i++) {
                System.out.println("Columna " + i + ": " + metaData1.getColumnName(i) + " (" + metaData1.getColumnTypeName(i) + ")");
            }
            rs1.close();
            pstmt1.close();
            
            // Verificar algunos datos de ejemplo de categorias
            System.out.println("\n--- DATOS EJEMPLO CATEGORIAS ---");
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT TOP 5 Empresa, Ejercicio, IdCategoria, NombreCategoria FROM categorias ORDER BY Empresa, Ejercicio, IdCategoria");
            ResultSet rs2 = pstmt2.executeQuery();
            while (rs2.next()) {
                System.out.println("Empresa: " + rs2.getInt("Empresa") + 
                                  ", Ejercicio: " + rs2.getInt("Ejercicio") + 
                                  ", IdCategoria: " + rs2.getInt("IdCategoria") + 
                                  ", Nombre: " + rs2.getString("NombreCategoria"));
            }
            rs2.close();
            pstmt2.close();
            
            conn.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DE LA VERIFICACION ===");
    }
}