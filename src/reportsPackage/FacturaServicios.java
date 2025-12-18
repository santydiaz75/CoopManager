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
public class FacturaServicios 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public FacturaServicios(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "FacturaServicios", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "FacturaServicios", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "FacturaServicios", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = FacturaServicios.class.getResource("FacturaServicios.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/FacturaServicios.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/FacturaServicios.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int idFacturaDesde, int idFacturaHasta, String cuentaBancaria)
    {
        
        try
        {   
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory + 
            			"\\reportsPackage\\FacturaServicios.jasper";
            
            if (master == null)          
            	Message.ShowErrorMessage(parent, "FacturaServicios", "No encuentro el archivo del informe maestro.");
            else {

	            JasperReport masterReport = null;
	            masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);              
	            
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("IdFacturaDesde", idFacturaDesde);
	            parameters.put("IdFacturaHasta", idFacturaHasta);
	            parameters.put("CuentaBancaria", cuentaBancaria);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	
            
            String sqlQuery = "SELECT " +
                "fc.Empresa AS facturascabecera_Empresa, " +
                "e.NIF AS empresas_NIF, " +
                "e.Direccion AS empresas_Direccion, " +
                "e.Poblacion AS empresas_Poblacion, " +
                "e.CodigoPostal AS empresas_CodigoPostal, " +
                "(COALESCE(e.CodigoPostal + ', ', '') + COALESCE(e.Poblacion, '')) AS empresas_CPPoblacion, " +
                "e.Provincia AS empresas_Provincia, " +
                "e.CorreoElectronico AS empresas_CorreoElectronico, " +
                "e.Telefono AS empresas_Telefono, " +
                "e.Fax AS empresas_Fax, " +
                "fc.Ejercicio AS facturascabecera_Ejercicio, " +
                "fc.IdFactura AS facturascabecera_IdFactura, " +
                "fc.Semana AS facturascabecera_Semana, " +
                "fc.Fecha AS facturascabecera_Fecha, " +
                "fc.CuentaCliente AS facturascabecera_CuentaCliente, " +
                "fc.Nif AS facturascabecera_Nif, " +
                "fc.Nombre AS facturascabecera_Nombre, " +
                "fc.Direccion AS facturascabecera_Direccion, " +
                "fc.Poblacion AS facturascabecera_Poblacion, " +
                "fc.Provincia AS facturascabecera_Provincia, " +
                "fc.CodigoPostal AS facturascabecera_CodigoPostal, " +
                "(COALESCE(fc.CodigoPostal + ', ', '') + COALESCE(fc.Poblacion, '')) AS facturascabecera_CPPoblacion, " +
                "fc.BaseImponible AS facturascabecera_BaseImponible, " +
                "fc.TipoImpuesto AS facturascabecera_TipoImpuesto, " +
                "fc.ImporteImpuesto AS facturascabecera_ImporteImpuesto, " +
                "fc.ImporteFactura AS facturascabecera_ImporteFactura, " +
                "fl.Linea AS facturaslineas_Linea, " +
                "fl.Unidades AS facturaslineas_Unidades, " +
                "fl.Concepto AS facturaslineas_Concepto, " +
                "fl.Precio AS facturaslineas_Precio, " +
                "fl.PctImpuesto AS facturaslineas_PctImpuesto, " +
                "fl.Importe AS facturaslineas_Importe, " +
                "COALESCE(c.ConIGIC, 1) AS ConceptosConIGIC, " +
                "COALESCE(c.IgnorarUnidades, 0) AS ConceptosIgnorarUnidades, " +
                "e.Lopd AS Lopd " +
                "FROM facturascabecera fc " +
                "INNER JOIN empresas e ON e.IdEmpresa = fc.Empresa " +
                "RIGHT OUTER JOIN facturaslineas fl ON fl.Empresa = fc.Empresa " +
                "    AND fl.Ejercicio = fc.Ejercicio " +
                "    AND fl.IdFactura = fc.IdFactura " +
                "LEFT OUTER JOIN conceptos c ON fl.Empresa = c.Empresa " +
                "    AND fl.Ejercicio = c.Ejercicio " +
                "    AND fl.IdConcepto = c.Concepto " +
                "WHERE fc.Empresa = ? " +
                "    AND fc.Ejercicio = ? " +
                "    AND fc.IdFactura >= ? " +
                "    AND fc.IdFactura <= ? " +
                "ORDER BY fc.Empresa, fc.Ejercicio, fc.IdFactura, ConceptosConIGIC DESC, fl.Linea ASC";
            
            System.out.println("DEBUG: Executing corrected SQL Server query with proper JOIN...");
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, empresa);
            pstmt.setInt(2, ejercicio);
            pstmt.setInt(3, idFacturaDesde);
            pstmt.setInt(4, idFacturaHasta);
            ResultSet rs = pstmt.executeQuery();
	            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            System.out.println("INFO: FacturaServicios - usando consulta SQL corregida");

	            System.out.println("DEBUG: Filling report with corrected data source...");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - FacturaServicios (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "FacturaServicios", "Mensaje de Error: " + j.getMessage());
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
        	Message.ShowErrorMessage(parent, "FacturaServicios", ex);
        }
    }
}
