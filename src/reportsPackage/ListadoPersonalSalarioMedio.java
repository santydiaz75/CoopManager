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
public class ListadoPersonalSalarioMedio 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ListadoPersonalSalarioMedio(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ListadoPersonalSalarioMedio.class.getResource("ListadoPersonalSalarioMedio.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ListadoPersonalSalarioMedio.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/ListadoPersonalSalarioMedio.jasper"
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
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\ListadoPersonalSalarioMedio.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            
                String sqlQuery = "SELECT e.empresa, e.ejercicio, e.idEmpleado, " +
                                 "(e.nombre + ' ' + coalesce(e.apellidos, '')) AS NombreCompleto, " +
                                 "e.nif, em.Lopd, " +
                                 "round(sum(n.TotalDevengado + n.ImporteBonificacion) / count(n.IdEmpleado),2) As BrutoMedio, " +
                                 "round(sum(n.TotalLiquido + n.ImporteBonificacion) / count(n.IdEmpleado),2) As LiquidoMedio " +
                                 "FROM empleados e inner join empresas em On e.Empresa = em.IdEmpresa " +
                                 "inner join empleadosnominas n On e.Empresa = n.empresa And e.ejercicio = n.ejercicio " +
                                 "And e.idEmpleado = n.idEmpleado " +
                                 "where e.ejercicio = ? and e.empresa = ? " +
                                 "group by e.empresa, e.ejercicio, e.idEmpleado, e.nombre, e.apellidos, e.nif, em.Lopd " +
                                 "order by e.nombre";
                
                System.out.println("DEBUG: Executing corrected SQL query for ListadoPersonalSalarioMedio...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                pstmt.setInt(1, ejercicio);
                pstmt.setInt(2, empresa);
                ResultSet rs = pstmt.executeQuery();
                
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            System.out.println("INFO: ListadoPersonalSalarioMedio - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);
	
	            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
	            jviewer.setTitle("GestCoop - ListadoPersonalSalarioMedio (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "ListadoPersonalSalarioMedio", ex);
                }
    }
}
