import java.sql.*;

/**
 * Test para verificar la estructura de la tabla liquidaciones
 */
public class TestLiquidaciones {
    
    public static void main(String[] args) {
        System.out.println("=== VERIFICACION ESTRUCTURA TABLA LIQUIDACIONES ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Verificar estructura tabla liquidaciones
            System.out.println("\n--- ESTRUCTURA TABLA LIQUIDACIONES ---");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT TOP 1 * FROM liquidaciones");
            ResultSet rs1 = pstmt1.executeQuery();
            ResultSetMetaData metaData1 = rs1.getMetaData();
            int columnCount1 = metaData1.getColumnCount();
            for (int i = 1; i <= columnCount1; i++) {
                System.out.println("Columna " + i + ": " + metaData1.getColumnName(i) + " (" + metaData1.getColumnTypeName(i) + ")");
            }
            rs1.close();
            pstmt1.close();
            
            // Verificar algunos datos de ejemplo de liquidaciones
            System.out.println("\n--- DATOS EJEMPLO LIQUIDACIONES ---");
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT TOP 5 * FROM liquidaciones ORDER BY empresa, ejercicio");
            ResultSet rs2 = pstmt2.executeQuery();
            ResultSetMetaData metaData2 = rs2.getMetaData();
            while (rs2.next()) {
                System.out.print("Row: ");
                for (int i = 1; i <= metaData2.getColumnCount(); i++) {
                    System.out.print(metaData2.getColumnName(i) + "=" + rs2.getObject(i) + ", ");
                }
                System.out.println();
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