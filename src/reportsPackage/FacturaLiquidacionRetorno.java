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
public class FacturaLiquidacionRetorno 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private Object parent;
    
    public FacturaLiquidacionRetorno(Object parent)
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
        	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = FacturaLiquidacionRetorno.class.getResource("FacturaLiquidacionRetorno.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/FacturaLiquidacionRetorno.jasper";
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
    
    public void runReporte(int empresa, int ejercicio)
    {
        
        try
        {       
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory +  
            			"\\reportsPackage\\FacturaLiquidacionRetorno.jasper";
            
            if (master == null)          
            	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", "No encuentro el archivo del informe maestro.");
            else {

	            JasperReport masterReport = null;
	            masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);              
	            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            parameters.put("SUBREPORT_DIR", workDirectory +  
    			"\\reportsPackage\\");
	            
	            
	
	            
	            String sqlQuery = "SELECT l.empresa, l.ejercicio, l.NumeroFactura, l.fecha, l.IdCosechero, " +
	                "(COALESCE(co.Apellidos + ', ', '') + COALESCE(co.Nombre, '')) as NombreApellidos, " +
	                "co.NIF, co.Direccion, co.CodigoPostal, co.Poblacion, " +
	                "COALESCE(l.TipoIgic, 0) as TipoIgic, COALESCE(l.TipoIrpf, 0) as TipoIrpf, " +
	                "COALESCE(l.ImporteIgic, 0) as ImporteIgic, COALESCE(l.ImporteIrpf, 0) as ImporteIrpf, " +
	                "lr.IdCategoria, ca.NombreCategoria, lr.KilosTotal, lr.PrecioEscandallo, " +
	                "CAST(NULL AS VARCHAR(1)) AS Lopd, lr.Titulo, lr.Concepto " +
	                "FROM liquidaciones l " +
	                "INNER JOIN empresas e ON l.empresa = e.IdEmpresa " +
	                "LEFT OUTER JOIN liquidacionesretorno lr ON l.empresa = lr.empresa " +
	                "    AND l.ejercicio = lr.ejercicio AND l.NumeroFactura = lr.NumeroFactura " +
	                "LEFT OUTER JOIN cosecheros co ON l.IdCosechero = co.IdCosechero " +
	                "    AND l.ejercicio = co.Ejercicio AND l.empresa = co.Empresa " +
	                "    AND co.idgrupo = co.IdCosechero " +
	                "LEFT OUTER JOIN categorias ca ON lr.IdCategoria = ca.IdCategoria " +
	                "    AND lr.ejercicio = ca.ejercicio AND lr.empresa = ca.empresa " +
	                "WHERE l.mes >= 13 AND l.ejercicio = ? AND l.empresa = ?";
	            
	            System.out.println("DEBUG: Executing corrected SQL Server query for FacturaLiquidacionRetorno...");
	            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
	            pstmt.setInt(1, ejercicio);
	            pstmt.setInt(2, empresa);
	            ResultSet rs = pstmt.executeQuery();
	            
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);

	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - FacturaLiquidacionRetorno (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	j.printStackTrace();
        	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", "Mensaje de Error: " + j.getMessage());
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
        	Message.ShowErrorMessage(parent, "FacturaLiquidacionRetorno", ex);
        }
    }
}
