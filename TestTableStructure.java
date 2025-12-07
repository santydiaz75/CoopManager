import java.sql.*;

/**
 * Test para verificar la estructura de las tablas
 */
public class TestTableStructure {
    
    public static void main(String[] args) {
        System.out.println("=== VERIFICACION ESTRUCTURA TABLAS ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Verificar estructura tabla empresas
            System.out.println("\n--- ESTRUCTURA TABLA EMPRESAS ---");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT TOP 1 * FROM empresas");
            ResultSet rs1 = pstmt1.executeQuery();
            ResultSetMetaData metaData1 = rs1.getMetaData();
            int columnCount1 = metaData1.getColumnCount();
            for (int i = 1; i <= columnCount1; i++) {
                System.out.println("Columna " + i + ": " + metaData1.getColumnName(i) + " (" + metaData1.getColumnTypeName(i) + ")");
            }
            rs1.close();
            pstmt1.close();
            
            // Verificar estructura tabla cosecheros
            System.out.println("\n--- ESTRUCTURA TABLA COSECHEROS ---");
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT TOP 1 * FROM cosecheros");
            ResultSet rs2 = pstmt2.executeQuery();
            ResultSetMetaData metaData2 = rs2.getMetaData();
            int columnCount2 = metaData2.getColumnCount();
            for (int i = 1; i <= columnCount2; i++) {
                System.out.println("Columna " + i + ": " + metaData2.getColumnName(i) + " (" + metaData2.getColumnTypeName(i) + ")");
            }
            rs2.close();
            pstmt2.close();
            
            // Verificar algunos datos de ejemplo
            System.out.println("\n--- DATOS EJEMPLO EMPRESAS ---");
            PreparedStatement pstmt3 = conn.prepareStatement("SELECT TOP 3 * FROM empresas");
            ResultSet rs3 = pstmt3.executeQuery();
            while (rs3.next()) {
                System.out.println("IdEmpresa: " + rs3.getInt("IdEmpresa"));
            }
            rs3.close();
            pstmt3.close();
            
            // Verificar algunos datos de ejemplo cosecheros
            System.out.println("\n--- DATOS EJEMPLO COSECHEROS ---");
            PreparedStatement pstmt4 = conn.prepareStatement("SELECT TOP 3 Empresa, Ejercicio FROM cosecheros");
            ResultSet rs4 = pstmt4.executeQuery();
            while (rs4.next()) {
                System.out.println("Empresa: " + rs4.getInt("Empresa") + ", Ejercicio: " + rs4.getInt("Ejercicio"));
            }
            rs4.close();
            pstmt4.close();
            
            conn.close();
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DE LA VERIFICACION ===");
    }
}