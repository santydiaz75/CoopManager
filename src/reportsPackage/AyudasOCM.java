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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.*;


/**
 *
 * @author WinDoctor
 */
public class AyudasOCM 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public AyudasOCM(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "AyudasOCM", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "AyudasOCM", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "AyudasOCM", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = AyudasOCM.class.getResource("AyudasOCM.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/AyudasOCM.jasper";
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
    
    public void runReporte(int empresa, int ejercicio, String bimestre, int SemanaDesde, int SemanaHasta)
    {
        
        try
        {            
            String master = getDirectory().getPath() + 
							"\\reportsPackage\\AyudasOCM.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "AyudasOCM", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Bimestre", bimestre);
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("SemanaDesde", SemanaDesde);
	            parameters.put("SemanaHasta", SemanaHasta);

                // === SOLUCION: Usar consulta SQL corregida ===
                // En lugar de usar la consulta del .jasper (que tiene referencias hardcodeadas),
                // ejecutamos una consulta corregida y pasamos los datos como JRResultSetDataSource
                
                String sqlQuery = "SELECT ec.Empresa, ec.Ejercicio, ec.IdCosechero, " +
                                 "dbo.CosecheroGetNombreByNif(co.Empresa, co.Ejercicio, co.NIF) as NombreApellidos, " +
                                 "co.NIF, co.Direccion, co.Poblacion, co.CodigoPostal, co.Telefono1, " +
                                 "co.IdBanco, co.IdSucursal, co.DigitoControl, co.CuentaBancaria, co.CuentaContable, " +
                                 "sum(ec.NumPinas) AS NumPinas, " +
                                 "(SELECT sum(el1.NumKilos) FROM entradascabecera ec1 " +
                                 " inner join entradaslineas el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada " +
                                 " where (ec.IdCosechero = ec1.IdCosechero) and ec1.Empresa = ? and ec1.Ejercicio = ? " +
                                 " and ec1.Semana >= ? and ec1.Semana <= ?) as Kilos, " +
                                 "e.Lopd " +
                                 "FROM entradascabecera ec " +
                                 "inner join cosecheros co on ec.Empresa = co.Empresa and ec.Ejercicio = co.Ejercicio and ec.IdCosechero = co.IdCosechero " +
                                 "inner join empresas e on e.IdEmpresa = co.Empresa " +
                                 "where ec.Empresa = ? and ec.Ejercicio = ? and ec.Semana >= ? and ec.Semana <= ? " +
                                 "group by ec.Empresa, ec.Ejercicio, ec.IdCosechero, " +
                                 "dbo.CosecheroGetNombreByNif(co.Empresa, co.Ejercicio, co.NIF), " +
                                 "co.NIF, co.Direccion, co.Poblacion, co.CodigoPostal, co.Telefono1, " +
                                 "co.IdBanco, co.IdSucursal, co.DigitoControl, co.CuentaBancaria, co.CuentaContable, e.Lopd " +
                                 "order by NombreApellidos";
                
                System.out.println("DEBUG: Executing corrected SQL query...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                pstmt.setInt(1, empresa);     // subquery empresa
                pstmt.setInt(2, ejercicio);   // subquery ejercicio
                pstmt.setInt(3, SemanaDesde); // subquery semana desde
                pstmt.setInt(4, SemanaHasta); // subquery semana hasta
                pstmt.setInt(5, empresa);     // main query empresa
                pstmt.setInt(6, ejercicio);   // main query ejercicio
                pstmt.setInt(7, SemanaDesde); // main query semana desde
                pstmt.setInt(8, SemanaHasta); // main query semana hasta
                ResultSet rs = pstmt.executeQuery();
                
                // Crear data source from ResultSet
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            // === ADVERTENCIA: CONSULTA SQL CORREGIDA ===
	            // Se ha eliminado las referencias hardcodeadas a [db_aa764d_coopmanagerdb].[dbo]
	            // y se utiliza PreparedStatement con JRResultSetDataSource para evitar errores SQL
	            System.out.println("INFO: AyudasOCM - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            //Informe diseñado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - AyudasOCM (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "AyudasOCM", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "AyudasOCM", ex);
                }
    }
}
