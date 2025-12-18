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
        	final String login = "db_aa764d_coopmanagerdb_admin";
            String url = HibernateSessionFactory.getConnectionURL();
            
        	this.parent = parent;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
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
            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("SemanaDesde", SemanaDesde);
	            parameters.put("SemanaHasta", SemanaHasta);
	            parameters.put("LOGO_DIR", workDirectory +  
    			"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	
	            
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
	            pstmt.setInt(1, empresa);
	            pstmt.setInt(2, ejercicio);
	            pstmt.setInt(3, empresa);
	            pstmt.setInt(4, ejercicio);
	            pstmt.setInt(5, empresa);
	            pstmt.setInt(6, ejercicio);
	            pstmt.setInt(7, empresa);
	            pstmt.setInt(8, ejercicio);
	            pstmt.setInt(9, SemanaDesde);
	            pstmt.setInt(10, SemanaHasta);
	            pstmt.setInt(11, ejercicio);
	            pstmt.setInt(12, empresa);
	            pstmt.setInt(13, empresa);
	            pstmt.setInt(14, ejercicio);
	            pstmt.setInt(15, empresa);
	            pstmt.setInt(16, ejercicio);
	            
	            ResultSet rs = pstmt.executeQuery();
	            
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);
	
	            System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);
	            
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ControlESAlmacen (Version Corregida)");
	            jviewer.setVisible(true);
	            
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
