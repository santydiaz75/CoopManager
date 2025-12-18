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
public class ControlProduccionZonas 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ControlProduccionZonas(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "ControlProduccionZonas", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ControlProduccionZonas", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ControlProduccionZonas", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ControlProduccionZonas.class.getResource("ControlProduccionZonas.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ControlProduccionZonas.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/FacturaServicios.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int idZonaDesde, int idZonaHasta)
    {
        
        try
        {   
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\ControlProduccionZonas.jasper";
            
            if (master == null)          
            	Message.ShowErrorMessage(parent, "ControlProduccionZonas", "No encuentro el archivo del informe maestro.");
            else {

	            JasperReport masterReport = null;
	            masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);              
	            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("ZonaDesde", idZonaDesde);
	            parameters.put("ZonaHasta", idZonaHasta);
	            parameters.put("LOGO1_DIR", workDirectory +  
    			"\\reportsPackage\\AnagramaAsprocan.jpg");
	            parameters.put("LOGO2_DIR", workDirectory +  
    			"\\reportsPackage\\AnagramaAgriten.jpg");
	
	            
	            String sqlQuery = "SELECT ec.IdZona, z.NombreZona, DATEPART(MONTH, ec.Fecha) as mes, m.NombreMes, " +
	                "COALESCE(dbo.EntradasKilosMesZona(?, ?, DATEPART(MONTH, ec.Fecha), ec.IdZona), 0) as Kilos, " +
	                "COALESCE(dbo.EntradasNumPinasMesZona(?, ?, DATEPART(MONTH, ec.Fecha), ec.IdZona), 0) as NumPinas, " +
	                "CASE WHEN COALESCE(dbo.EntradasNumPinasMesZona(?, ?, DATEPART(MONTH, ec.Fecha), ec.IdZona), 0) > 0 " +
	                "THEN COALESCE(dbo.EntradasKilosMesZona(?, ?, DATEPART(MONTH, ec.Fecha), ec.IdZona), 0) / " +
	                "     COALESCE(dbo.EntradasNumPinasMesZona(?, ?, DATEPART(MONTH, ec.Fecha), ec.IdZona), 0) " +
	                "ELSE 0 END as Promedio " +
	                "FROM entradascabecera ec " +
	                "INNER JOIN meses m ON DATEPART(MONTH, ec.fecha) = m.mes " +
	                "INNER JOIN zonas z ON ec.ejercicio = z.ejercicio AND ec.empresa = z.empresa AND ec.IdZona = z.IdZona " +
	                "WHERE ec.ejercicio = ? AND ec.empresa = ? AND ec.IdZona >= ? AND ec.IdZona <= ? " +
	                "GROUP BY ec.IdZona, z.NombreZona, DATEPART(MONTH, ec.Fecha), m.NombreMes " +
	                "ORDER BY ec.IdZona, DATEPART(MONTH, ec.fecha)";
	            
	            System.out.println("DEBUG: Executing corrected SQL Server query for ControlProduccionZonas...");
	            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
	            pstmt.setInt(1, empresa);
	            pstmt.setInt(2, ejercicio);
	            pstmt.setInt(3, empresa);
	            pstmt.setInt(4, ejercicio);
	            pstmt.setInt(5, empresa);
	            pstmt.setInt(6, ejercicio);
	            pstmt.setInt(7, empresa);
	            pstmt.setInt(8, ejercicio);
	            pstmt.setInt(9, empresa);
	            pstmt.setInt(10, ejercicio);
	            pstmt.setInt(11, ejercicio);
	            pstmt.setInt(12, empresa);
	            pstmt.setInt(13, idZonaDesde);
	            pstmt.setInt(14, idZonaHasta);
	            ResultSet rs = pstmt.executeQuery();
	            
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);

	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ControlProduccionZonas (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ControlProduccionZonas", "Mensaje de Error: " + j.getMessage());
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
        	Message.ShowErrorMessage(parent, "FacturaServicios", ex);
        }
    }
}
