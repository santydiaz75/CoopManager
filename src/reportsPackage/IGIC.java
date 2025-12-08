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
public class IGIC 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public IGIC(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "IGIC", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "IGIC", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "IGIC", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = IGIC.class.getResource("IGIC.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/IGIC.jasper";
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
    
    public void runReporte(int empresa, int ejercicio, Date FechaDesde, Date FechaHasta)
    {
        
        try
        {            
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\IGIC.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "IGIC", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("FechaDesde", FechaDesde);
	            parameters.put("FechaHasta", FechaHasta);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            
	            // === SOLUCION: Usar tabla liquidaciones en lugar de igic ===
	            // La tabla 'igic' no existe - los datos estan en 'liquidaciones'
	            // Creamos una consulta que obtiene los datos de IGIC desde liquidaciones
	            
	            String sqlQuery = """
	                SELECT 
	                    l.Empresa,
	                    l.Ejercicio, 
	                    l.NumeroFactura,
	                    l.Fecha,
	                    l.IdCosechero,
	                    l.Mes,
	                    l.TipoIGIC as TipoIgic,
	                    l.BaseImponible,
	                    l.ImporteIGIC as ImporteIgic,
	                    l.TipoIRPF,
	                    l.ImporteIRPF,
	                    CONCAT(COALESCE(c.Nombre, ''), ' ', COALESCE(c.Apellidos, '')) as NombreApellidos,
	                    c.Nombre,
	                    c.Apellidos,
	                    c.Nif,
	                    e.Lopd as lopd
	                FROM liquidaciones l 
	                LEFT JOIN cosecheros c ON l.IdCosechero = c.IdCosechero 
	                    AND l.Empresa = c.Empresa AND l.Ejercicio = c.Ejercicio
	                LEFT JOIN empresas e ON l.Empresa = e.IdEmpresa
	                WHERE l.Empresa = ? AND l.Ejercicio = ? 
	                    AND l.Fecha >= ? AND l.Fecha <= ?
	                    AND l.ImporteIGIC > 0
	                ORDER BY l.Fecha, l.NumeroFactura
	                """;
	            
	            System.out.println("DEBUG: Executing corrected SQL query using liquidaciones table...");
	            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
	            pstmt.setInt(1, empresa);
	            pstmt.setInt(2, ejercicio);
	            pstmt.setDate(3, new java.sql.Date(FechaDesde.getTime()));
	            pstmt.setDate(4, new java.sql.Date(FechaHasta.getTime()));
	            ResultSet rs = pstmt.executeQuery();
	            
	            // Crear data source from ResultSet
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            // === ADVERTENCIA: CONSULTA SQL CORREGIDA ===
	            // Se ha corregido para usar la tabla 'liquidaciones' en lugar de 'igic'
	            // La tabla 'igic' no existia en la base de datos
	            // Los datos de IGIC se obtienen desde liquidaciones con JOIN a cosecheros
	            System.out.println("INFO: IGIC - usando consulta corregida desde tabla liquidaciones");

	            System.out.println("DEBUG: Filling report with data from liquidaciones table...");
	            //Informe diseñado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - IGIC (Datos desde Liquidaciones)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "IGIC", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "IGIC", ex);
                }
    }
}
