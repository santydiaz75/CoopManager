import java.sql.*;

/**
 * Test final para probar la consulta corregida tal como aparece en el .jrxml original
 */
public class TestFinalQuery {
    
    public static void main(String[] args) {
        System.out.println("=== TEST CONSULTA FINAL ===");
        
        try {
            // Configuracion de conexion
            String url = "jdbc:sqlserver://SQL6032.site4now.net;databaseName=db_aa764d_coopmanagerdb;trustServerCertificate=true";
            String login = "db_aa764d_coopmanagerdb_admin";
            String password = "salmadh2010";
            
            // Conectar
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion establecida correctamente");
            
            // Probar primero sin INNER JOIN
            System.out.println("\n1. Test simple sin JOIN:");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT TOP 5 Empresa, Ejercicio, IdCosechero, Apellidos, Nombre, Nif, Telefono1, Email FROM cosecheros WHERE Ejercicio = ? AND Empresa = ? ORDER BY Apellidos");
            pstmt1.setInt(1, 1995);
            pstmt1.setInt(2, 1);
            ResultSet rs1 = pstmt1.executeQuery();
            int count1 = 0;
            while (rs1.next()) {
                System.out.println("ID: " + rs1.getInt("IdCosechero") + ", Apellidos: " + rs1.getString("Apellidos"));
                count1++;
            }
            System.out.println("Resultados encontrados (sin JOIN): " + count1);
            rs1.close();
            pstmt1.close();
            
            // Ahora probar con INNER JOIN
            System.out.println("\n2. Test con INNER JOIN:");
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT c.Empresa, c.Ejercicio, c.IdCosechero, c.Apellidos, c.Nombre, c.Nif, c.Telefono1, c.Email, e.Lopd FROM cosecheros c INNER JOIN empresas e ON c.Empresa = e.IdEmpresa WHERE c.Ejercicio = ? AND c.Empresa = ? ORDER BY c.Apellidos");
            pstmt2.setInt(1, 1995);
            pstmt2.setInt(2, 1);
            ResultSet rs2 = pstmt2.executeQuery();
            int count2 = 0;
            while (rs2.next() && count2 < 5) {
                System.out.println("ID: " + rs2.getInt("IdCosechero") + ", Apellidos: " + rs2.getString("Apellidos"));
                count2++;
            }
            System.out.println("Resultados encontrados (con JOIN): " + count2);
            rs2.close();
            pstmt2.close();
            
            conn.close();
            System.out.println("\nSUCCESS: Ambas consultas funcionan!");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DEL TEST ===");
    }
}