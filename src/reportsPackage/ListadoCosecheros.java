/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reportsPackage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.jasypt.util.text.BasicTextEncryptor;

import sessionPackage.HibernateSessionFactory;

import entitiesPackage.Message;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.*;


/**
 *
 * @author WinDoctor
 */
public class ListadoCosecheros 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ListadoCosecheros(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "ListadoCosecheros", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ListadoCosecheros", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ListadoCosecheros", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ListadoCosecheros.class.getResource("ListadoCosecheros.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ListadoCosecheros.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/ListadoCosecheros.jasper"
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
    
    public void runReporte(int empresa, int ejercicio)
    {
        
        try
        {            
            JasperReport masterReport = null;
            String workDirectory = getDirectory().getPath();
            
            // Try loading from file system first (more reliable for compatibility)
            String master = workDirectory + "\\reportsPackage\\ListadoCosecheros.jasper";
            System.out.println("DEBUG: Trying to load from file: " + master);
            
            if (new File(master).exists()) {
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);
                System.out.println("DEBUG: Successfully loaded from file system");
            } else {
                // Fallback to classpath
                System.out.println("DEBUG: File not found, trying classpath...");
                URL reportUrl = ListadoCosecheros.class.getResource("/reportsPackage/ListadoCosecheros.jasper");
                if (reportUrl != null) {
                    // Try converting URL to file path for better compatibility
                    if (reportUrl.getProtocol().equals("file")) {
                        String filePath = reportUrl.getPath();
                        masterReport = (JasperReport) JRLoader.loadObjectFromFile(filePath);
                        workDirectory = new File(filePath).getParent();
                    } else {
                        masterReport = (JasperReport) JRLoader.loadObject(reportUrl);
                        workDirectory = "";
                    }
                    System.out.println("DEBUG: Successfully loaded from classpath");
                }
            }
            
            if (masterReport == null) 
            	Message.ShowErrorMessage(parent, "ListadoCosecheros", "No encuentro el archivo del informe maestro.");
            else {
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            
	            // Use workDirectory for logo path
	            String logoPath = workDirectory + "\\reportsPackage\\Anagrama" + empresa + ".jpg";
	            parameters.put("LOGO_DIR", logoPath);
	            
	            //Informe diseñado y compilado con iReport
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);
	
	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop");
	            jviewer.setVisible(true);
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ListadoCosecheros", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "ListadoCosecheros", ex);
                }
    }
}
