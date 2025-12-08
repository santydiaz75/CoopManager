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
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.*;


/**
 *
 * @author WinDoctor
 */
public class Rentabilidad 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public Rentabilidad(java.awt.Frame parent)
    {        
        try 
        {
            String url = HibernateSessionFactory.getConnectionURL();
            String login = HibernateSessionFactory.getConfiguration().getProperty("hibernate.connection.username");
            String password = HibernateSessionFactory.getConfiguration().getProperty("hibernate.connection.password");
            
        	this.parent = parent;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //se carga el driver
            conn = DriverManager.getConnection(url, login, password);
        } 
        catch (ClassNotFoundException ex) 
        {
        	Message.ShowErrorMessage(parent, "Rentabilidad", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "Rentabilidad", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "Rentabilidad", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = Rentabilidad.class.getResource("Rentabilidad.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/Rentabilidad.jasper";
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
    
    public void runReporte(int empresa, int ejercicio, Date fechadesde, Date fechahasta, float comisiones, float subvenciones, float amortizaciones, float gastosfinancieros, float cuotaAgriten)
    {
        
        try
        {            
            String masterJrxml = getDirectory().getPath() + 
							"\\reportsPackage\\Rentabilidad.jrxml";
            
            if (masterJrxml == null) 
            	Message.ShowErrorMessage(parent, "Rentabilidad", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                // Compilar directamente desde JRXML
                masterReport = JasperCompileManager.compileReport(masterJrxml);       
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("FechaDesde", fechadesde);
	            parameters.put("FechaHasta", fechahasta);
	            parameters.put("Comisiones", comisiones);
	            parameters.put("Subvenciones", subvenciones);
	            parameters.put("Amortizaciones", amortizaciones);
	            parameters.put("GastosFinancieros", gastosfinancieros);
	            parameters.put("CuotaAgriten", cuotaAgriten);
	
	            //Informe diseñado y compilado con iReport
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,conn);
	
	            //Se lanza el Viewer de Jasper, no termina aplicaciÃ³n al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop");
	            jviewer.setVisible(true);
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "Rentabilidad", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "Rentabilidad", ex);
                }
    }
}
