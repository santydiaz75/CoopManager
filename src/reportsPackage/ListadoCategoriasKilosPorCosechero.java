/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reportsPackage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class ListadoCategoriasKilosPorCosechero 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ListadoCategoriasKilosPorCosechero(java.awt.Frame parent)
    {        
        try 
        {
        	final String login = "db_aa764d_coopmanagerdb_admin"; //usuario de acceso a SQL Server
            String url = HibernateSessionFactory.getConnectionURL();
            
        	this.parent = parent;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //se carga el driver
            String password = "salmadh2010";
            conn = DriverManager.getConnection(url,login,password);
        } 
        catch (ClassNotFoundException ex) 
        {
        	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ListadoCategoriasKilosPorCosechero.class.getResource("ListadoCategoriasKilosPorCosechero.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ListadoCategoriasKilosPorCosechero.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/ListadoCategoriasKilosPorCosechero.jasper"
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
    
    public void runReporte(int empresa, Date fechadesde, Date fechahasta)
    {
        
        try
        {            
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\ListadoCategoriasKilosPorCosechero.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("FechaDesde", fechadesde);
	            parameters.put("FechaHasta", fechahasta);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            
	            // === ADVERTENCIA: CONSULTA SQL SIN CORREGIR ===

	            
	            // Este reporte puede tener referencias hardcodeadas a la base de datos

	            
	            // En archivo ListadoCategoriasKilosPorCosechero.jrxml

	            
	            // TODO: Implementar solucion especifica si hay errores SQL

	            
	            System.out.println("WARNING: ListadoCategoriasKilosPorCosechero - verificar referencias DB en .jrxml");

	            
	            //Informe diseñado y compilado con iReport
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);
	
	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ListadoCategoriasKilosPorCosechero (CHECK SQL)");
	            jviewer.setVisible(true);
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "ListadoCategoriasKilosPorCosechero", ex);
                }
    }
}
