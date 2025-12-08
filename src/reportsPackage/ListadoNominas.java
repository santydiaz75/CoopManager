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
public class ListadoNominas 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ListadoNominas(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "ListadoNominas", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ListadoNominas", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ListadoNominas", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ListadoNominas.class.getResource("ListadoNominas.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ListadoNominas.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/ListadoNominas.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int mes)
    {
        
        try
        {            
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\ListadoNominas.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "ListadoNominas", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            // Parámetros del reporte
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("Mes", mes);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            
                // === SOLUCION: Usar consulta SQL corregida ===
                String sqlQuery = "SELECT e.empresa, e.ejercicio, e.idEmpleado, " +
                                 "(e.nombre + ' ' + coalesce(e.apellidos, '')) AS NombreCompleto, " +
                                 "en.TotalDevengado, en.ImporteIrpf, en.ImporteSegSoc, " +
                                 "(en.ImporteSegAutonomo + en.ImporteEmbargo) AS ImporteOtros, " +
                                 "en.TotalLiquido, en.ImporteBonificacion, em.Lopd " +
                                 "FROM empleados e INNER JOIN empresas em ON e.Empresa = em.IdEmpresa " +
                                 "INNER JOIN empleadosnominas en ON " +
                                 "e.Empresa = en.Empresa and e.Ejercicio = en.Ejercicio and e.IdEmpleado = en.IdEmpleado " +
                                 "where e.ejercicio = ? and e.empresa = ? and en.Mes = ? " +
                                 "order by e.nombre";
                
                System.out.println("DEBUG: Executing corrected SQL query for ListadoNominas...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                pstmt.setInt(1, ejercicio);
                pstmt.setInt(2, empresa);
                pstmt.setInt(3, mes);
                ResultSet rs = pstmt.executeQuery();
                
                // Crear data source from ResultSet
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            // === ADVERTENCIA: CONSULTA SQL CORREGIDA ===
	            System.out.println("INFO: ListadoNominas - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            // Informe diseñado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);
	
	            // Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
	            jviewer.setTitle("GestCoop - ListadoNominas (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ListadoNominas", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "ListadoNominas", ex);
                }
    }
}
