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
public class ResumenLiquidacion 
{
	private static File WORKING_DIRECTORY;
    private Connection conn;
    private Object parent;
    
    public ResumenLiquidacion(Object parent)
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
        	Message.ShowErrorMessage(parent, "ResumenLiquidacion", ex.getMessage());
        } 
        catch (SQLException ex) 
        {
        	Message.ShowErrorMessage(parent, "ResumenLiquidacion", ex.getMessage());
        }
        catch (RuntimeException e) {
        	Message.ShowErrorMessage(parent, "ResumenLiquidacion", e);
        }
        
        
    }
    
    public static File getDirectory() {
        if(WORKING_DIRECTORY == null) {
        	
            try {
                URL url = ResumenLiquidacion.class.getResource("ResumenLiquidacion.jasper");  
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile().getParentFile();
                    WORKING_DIRECTORY = f;
                } 
                else
                  if(url.getProtocol().equals("jar")) {
                    String expected = "/reportsPackage/ResumenLiquidacion.jasper";
                    String s = url.toString();
                    s = s.substring(4);                   
                    /**
                    *Extrayendo subcadena eliminando el "jar:"
                    */
                    s = s.substring(0, s.length() - expected.length());   
                    /**
                    *Extrayendo una nueva subcadena eliminando "/reportsPackage/FacturaLiquidacionConCopia.jasper"
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
        //this.id_contact="";
        //this.id_contact = id;
        
        try
        {       
        	String workDirectory = getDirectory().getPath();
            String master =  workDirectory +  
            			"\\reportsPackage\\ResumenLiquidacion.jasper";
            
            if (master == null)          
            	Message.ShowErrorMessage(parent, "ResumenLiquidacion", "No encuentro el archivo del informe maestro.");
            else {

	            JasperReport masterReport = null;
	            masterReport = (JasperReport) JRLoader.loadObjectFromFile(master);              
	            
	            //este es el parámetro, se pueden agregar más parámetros
	            //basta con poner mas parametro.put
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("Empresa", empresa);
	            parameters.put("Ejercicio", ejercicio);
	            parameters.put("Mes", mes);
	            parameters.put("SemanaDesde", semanaDesde);
	            parameters.put("SemanaHasta", semanaHasta);
	            parameters.put("FechaDesde", fechaDesde);
	            parameters.put("FechaHasta", fechaHasta);
	            parameters.put("LOGO_DIR", workDirectory +  
	            		"\\reportsPackage\\Anagrama" + empresa + ".jpg");
	            parameters.put("SUBREPORT_DIR", workDirectory +  
    			"\\reportsPackage\\");
	            
	            
	            // === SOLUCION: Usar consulta SQL corregida ===
                // En lugar de usar la consulta del .jasper (que tiene referencias hardcodeadas),
                // ejecutamos una consulta corregida y pasamos los datos como JRResultSetDataSource
                
                String sqlQuery = "SELECT l.empresa, l.ejercicio, l.mes, m.NombreMes, l.NumeroFactura, l.fecha, " +
                                 "l.IdCosechero, (co.Apellidos + ', ' + co.Nombre) as NombreApellidos, " +
                                 "coalesce(l.BaseImponible,0) as BaseImponible, coalesce(l.ImporteBonificacion,0) as ImporteBonificacion, " +
                                 "coalesce(co.TipoIgic,0) as TipoIgic, coalesce(co.TipoIrpf, 0) as TipoIrpf, " +
                                 "coalesce(l.ImporteIgic,0) as ImporteIgic, coalesce(l.ImporteIrpf, 0) as ImporteIrpf, " +
                                 "coalesce(ll.PinasSemana1, 0) as PinasSemana1, " +
                                 "coalesce(ll.PinasSemana2, 0) as PinasSemana2, " +
                                 "coalesce(ll.PinasSemana3, 0) as PinasSemana3, " +
                                 "coalesce(ll.PinasSemana4, 0) as PinasSemana4, " +
                                 "coalesce(ll.PinasSemana5, 0) as PinasSemana5, " +
                                 "coalesce(ll.PrecioSemana1, 0) as PrecioSemana1, " +
                                 "coalesce(ll.PrecioSemana2, 0) as PrecioSemana2, " +
                                 "coalesce(ll.PrecioSemana3, 0) as PrecioSemana3, " +
                                 "coalesce(ll.PrecioSemana4, 0) as PrecioSemana4, " +
                                 "coalesce(ll.PrecioSemana5, 0) as PrecioSemana5, " +
                                 "Sum(coalesce(ll.KilosSemana1, 0)) as KilosSemana1, " +
                                 "Sum(coalesce(ll.KilosSemana2, 0)) as KilosSemana2, " +
                                 "Sum(coalesce(ll.KilosSemana3, 0)) as KilosSemana3, " +
                                 "Sum(coalesce(ll.KilosSemana4, 0)) as KilosSemana4, " +
                                 "Sum(coalesce(ll.KilosSemana5, 0)) as KilosSemana5, " +
                                 "Sum(coalesce(ll.KilosInutSemana1, 0)) as KilosInutSemana1, " +
                                 "Sum(coalesce(ll.KilosInutSemana2, 0)) as KilosInutSemana2, " +
                                 "Sum(coalesce(ll.KilosInutSemana3, 0)) as KilosInutSemana3, " +
                                 "Sum(coalesce(ll.KilosInutSemana4, 0)) as KilosInutSemana4, " +
                                 "Sum(coalesce(ll.KilosInutSemana5, 0)) as KilosInutSemana5, " +
                                 "e.Lopd " +
                                 "FROM liquidaciones l inner join empresas e on l.empresa = e.IdEmpresa " +
                                 "left outer join liquidacioneslineas ll on l.empresa = ll.empresa and l.ejercicio = ll.ejercicio " +
                                 "and l.NumeroFactura = ll.NumeroFactura " +
                                 "left outer join meses m on l.mes = m.mes " +
                                 "left outer join (Select * From cosecheros where idgrupo = IdCosechero) co on l.IdCosechero = co.IdCosechero and co.Ejercicio = ? and co.Empresa = ? " +
                                 "where l.mes = ? and l.ejercicio = ? and l.empresa = ? " +
                                 "group by l.empresa, l.ejercicio, l.mes, m.NombreMes, l.NumeroFactura, l.fecha, l.IdCosechero, " +
                                 "(co.Apellidos + ', ' + co.Nombre), coalesce(l.BaseImponible,0), coalesce(l.ImporteBonificacion,0), " +
                                 "coalesce(co.TipoIgic,0), coalesce(co.TipoIrpf, 0), coalesce(l.ImporteIgic,0), coalesce(l.ImporteIrpf, 0), " +
                                 "coalesce(ll.PinasSemana1, 0), coalesce(ll.PinasSemana2, 0), coalesce(ll.PinasSemana3, 0), " +
                                 "coalesce(ll.PinasSemana4, 0), coalesce(ll.PinasSemana5, 0), coalesce(ll.PrecioSemana1, 0), " +
                                 "coalesce(ll.PrecioSemana2, 0), coalesce(ll.PrecioSemana3, 0), coalesce(ll.PrecioSemana4, 0), " +
                                 "coalesce(ll.PrecioSemana5, 0), e.Lopd";
                
                System.out.println("DEBUG: Executing corrected SQL query for ResumenLiquidacion...");
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                
                // Set parameters 
                pstmt.setInt(1, ejercicio);  // co.Ejercicio = ?
                pstmt.setInt(2, empresa);    // co.Empresa = ?
                pstmt.setInt(3, mes);        // l.mes = ?
                pstmt.setInt(4, ejercicio);  // l.ejercicio = ?
                pstmt.setInt(5, empresa);    // l.empresa = ?
                
                ResultSet rs = pstmt.executeQuery();
                
                // Crear data source from ResultSet
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

	            // === ADVERTENCIA: CONSULTA SQL CORREGIDA ===
	            // Se ha eliminado las referencias hardcodeadas a [db_aa764d_coopmanagerdb].[dbo]
	            // y se utiliza PreparedStatement con JRResultSetDataSource para evitar errores SQL
	            System.out.println("INFO: ResumenLiquidacion - usando consulta SQL corregida");

                System.out.println("DEBUG: Filling report with corrected data source...");
	            //Informe diseñado y compilado con iReport - usando el dataSource corregido
	            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,parameters,dataSource);

	            //Se lanza el Viewer de Jasper, no termina aplicación al salir
	            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
	            jviewer.setTitle("GestCoop - ResumenLiquidacion (Version Corregida)");
	            jviewer.setVisible(true);
	            
	            // Cerrar recursos
	            rs.close();
	            pstmt.close();
            }
        }

        catch (Exception j)
        {
        	j.printStackTrace();
        	Message.ShowErrorMessage(parent, "ResumenLiquidacion", "Mensaje de Error: " + j.getMessage());
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
        	Message.ShowErrorMessage(parent, "ResumenLiquidacion", ex);
        }
    }
}
