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
public class PreLiquidacion 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private Object parent;
    
    public PreLiquidacion(Object parent)
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
        	Message.ShowErrorMessage(parent, "PreLiquidacion", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "PreLiquidacion", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "PreLiquidacion", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = PreLiquidacion.class.getResource("PreLiquidacion.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/PreLiquidacion.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/PreLiquidacion.jasper"
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
    		Date fechaDesde, Date fechaHasta)
    {
        try
        {       
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory +  
            			"\\reportsPackage\\PreLiquidacion.jasper";
            
            if (master == null) {
            	Message.ShowErrorMessage(parent, "PreLiquidacion", "No encuentro el archivo del informe maestro.");
            	return;
            }

	        JasperReport masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);
	        
	        String sql = "SELECT co.Empresa, co.Ejercicio, m.NombreMes, co.IdCosechero, " +
	        		     "(co.Apellidos + ', ' + co.Nombre) as NombreApellidos, " +
	        		     "coalesce(co.TipoIgic,0) as TipoIgic, coalesce(co.TipoIrpf, 0) as TipoIrpf, " +
	        		     "coalesce(dbo.PreLiquidacionGetBaseImponible(?, ?, ?, ?, co.IdCosechero), 0) as BaseImponible, " +
	        		     "coalesce(dbo.PreLiquidacionGetNumKilos(?, ?, ?, ?, co.IdCosechero), 0) as NumKilos, " +
	        		     "coalesce(dbo.PreLiquidacionKilosInutilizadosCosechero(?, ?, ?, ?, co.IdCosechero), 0) as NumKilosInut, " +
	        		     "coalesce(dbo.PreLiquidacionGetNumPinas(?, ?, ?, ?, co.IdCosechero), 0) as NumPinas, e.Lopd " +
	        		     "FROM cosecheros co inner join empresas e On co.Empresa = e.IdEmpresa " +
	        		     "left outer join meses m on ? = m.mes " +
	        		     "where co.Ejercicio = ? and co.Empresa = ? " +
	        		     "group by co.Empresa, co.Ejercicio, m.NombreMes, co.IdCosechero, " +
	        		     "(co.Apellidos + ', ' + co.Nombre), coalesce(co.TipoIgic,0), coalesce(co.TipoIrpf, 0), e.Lopd";
	        
	        System.out.println("PreLiquidacion - Executing SQL: " + sql);
	        
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setInt(1, empresa);
	        pstmt.setInt(2, ejercicio);
	        pstmt.setInt(3, semanaDesde);
	        pstmt.setInt(4, semanaHasta);
	        pstmt.setInt(5, empresa);
	        pstmt.setInt(6, ejercicio);
	        pstmt.setInt(7, semanaDesde);
	        pstmt.setInt(8, semanaHasta);
	        pstmt.setInt(9, empresa);
	        pstmt.setInt(10, ejercicio);
	        pstmt.setInt(11, semanaDesde);
	        pstmt.setInt(12, semanaHasta);
	        pstmt.setInt(13, empresa);
	        pstmt.setInt(14, ejercicio);
	        pstmt.setInt(15, semanaDesde);
	        pstmt.setInt(16, semanaHasta);
	        pstmt.setInt(17, mes);
	        pstmt.setInt(18, ejercicio);
	        pstmt.setInt(19, empresa);
	        
	        System.out.println("PreLiquidacion - Parameters: empresa=" + empresa + ", ejercicio=" + ejercicio + 
	        		           ", mes=" + mes + ", semanaDesde=" + semanaDesde + ", semanaHasta=" + semanaHasta);
	        
	        ResultSet rs = pstmt.executeQuery();
	        
	        JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);
	        
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Empresa", empresa);
	        parameters.put("Ejercicio", ejercicio);
	        parameters.put("Mes", mes);
	        parameters.put("SemanaDesde", semanaDesde);
	        parameters.put("SemanaHasta", semanaHasta);
	        parameters.put("FechaDesde", fechaDesde);
	        parameters.put("FechaHasta", fechaHasta);
	        parameters.put("LOGO_DIR", workDirectory + "\\reportsPackage\\Anagrama" + empresa + ".jpg");
	        parameters.put("SUBREPORT_DIR", workDirectory + "\\reportsPackage\\");
	        
	        JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);

	        JasperViewer jviewer = new JasperViewer(jasperPrint, false);
	        jviewer.setTitle("GestCoop - PreLiquidacion");
	        jviewer.setVisible(true);
	        
	        rs.close();
	        pstmt.close();
        }
        catch (Exception j)
        {
        	j.printStackTrace();
        	Message.ShowErrorMessage(parent, "PreLiquidacion", "Mensaje de Error: " + j.getMessage());
        }
    }    public void cerrar()
    {
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "PreLiquidacion", ex);
        }
    }
}
