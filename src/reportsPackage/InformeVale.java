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
public class InformeVale 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private java.awt.Frame parent;
    
    public InformeVale(java.awt.Frame parent)
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
        	Message.ShowErrorMessage(parent, "InformeVale", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "InformeVale", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "InformeVale", e);
        }
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = InformeVale.class.getResource("InformeVale.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/InformeVale.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/InformeVale.jasper"
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
    
    public void runReporte(int empresa, int ejercicio, int Cosechero, int SemanaDesde, int SemanaHasta)
    {
        
        try
        {            
            String master = getDirectory().getPath() + 
							"\\reportsPackage\\InformeVale.jasper";
            
            if (master == null) 
            	Message.ShowErrorMessage(parent, "InformeVale", "No encuentro el archivo del informe maestro.");
            else {
            
            	JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);       
            
                // === SOLUCION: Usar consulta SQL corregida ===
                // En lugar de usar la consulta del .jasper (que tiene referencias hardcodeadas),
                // ejecutamos una consulta corregida y pasamos los datos como JRResultSetDataSource
                
                String sqlQuery = "SELECT ec.Empresa, ec.Ejercicio, " +
                                 "case when ? = 0 then 0 else co.IdCosechero end as IdCosechero, " +
                                 "case when ? = 0 then 'Todos' else (co.Apellidos + ', ' + co.Nombre) end as NombreApellidos, " +
                                 "ca.IdCategoria, ca.NombreCategoria, " +
                                 "case when ? = 0 then 'Todas' else z.NombreZona end as NombreZona, " +
                                 "(SELECT sum(ec1.NumPinas) FROM entradascabecera ec1 " +
                                 " where (ec1.IdCosechero = ? or ? = 0) and ec1.Empresa = ? and ec1.Ejercicio = ? " +
                                 " and ec1.Semana >= ? and ec1.Semana <= ?) as NumPinas, " +
                                 "(SELECT sum(el1.NumKilos) FROM entradascabecera ec1 " +
                                 " inner join entradaslineas el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada " +
                                 " where (ec1.IdCosechero = ? or ? = 0) and ec1.Empresa = ? and ec1.Ejercicio = ? " +
                                 " and ec1.Semana >= ? and ec1.Semana <= ?) as KilosTotal, " +
                                 "Sum(el.NumKilos) as NumKilos, e.Lopd " +
                                 "FROM entradascabecera ec " +
                                 "inner join empresas e on e.IdEmpresa = ec.Empresa " +
                                 "inner join cosecheros co on ec.Empresa = co.Empresa and ec.Ejercicio = co.Ejercicio and ec.IdCosechero = co.IdCosechero " +
                                 "inner join entradaslineas el on ec.Empresa = el.Empresa and ec.Ejercicio = el.Ejercicio and ec.IdEntrada = el.IdEntrada " +
                                 "inner join categorias ca on el.Empresa = ca.Empresa and el.Ejercicio = ca.Ejercicio and el.IdCategoria = ca.IdCategoria " +
                                 "inner join zonas z on ec.Empresa = z.Empresa and ec.Ejercicio = z.Ejercicio and ec.IdZona = z.IdZona " +
                                 "where (ec.IdCosechero = ? or ? = 0) and ec.Empresa = ? and ec.Ejercicio = ? " +
                                 "and ec.Semana >= ? and ec.Semana <= ? " +
                                 "group by ec.Empresa, ec.Ejercicio, co.IdCosechero, co.Apellidos, co.Nombre, ca.IdCategoria, ca.NombreCategoria, z.NombreZona, e.Lopd, ca.Orden " +
                                 "order by ca.Orden Desc";
                
                System.out.println("DEBUG: Executing corrected SQL query...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                
                // Set parameters (multiple references to same values due to CASE statements and subqueries)
                int paramIndex = 1;
                pstmt.setInt(paramIndex++, Cosechero);  // case when ? = 0 then 0 else co.IdCosechero
                pstmt.setInt(paramIndex++, Cosechero);  // case when ? = 0 then 'Todos' else...
                pstmt.setInt(paramIndex++, Cosechero);  // case when ? = 0 then 'Todas' else...
                pstmt.setInt(paramIndex++, Cosechero);  // first subquery WHERE (ec1.IdCosechero = ? or ? = 0)
                pstmt.setInt(paramIndex++, Cosechero);
                pstmt.setInt(paramIndex++, empresa);    // first subquery empresa
                pstmt.setInt(paramIndex++, ejercicio);  // first subquery ejercicio  
                pstmt.setInt(paramIndex++, SemanaDesde); // first subquery semana desde
                pstmt.setInt(paramIndex++, SemanaHasta); // first subquery semana hasta
                pstmt.setInt(paramIndex++, Cosechero);  // second subquery WHERE (ec1.IdCosechero = ? or ? = 0)
                pstmt.setInt(paramIndex++, Cosechero);
                pstmt.setInt(paramIndex++, empresa);    // second subquery empresa
                pstmt.setInt(paramIndex++, ejercicio);  // second subquery ejercicio
                pstmt.setInt(paramIndex++, SemanaDesde); // second subquery semana desde
                pstmt.setInt(paramIndex++, SemanaHasta); // second subquery semana hasta
                pstmt.setInt(paramIndex++, Cosechero);  // main query WHERE (ec.IdCosechero = ? or ? = 0)
                pstmt.setInt(paramIndex++, Cosechero);
                pstmt.setInt(paramIndex++, empresa);    // main query empresa
                pstmt.setInt(paramIndex++, ejercicio);  // main query ejercicio
                pstmt.setInt(paramIndex++, SemanaDesde); // main query semana desde
                pstmt.setInt(paramIndex++, SemanaHasta); // main query semana hasta
                
                ResultSet rs = pstmt.executeQuery();
                
                // Crear data source from ResultSet
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("Cosechero", Cosechero);
	            parameters.put("SemanaDesde", SemanaDesde);
	            parameters.put("SemanaHasta", SemanaHasta);

	            // === ADVERTENCIA: CONSULTA SQL CORREGIDA ===
	            // Se ha eliminado las referencias hardcodeadas a [db_aa764d_coopmanagerdb].[dbo]
	            // y se utiliza PreparedStatement con JRResultSetDataSource para evitar errores SQL
	            System.out.println("INFO: InformeVale - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            //Informe diseñado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - InformeVale (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }        catch (Exception j)
        {
        	Message.ShowErrorMessage(parent, "InformeVale", "Mensaje de Error: " + j.getMessage());
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
                	Message.ShowErrorMessage(parent, "InformeVale", ex);
                }
    }
}
