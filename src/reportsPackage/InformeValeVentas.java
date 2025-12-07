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
public class InformeValeVentas 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public InformeValeVentas(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "InformeValeVentas", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "InformeValeVentas", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "InformeValeVentas", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = InformeValeVentas.class.getResource("InformeValeVentas.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/InformeValeVentas.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/InformeValeVentas.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int Receptor, int SemanaDesde, int SemanaHasta, int TipoMercado)
    {
        
        try
        {            
            String master = getDirectory().getPath() + 
							"\\reportsPackage\\InformeValeVentas.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "InformeValeVentas", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("Receptor", Receptor);
	            parameters.put("SemanaDesde", SemanaDesde);
	            parameters.put("SemanaHasta", SemanaHasta);
	            if (TipoMercado == 0)
	            	parameters.put("TipoMercado", -1);
	            if (TipoMercado == 1)
	            	parameters.put("TipoMercado", 1);
	            if (TipoMercado == 2)
	            	parameters.put("TipoMercado", 0);
	
	            // === ADVERTENCIA: CONSULTA SQL SIN CORREGIR ===

	
	            // Este reporte puede tener referencias hardcodeadas a la base de datos

	
	            // En archivo InformeValeVentas.jrxml

	
	            // TODO: Implementar solucion especifica si hay errores SQL

	
	            System.out.println("WARNING: InformeValeVentas - verificar referencias DB en .jrxml");

	
	            //Informe diseñado y compilado con iReport
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);
	
	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - InformeValeVentas (CHECK SQL)");
	            jviewer.setVisible(true);
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "InformeValeVentas", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "InformeValeVentas", ex);
                }
    }
}
