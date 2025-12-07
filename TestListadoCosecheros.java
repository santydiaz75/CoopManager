import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jasypt.util.text.BasicTextEncryptor;
import sessionPackage.HibernateSessionFactory;

public class TestListadoCosecheros {
    
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("Iniciando test de conexion y consulta SQL...");
            
            // Configurar conexion igual que en ListadoCosecheros.java
            final String login = "db_aa764d_coopmanagerdb_admin";
            String url = HibernateSessionFactory.getConnectionURL();
            
            System.out.println("URL de conexion: " + url);
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            BasicTextEncryptor bte = new BasicTextEncryptor();
            bte.setPassword("santi");
            String paswworddecrypt = "salmadh2010";
            
            conn = DriverManager.getConnection(url, login, paswworddecrypt);
            System.out.println("OK - Conexion establecida correctamente");
            
            // Probar la consulta SQL corregida (sin referencias a la BD)
            String sql = "SELECT c.Empresa, c.Ejercicio, c.IdCosechero, c.Apellidos, c.Nombre, c.Nif, c.Telefono1, c.Email, e.Lopd " +
                        "FROM cosecheros c inner join empresas e on c.Empresa = e.IdEmpresa " +
                        "where c.Ejercicio = ? and c.Empresa = ? " +
                        "order by c.Apellidos";
            
            System.out.println("Probando consulta SQL: " + sql);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 2024); // Ejercicio de prueba
            pstmt.setInt(2, 1);    // Empresa de prueba
            
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            System.out.println("Resultados encontrados:");
            while (rs.next() && count < 5) { // Limitar a 5 resultados para prueba
                System.out.println("ID: " + rs.getInt("IdCosechero") + 
                                 ", Apellidos: " + rs.getString("Apellidos") + 
                                 ", Nombre: " + rs.getString("Nombre"));
                count++;
            }
            
            if (count == 0) {
                System.out.println("No se encontraron registros para Ejercicio=2024, Empresa=1");
                System.out.println("Esto es normal si no hay datos para esos parametros");
            } else {
                System.out.println("Total de registros mostrados: " + count);
            }
            
            rs.close();
            pstmt.close();
            
            System.out.println("OK - Consulta SQL ejecutada correctamente");
            
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Driver SQL Server no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR SQL: " + e.getMessage());
            System.err.println("Codigo de error: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
        } catch (Exception e) {
            System.err.println("ERROR general: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("OK - Conexion cerrada");
                } catch (SQLException e) {
                    System.err.println("Error cerrando conexion: " + e.getMessage());
                }
            }
        }
    }
}