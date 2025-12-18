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

import java.util.Date;
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
public class FacturaLiquidacion 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private Object parent;
    
    public FacturaLiquidacion(Object parent)
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
        	Message.ShowErrorMessage(parent, "FacturaLiquidacion", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "FacturaLiquidacion", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "FacturaLiquidacion", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = FacturaLiquidacion.class.getResource("FacturaLiquidacion.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/FacturaLiquidacion.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/FacturaLiquidacion.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int mes, int semanaDesde, int semanaHasta, 
    		Date fechaDesde, Date fechaHasta, int numerofacturadesde, int numerofacturahasta)
    {
        
        try
        {       
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory +  
            			"\\reportsPackage\\FacturaLiquidacion.jasper";
            
            if (master == null)          
            	Message.ShowErrorMessage(parent, "FacturaLiquidacion", "No encuentro el archivo del informe maestro.");
            else {

	            JasperReport masterReport = null;
	            masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);              
	            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("Mes", mes);
	            parameters.put("SemanaDesde", semanaDesde);
	            parameters.put("SemanaHasta", semanaHasta);
	            parameters.put("FechaDesde", fechaDesde);
	            parameters.put("FechaHasta", fechaHasta);
	            parameters.put("NumeroFacturaDesde", numerofacturadesde);
	            parameters.put("NumeroFacturaHasta", numerofacturahasta);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            parameters.put("SUBREPORT_DIR", workDirectory +  
    			"\\reportsPackage\\");
	            
                System.out.println("INFO: FacturaLiquidacion - consultas SQL corregidas en archivos .jrxml");

	            System.out.println("INFO: FacturaLiquidacion - usando conexi�n directa para subreportes");

                System.out.println("DEBUG: Filling report with connection for subreports...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);
	
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - FacturaLiquidacion (SQL Corregido)");
	            jviewer.setVisible(true);
            }
        }

        catch (Exception j)
        {
        	j.printStackTrace();
        	Message.ShowErrorMessage(parent, "FacturaLiquidacion", "Mensaje de Error: " + j.getMessage());
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
        	Message.ShowErrorMessage(parent, "FacturaLiquidacion", ex);
        }
    }
}
