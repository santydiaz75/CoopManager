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
public class ListadoCategoriasKilosPorCosechero 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public ListadoCategoriasKilosPorCosechero(java.awt.Frame parent)
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
            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("FechaDesde", fechadesde);
	            parameters.put("FechaHasta", fechahasta);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");

                
                String sqlQuery = "SELECT v.empresa, v.nif, v.apellidos, v.nombre, v.numkilosreferencia, " +
                                 "e.Lopd, c.NombreCategoria, sum(el.numkilos) as KilosCategorias " +
                                 "FROM viewentradasquery v " +
                                 "inner join entradaslineas el ON v.IdEntrada = el.IdEntrada " +
                                 "and v.Empresa = el.Empresa and v.Ejercicio = el.Ejercicio " +
                                 "inner join empresas e ON v.Empresa = e.IdEmpresa " +
                                 "inner join categorias c ON el.Empresa = c.Empresa and el.Ejercicio = c.Ejercicio and el.IdCategoria = c.IdCategoria " +
                                 "where v.Fecha >= ? and v.Fecha <= ? and v.empresa = ? " +
                                 "group by v.empresa, v.nif, v.apellidos, v.nombre, v.numkilosreferencia, " +
                                 "e.Lopd, c.NombreCategoria, v.idCosechero, c.orden " +
                                 "order by v.apellidos, v.idCosechero, c.orden desc";
                
                System.out.println("DEBUG: Executing corrected SQL query...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                pstmt.setDate(1, new java.sql.Date(fechadesde.getTime()));
                pstmt.setDate(2, new java.sql.Date(fechahasta.getTime()));
                pstmt.setInt(3, empresa);
                ResultSet rs = pstmt.executeQuery();
                
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            System.out.println("INFO: ListadoCategoriasKilosPorCosechero - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ListadoCategoriasKilosPorCosechero (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            rs.close();
	            pstmt.close();
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
