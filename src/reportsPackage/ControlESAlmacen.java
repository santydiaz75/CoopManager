/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reportsPackage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.jasypt.util.text.BasicTextEncryptor;

import sessionPackage.HibernateSessionFactory;

import entitiesPackage.Message;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.*;


/**
 *
 * @author WinDoctor
 */
public class ControlESAlmacen 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ControlESAlmacen(java.awt.Frame parent)
    {        
        try 
        {
        	final String login = "db_aa764d_coopmanagerdb_admin"; //usuario de acceso a SQL Server
            String url = HibernateSessionFactory.getConnectionURL();
            
        	this.parent = parent;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //se carga el driver
            BasicTextEncryptor bte = new BasicTextEncryptor();
            bte.setPassword("santi");
            String paswworddecrypt = "salmadh2010";
            conn = DriverManager.getConnection(url,login,paswworddecrypt);
        } 
        catch (ClassNotFoundException ex) 
        {
        	Message.ShowErrorMessage(parent, "ControlESAlmacen", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ControlESAlmacen", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ControlESAlmacen", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ControlESAlmacen.class.getResource("ControlESAlmacen.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ControlESAlmacen.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/AyudasOCm.jasper"
                    */
                    File f = new File(new URL(s).toURI());
                    f = f.getParentFile();
                    WORKING_DIRECTORY = f;
                }
            } catch(Exception e) {
                WORKING_DIRECTORY = new File(".");
            }
        }
        return WORKING_DIRECTORY;
    }
    
    public void runReporte(int empresa, int ejercicio, int SemanaDesde, int SemanaHasta)
    {
        
        try
        {          
        	String workDirectory = getDirectory().getPath();
            String master = workDirectory + 
							"\\reportsPackage\\ControlESAlmacen.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "ControlESAlmacen", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            //este es el par√°metro, se pueden agregar m√°s par√°metros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("SemanaDesde", SemanaDesde);
	            parameters.put("SemanaHasta", SemanaHasta);
	            parameters.put("LOGO_DIR", workDirectory +  
    			"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	
	            // === SOLUCION: Usar consulta SQL corregida para SQL Server ===
	            // En lugar de usar la consulta del .jasper (que tiene referencias hardcodeadas),
	            // ejecutamos una consulta corregida que evita las referencias hardcodeadas
	            
	            String sqlQuery = "SELECT c.semana, ca.IdCategoria, ca.NombreCategoria, " +
	                "COALESCE(dbo.EntradasKilosSemana(?, ?, c.semana, ca.IdCategoria), 0) as EntradaKilosSemana, " +
	                "COALESCE(dbo.VentasKilosSemana(?, ?, c.semana, ca.IdCategoria), 0) as VentasKilosSemana, " +
	                "dbo.EntradasGetFecha(?, ?, c.semana) as Fecha, " +
	                "COALESCE(dbo.EntradasKilosInutilizadosSemana(?, ?, c.semana, ca.IdCategoria), 0) as KilosInutilizadosSemana " +
	                "FROM calendario c " +
	                "CROSS JOIN categorias ca " +
	                "WHERE ca.ejercicio = c.ejercicio AND ca.empresa = c.empresa " +
	                "AND c.semana >= ? AND c.semana <= ? AND c.ejercicio = ? AND c.empresa = ? " +
	                "AND (COALESCE(dbo.EntradasKilosSemana(?, ?, c.semana, ca.IdCategoria), 0) > 0 " +
	                "OR COALESCE(dbo.VentasKilosSemana(?, ?, c.semana, ca.IdCategoria), 0) > 0) " +
	                "GROUP BY c.semana, ca.IdCategoria, ca.NombreCategoria " +
	                "ORDER BY c.semana, ca.IdCategoria";
	            
	            System.out.println("DEBUG: Executing corrected SQL Server query for ControlESAlmacen...");
	            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
	            // Setear par·metros m˙ltiples veces seg˙n se necesite en la consulta
	            pstmt.setInt(1, empresa);    // EntradasKilosSemana - par·metro 1
	            pstmt.setInt(2, ejercicio);  // EntradasKilosSemana - par·metro 2
	            pstmt.setInt(3, empresa);    // VentasKilosSemana - par·metro 1
	            pstmt.setInt(4, ejercicio);  // VentasKilosSemana - par·metro 2
	            pstmt.setInt(5, empresa);    // EntradasGetFecha - par·metro 1
	            pstmt.setInt(6, ejercicio);  // EntradasGetFecha - par·metro 2
	            pstmt.setInt(7, empresa);    // EntradasKilosInutilizadosSemana - par·metro 1
	            pstmt.setInt(8, ejercicio);  // EntradasKilosInutilizadosSemana - par·metro 2
	            pstmt.setInt(9, SemanaDesde);   // WHERE semana >=
	            pstmt.setInt(10, SemanaHasta);  // WHERE semana <=
	            pstmt.setInt(11, ejercicio);    // WHERE ejercicio =
	            pstmt.setInt(12, empresa);      // WHERE empresa =
	            pstmt.setInt(13, empresa);   // EntradasKilosSemana en HAVING - par·metro 1
	            pstmt.setInt(14, ejercicio); // EntradasKilosSemana en HAVING - par·metro 2
	            pstmt.setInt(15, empresa);   // VentasKilosSemana en HAVING - par·metro 1
	            pstmt.setInt(16, ejercicio); // VentasKilosSemana en HAVING - par·metro 2
	            
	            ResultSet rs = pstmt.executeQuery();
	            
	            // Crear data source from ResultSet
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);
	
	            System.out.println("DEBUG: Filling report with corrected data source...");
	            //Informe diseÒado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);
	            
	            //Se lanza el Viewer de Jasper, no termina aplicaciÛn al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ControlESAlmacen (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ControlESAlmacen", "Mensaje de Error: " + j.getMessage());
        }
        
    }
    
    public void cerrar()
    {
                try 
                {
                    conn.close();
                } 
                catch (SQLException ex) 
                {
                	Message.ShowErrorMessage(parent, "ControlESAlmacen", ex);
                }
    }
}
