package entitiesPackage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.CallableStatement;
import java.sql.Connection;

import sessionPackage.HibernateSessionFactory;
import sessionPackage.MySession;
import winUIPackage.PreferencesUI;


public class Entity {
	
	public EntityType.EntitiesType entityType;
	private  MySession session;
	
	public  void setSession(MySession session) {
		this.session = session;
	}

	public  MySession getSession() {
		return session;
	}
	
	public Entity() {
	}
	
	public Entity(EntityType.EntitiesType entitytype, MySession session) {
		this.entityType = entitytype;
		this.setSession(session);
	}
	
	public  List<?> executeQueryView(Object parentFrame, String select, String from, String where, String orderby) {
		String query = "";
		try {
			query = "From " + from;
			if (!where.equals(""))
				query = query + " Where " + where;
			
			if (!orderby.equals(""))
				query = query + " order by " + orderby;
			
				
			getSession().getSession().beginTransaction();
			
			Query q = getSession().getSession().createQuery(query);
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.executeQueryView()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.executeQueryView()", e);
		}
		return null;
	}
	
	public  List<?> executeQuery(Object parentFrame, String select, String from, String where, String orderby) {
		String query = "";
		try {
			query = "From " + from;
			if (where.equals(""))
				query = query + " Where id.empresas.idEmpresa="
				+ session.getEmpresa().getIdEmpresa()
				+ " and id.ejercicios.ejercicio="
				+ session.getEjercicio().getEjercicio();
			else
				query = query + " Where " + where + " and id.empresas.idEmpresa="
				+ session.getEmpresa().getIdEmpresa()
				+ " and id.ejercicios.ejercicio="
				+ session.getEjercicio().getEjercicio();
			
			if (!orderby.equals(""))
				query = query + " order by " + orderby;
			
				
			getSession().getSession().beginTransaction();
			
			Query q = getSession().getSession().createQuery(query);
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		}
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.executeQuery()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.executeQuery()", e);
		}
		return null;
	}
	
	public  int newId(Object parentFrame, String tableName, String fieldName) {
		Integer newValue = 0;
		try {
			String strquery = "";
			
			
			strquery = "SELECT Max(" + fieldName + ") FROM " + tableName + " WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio();
			
			Query q = session.getSession().createQuery(strquery);
			List<?> resultList = q.list();
			if (resultList.get(0) != null )
				newValue = ((Integer) resultList.get(0)) + 1;
			else
				newValue = 1;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.newId()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.newId()", e);
		}
		return newValue;
	}
	
	public  int newIdEmpresa(Object parentFrame) {
		Integer newValue = 0;
		try {
			String strquery = "";
			
			
			strquery = "SELECT Max(idEmpresa) FROM Empresas";
			
			Query q = session.getSession().createQuery(strquery);
			List<?> resultList = q.list();
			if (resultList.get(0) != null )
				newValue = ((Integer) resultList.get(0)) + 1;
			else
				newValue = 1;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.newIdEmpresa()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.newIdEmpresa()", e);
		}
		return newValue;
	}
	
	public  boolean EsUltimaLiquidacion(Object parentFrame, Integer mes) {
		Integer maxMes = 0;
		try {
			String strquery = "";
			
			
			strquery = "SELECT Max(mes) FROM Liquidaciones where mes < 13 and id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" +  session.getEjercicio().getEjercicio();
			
			Query q = session.getSession().createQuery(strquery);
			List<?> resultList = q.list();
			if (resultList.get(0) != null )
				maxMes = ((Integer) resultList.get(0));
			else
				maxMes = 0;
			
			if (maxMes.equals(mes))
				return true;
			else
				return false;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EsUltimaLiquidacion()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EsUltimaLiquidacion()", e);
		}
		return false;
	}
	
	public  List<?> LiquidacionesFindByMes(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Liquidaciones where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" +  session.getEjercicio().getEjercicio() + " and mes=" + mes);
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.LiquidacionesFindByMes()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.LiquidacionesFindByMes()", e);
		}
		return null;
	}
	
	public  void LiquidacionDeleteByMes(Object parentFrame, Integer mes, Boolean nomessageSuccess, boolean soloDetalle) {
		try {
			
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Liquidacioneslineas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.numeroFactura in " +
					"(Select id.numeroFactura From Liquidaciones where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and mes=" + mes + ")");
			try {
				q.executeUpdate();
				if (!soloDetalle) {
					q = getSession().getSession().createQuery("Delete From Liquidaciones where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and mes=" + mes);
					q.executeUpdate();
				}
				if (nomessageSuccess)
					Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.LiquidacionDeleteByMes()", he);
			}
			getSession().getSession().getTransaction().commit();
				
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.LiquidacionDeleteByMes()", e);
		}
	}
	
	public float LiquidacionGetBaseImponible(Object parentFrame, Integer mes, Integer semanadesde, Integer semanahasta, Integer cosechero) 
    throws SQLException
	{
		float baseimponible = 0;
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionGetBaseImponible", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ ? = call LiquidacionGetBaseImponible(?, ?, ?, ?, ?, ?) }");
	       proc.registerOutParameter(1, Types.FLOAT);
	       proc.setInt(2, session.getEmpresa().getIdEmpresa());
	       proc.setInt(3, session.getEjercicio().getEjercicio());
	       proc.setInt(4, mes);
	       proc.setInt(5, semanadesde);
	       proc.setInt(6, semanahasta);
	       proc.setInt(7, cosechero);
	       proc.execute();
		   baseimponible = proc.getFloat(1);
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {}
	      conn.close();
	   }
	   return baseimponible;
	}
	
	public float LiquidacionImporteBonificacion(Object parentFrame, Integer mes, Integer semanadesde, Integer semanahasta, Integer cosechero) 
    throws SQLException
	{
		float importebonificacion = 0;
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionImporteBonificacion", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ ? = call LiquidacionImporteBonificacion(?, ?, ?, ?, ?, ?) }");
	       proc.registerOutParameter(1, Types.FLOAT);
	       proc.setInt(2, session.getEmpresa().getIdEmpresa());
	       proc.setInt(3, session.getEjercicio().getEjercicio());
	       proc.setInt(4, mes);
	       proc.setInt(5, semanadesde);
	       proc.setInt(6, semanahasta);
	       proc.setInt(7, cosechero);
	       proc.execute();
	       importebonificacion = proc.getFloat(1);
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {}
	      conn.close();
	   }
	   return importebonificacion;
	}
	
	public void LiquidacionInsertKilosInutilizados(Object parentFrame, Integer semanadesde, Integer semanahasta) 
	    throws SQLException
	{
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionInsertKilosInutilizados", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ call LiquidacionInsertKilosInutilizados(?, ?, ?, ?) }");
	       proc.setInt(1, session.getEmpresa().getIdEmpresa());
	       proc.setInt(2, session.getEjercicio().getEjercicio());
	       proc.setInt(3, semanadesde);
	       proc.setInt(4, semanahasta);
	       proc.execute();
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {}
	      conn.close();
	   }
	}
	
	public void LiquidacionGenerate(Object parentFrame, Integer mes, Integer semanadesde, Integer semanahasta) 
    throws SQLException
	{
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionGenerate", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ call LiquidacionGenerate(?, ?, ?, ?, ?) }");
	       proc.setInt(1, session.getEmpresa().getIdEmpresa());
	       proc.setInt(2, session.getEjercicio().getEjercicio());
	       proc.setInt(3, mes);
	       proc.setInt(4, semanadesde);
	       proc.setInt(5, semanahasta);
	       proc.execute();
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {}
	      conn.close();
	   }
	}
	
    public void LiquidacionRetornoGenerate(Object parentFrame, float importekilo, float porcentajeKilos, int ejercicioContable, 
                int numeroBonificacion, java.util.Date fecha, String titulo) 
    throws SQLException
	{
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionRetornoGenerate", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ call LiquidacionRetornoGenerate(?, ?, ?, ?, ?, ?, ?, ?) }");
	       proc.setInt(1, session.getEmpresa().getIdEmpresa());
	       proc.setInt(2, session.getEjercicio().getEjercicio());
           proc.setInt(3, ejercicioContable);
           
           java.sql.Date sqlFecha = new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDate());
           proc.setDate(4, sqlFecha);
           proc.setFloat(5, numeroBonificacion);
	       proc.setFloat(6, importekilo);
           proc.setFloat(7, porcentajeKilos);
           proc.setString(8, titulo);
	       proc.execute();
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {
                  Message.ShowErrorMessage(parentFrame,
					"Entity.LiquidacionRetornoGenerate()", e);
              }
	      conn.close();
	   }
	}
     
    public void LiquidacionRetornoGeneratePorCosechero(Object parentFrame, int cosechero, float importekilo, float numeroKilos, int ejercicioContable, 
            int numeroBonificacion, java.util.Date fecha, String titulo, String concepto) 
	throws SQLException
	{
		Connection conn = null;
		CallableStatement proc = null;
	
	   try
	   {
		   try 
	       {
	        	final String login = "coopuser"; //usuario de acceso a MySQL
	            String url = HibernateSessionFactory.getConnectionURL();
	            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
	            BasicTextEncryptor bte = new BasicTextEncryptor();
	            bte.setPassword("santi");
	            String paswworddecrypt = bte.decrypt("lk8Ay3Lex6JXR3rcsUqxI2dQlRKmTq4N");
	            conn = DriverManager.getConnection(url,login,paswworddecrypt);
	       } 
	       catch (ClassNotFoundException ex) 
	       {
	        	Message.ShowErrorMessage(parentFrame, "LiquidacionRetornoGeneratePorCosechero", ex.getMessage());
	       } 
	       
	       proc = conn.prepareCall("{ call LiquidacionCosecheroRetornoGenerate(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
	       proc.setInt(1, session.getEmpresa().getIdEmpresa());
	       proc.setInt(2, session.getEjercicio().getEjercicio());
           proc.setInt(3, ejercicioContable);
           
           java.sql.Date sqlFecha = new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDate());
           proc.setDate(4, sqlFecha);
           proc.setFloat(5, numeroBonificacion);
           proc.setInt(6, cosechero);
           proc.setFloat(7, importekilo);
           proc.setFloat(8, numeroKilos);
           proc.setString(9, titulo);
           proc.setString(10, concepto);
	       proc.execute();
	   }
	   finally
	   {
	      try
	      {
	         proc.close();
	      }
	      catch (SQLException e) {
	              Message.ShowErrorMessage(parentFrame,
					"Entity.LiquidacionRetornoGenerate()", e);
	          }
	      conn.close();
	   }
	}
    
	public  List<?> ContabilizaLiquidaciones(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontaliquidaciones c " +
			" where c.id.mes = " + mes + " and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaLiquidaciones()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaLiquidaciones()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaNominas(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontanominas c " +
			" where c.id.mes = " + mes + " and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaNominas()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaNominas()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaIndemnizaciones(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontaindemnizaciones c " +
			" where c.id.mes = " + mes + " and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaIndemnizaciones()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaIndemnizaciones()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaBonificaciones(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontabonificaciones c " +
			" where c.id.mes = " + mes + " and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaBonificaciones()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaBonificaciones()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaFacturas(Object parentFrame, Integer facturadesde, Integer facturahasta) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Facturascabecera c " +
			" where c.id.idFactura >= " + facturadesde + " and c.id.idFactura <= " + facturahasta + 
			" and id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + 
			" and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaFacturas()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaFacturas()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaPagos(Object parentFrame, 
			Date fechapagodesde, Date fechapagohasta) {
		try {
			
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
			String strFechaDesde = df.format(fechapagodesde);
			String strFechaHasta = df.format(fechapagohasta);
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontapagos c " +
			" where c.id.fechaPago >= '" + strFechaDesde + "' and c.id.fechaPago <= '" + strFechaHasta + "'" +
					" and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaPagos()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaPagos()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaLiquidacionesPagos(Object parentFrame, 
			Date fechapagodesde, Date fechapagohasta) {
		try {
			
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
			String strFechaDesde = df.format(fechapagodesde);
			String strFechaHasta = df.format(fechapagohasta);
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Liquidacionespagos " +
			" where fechaPago >= '" + strFechaDesde + "' and fechaPago <= '" + strFechaHasta + "'" +
					" and id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaLiquidacionesPagos()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaLiquidacionesPagos()", e);
		}
		return null;
	}
	
	public  List<?> ContabilizaCobros(Object parentFrame, 
			Date fechacobrodesde, Date fechacobrohasta) {
		try {
			
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
			String strFechaDesde = df.format(fechacobrodesde);
			String strFechaHasta = df.format(fechacobrohasta);
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Viewcontacobros c " +
			" where c.id.fechaCobro >= '" + strFechaDesde + "' and c.id.fechaCobro <= '" + strFechaHasta + "'" +
					" and c.id.empresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and c.id.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ContabilizaPagos()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ContabilizaPagos()", e);
		}
		return null;
	}
	
	public  List<?> BancoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindAll()", e);
		}
		return null;
	}
	
	public  List<?> BancoFindDistinctAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("select distinct b.nombreBanco from Bancos b WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindDistinctAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindDistinctAll()", e);
		}
		return null;
	}
	
	public  Bancos BancoFindById(Object parentFrame, BancosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBanco='" + id.getIdBanco() + "' and id.idSucursal='" + id.getIdSucursal() + "'");
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Bancos) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindById()", e);
		}
		return null;
	}
	
	public List<?> BancoFindByIdBanco(Object parentFrame,String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBanco='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindByIdBanco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindByIdBanco()", e);
		}
		return null;
	}
	
	public  List<?> BancoFindByIdSucursal(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idSucursal='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindByIdSucursal()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindByIdSucursal()", e);
		}
		return null;
	}
	
	public  List<?> BancoFindByNombreBanco(Object parentFrame,String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreBanco='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindByNombreBanco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindByNombreBanco()", e);
		}
		return null;
	}
	
	public  List<?> BancoFindByNombreSucursal(Object parentFrame,String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreSucursal='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BancoFindByNombreSucursal()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoFindByNombreSucursal()", e);
		}
		return null;
	}
	
	public  List<?> SucursalFindByIdBanco(Object parentFrame,String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBanco='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.SucursalFindByIdBanco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.SucursalFindByIdBanco()", e);
		}
		return null;
	}
	
	public  void BancoDelete(Object parentFrame, Bancos banco) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Bancos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBanco='" + banco.getId().getIdBanco()+ "' and id.idSucursal='" + banco.getId().getIdSucursal()+ "'");
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.BancoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BancoDelete()", e);
		}
	}
	
	public  List<?> BarcoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Barcos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BarcoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BarcoFindAll()", e);
		}
		return null;
	}
	
	public  Barcos BarcoFindById(Object parentFrame,BarcosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Barcos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBarco=" + id.getIdBarco());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Barcos) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BarcoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BarcoFindById()", e);
		}
		return null;
	}
	
	public  Barcos BarcoFindByIdBarco(Object parentFrame,Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Barcos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBarco=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Barcos) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BarcoFindByIdBarco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BarcoFindByIdBarco()", e);
		}
		return null;
	}
	
	public  List<?> BarcoFindByNombreBarco(Object parentFrame,String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Barcos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreBarco='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BarcoFindByNombreBarco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BarcoFindByNombreBarco()", e);
		}
		return null;
	}
	
	public  void BarcoDelete(Object parentFrame, Barcos barco) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Barcos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idBarco=" + barco.getId().getIdBarco());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.BarcoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BarcoDelete()", e);
		}
	}
	
	public  Calendario CalendarioFindById(Object parentFrame, CalendarioId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.semana=" + id.getSemana());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Calendario) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CalendarioFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CalendarioFindById()", e);
		}
		return null;
	}
	
	public  void CalendarioDelete(Object parentFrame, Calendario calendario) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana=" + calendario.getId().getSemana());
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.CalendarioDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} 
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CalendarioDelete()", e);
		}
	}

	public Integer CalendarioGetSemanaByFecha(Object parentFrame, Date fecha) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
			String strFecha = df.format(fecha);
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select id.semana From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and (desdeFecha='" + strFecha + "' or hastaFecha='" + strFecha + "')");
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Integer) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CalendarioGetSemanaByFecha()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CalendarioGetSemanaByFecha()", e);
		}
		return null;
	}
	
	public Integer SemanaByFecha(Object parentFrame, Date fecha) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
			String strFecha = df.format(fecha);
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select id.semana From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and desdeFecha <= '" + strFecha + "' and hastaFecha >= '" + strFecha + "'");
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Integer) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.SemanaByFecha()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.SemanaByFecha()", e);
		}
		return null;
	}
	
	public Date SemanaGetFechaDesde(Object parentFrame, Integer semana) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select desdeFecha From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana=" + semana);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Date) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.SemanaGetFechaDesde()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.SemanaGetFechaDesde()", e);
		}
		return null;
	}
	
	public Date SemanaGetFechaHasta(Object parentFrame, Integer semana) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select hastaFecha From Calendario where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana=" + semana);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Date) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.SemanaGetFechaHasta()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.SemanaGetFechaHasta()", e);
		}
		return null;
	}
	
	public  Bimestres BimestreFindById(Object parentFrame, BimestresId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Bimestres where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.bimestre=" + id.getBimestre());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Bimestres) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.BimestreFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BimestreFindById()", e);
		}
		return null;
	}
	
	public  void BimestreDelete(Object parentFrame, Bimestres bimestre) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Bimestres where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.bimestre=" + bimestre.getId().getBimestre());
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.BimestreDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.BimestreDelete()", e);
		}
	}
	
	public  Categorias CategoriaFindById(Object parentFrame, CategoriasId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Categorias WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCategoria=" + id.getIdCategoria());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Categorias) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaFindById()", e);
		}
		return null;
	}
	
	public List<?> CategoriaFindAll(Object parentFrame, Boolean onlyActive) {
		try {
			Query q;
			getSession().getSession().beginTransaction();
			if (onlyActive == false) 
				q = getSession().getSession().createQuery("from Categorias WHERE id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio());
			else
				q = getSession().getSession().createQuery("from Categorias WHERE activa <> 0 and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaFindAll()", e);
		}
		return null;
	}
	
	public Categorias CategoriaFindByIdCategoria(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Categorias WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCategoria=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Categorias) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaFindByIdCategoria()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaFindByIdCategoria()", e);
		}
		return null;
	}
	
	public  List<?> CategoriaFindByNombreCategoria(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Categorias WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreCategoria='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaFindByNombreCategoria()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaFindByNombreCategoria()", e);
		}
		return null;
	}
	
	public  List<?> CategoriaFindByCodAgriten(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Categorias WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and codCategoriaAgriten='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaFindByCodAgriten()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaFindByCodAgriten()", e);
		}
		return null;
	}
	
	public Float CategoriaGetKilosCaja(Object parentFrame, int idCategoria) {
		try {
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select numKilosCaja From Categorias where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idCategoria=" + idCategoria);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return ((Number) q.list().get(0)).floatValue();
			else
				return ((Number)0).floatValue();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CategoriaGetKilosCaja()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaGetKilosCaja()", e);
		}
		return null;
	}
	
	public  void CategoriaDelete(Object parentFrame, Categorias categoria) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Categorias WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCategoria=" + categoria.getId().getIdCategoria());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.CategoriaDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CategoriaDelete()", e);
		}
	}
	
	public  Conceptos ConceptoFindById(Object parentFrame, ConceptosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conceptos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.concepto=" + id.getConcepto());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Conceptos) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoFindById()", e);
		}
		return null;
	}
	
	public  List<?> ConceptoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Conceptos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoFindAll()", e);
		}
		return null;
	}
	
	public  Conceptos  ConceptoFindByIdConcepto(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conceptos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.concepto=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Conceptos) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoFindByIdConcepto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoFindByIdConcepto()", e);
		}
		return null;
	}
	
	public  List<?> ConceptoFindByConceptoDesc(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conceptos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and conceptoDesc='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoFindByConceptoDesc()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoFindByConceptoDesc()", e);
		}
		return null;
	}
	
	
	public Short ConceptoConIGIC(Object parentFrame, Integer concepto) {
		try {
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select conIgic From Conceptos where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.concepto=" + concepto);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Short) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoConIGIC()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoConIGIC()", e);
		}
		return null;
	}
	
	public  void ConceptoDelete(Object parentFrame, Conceptos concepto) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Conceptos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.concepto=" + concepto.getId().getConcepto());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.ConceptoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoDelete()", e);
		}
	}
	
	public  List<?> ConceptoPagoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Conceptospago WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoPagoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoPagoFindAll()", e);
		}
		return null;
	}
	
	public  Conceptospago  ConceptoPagoFindByIdConceptoPago(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conceptospago WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.conceptoPago=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Conceptospago) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoPagoFindByIdConceptoPago()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoPagoFindByIdConceptoPago()", e);
		}
		return null;
	}
	
	public  List<?> ConceptoPagoFindByConceptoPagoDesc(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conceptospago WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and conceptoPagoDesc='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConceptoPagoFindByConceptoPagoDesc()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoPagoFindByConceptoPagoDesc()", e);
		}
		return null;
	}
	
	public  void ConceptoPagoDelete(Object parentFrame, Conceptospago concepto) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Conceptospago WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.conceptoPago=" + concepto.getId().getConceptoPago());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.ConceptoPagoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConceptoPagoDelete()", e);
		}
	}
	
	public  List<?> ConductorFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Conductores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConductorFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConductorFindAll()", e);
		}
		return null;
	}
	
	public  Conductores ConductorFindById(Object parentFrame, ConductoresId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conductores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idConductor=" + id.getIdConductor());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Conductores) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConductorFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConductorFindById()", e);
		}
		return null;
	}
	
	public  Conductores ConductorFindByIdConductor(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Conductores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idConductor=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Conductores) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ConductorFindByIdConductor()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConductorFindByIdConductor()", e);
		}
		return null;
	}
	
	public  void ConductorDelete(Object parentFrame, Conductores conductor) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Conductores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idConductor=" + conductor.getId().getIdConductor());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.ConductorDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ConductorDelete()", e);
		}
	}
	
	public  List<?> CosecheroFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroFindAll()", e);
		}
		return null;
	}
	
	public  List<?> CosecheroGetGrupos(Object parentFrame, Integer semanadesde, Integer semanahasta ) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select distinct co from Cosecheros co, Entradascabecera ec " +
					" where co.id.empresas.idEmpresa = ec.id.empresas.idEmpresa and " +
					" co.id.ejercicios.ejercicio = ec.id.ejercicios.ejercicio and " +
					" co.id.idCosechero = CosecheroGetGrupo(" + session.getEmpresa().getIdEmpresa() + 
					", " + session.getEjercicio().getEjercicio() + ", ec.idCosechero) and co.id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and co.id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and idGrupo = co.id.idCosechero and ec.semana >= " + semanadesde +
					" and ec.semana <= " + semanahasta +
					" order by co.id.idCosechero");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroGetGrupos()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroGetGrupos()", e);
		}
		return null;
	}
	
	public  Cosecheros CosecheroFindById(Object parentFrame, CosecherosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCosechero=" + id.getIdCosechero());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Cosecheros) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroFindById()", e);
		}
		return null;
	}
	
	public  Cosecheros CosecheroFindByIdCosechero(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCosechero=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Cosecheros) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroFindByIdCosechero()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroFindByIdCosechero()", e);
		}
		return null;
	}
	
	public  List<?> CosecheroFindByNombreCosechero(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreCosechero='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroFindByNombreCosechero()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroFindByNombreCosechero()", e);
		}
		return null;
	}
	
	public  Integer CosecheroGetZona(Object parentFrame, Integer idCosechero) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select idZona From Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and idCosechero=" + idCosechero);
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Integer) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.CosecheroGetZona()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroGetZona()", e);
		}
		return null;
	}
	
	public  void CosecheroDelete(Object parentFrame, Cosecheros cosechero) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Cosecheros WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCosechero=" + cosechero.getId().getIdCosechero());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.CosecheroDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.CosecheroDelete()", e);
		}
	}
	
	public  List<?> EjercicioFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Ejercicios");
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EjercicioFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EjercicioFindAll()", e);
		}
		return null;
	}
	
	public  Ejercicios EjercicioFindLast(Object parentFrame) {
		try {
			String strquery = "FROM Ejercicios ORDER BY ejercicio DESC";
			
			Query q = session.getSession().createQuery(strquery);
			List<?> resultList = q.list();
			Ejercicios lastEjercicio = ((Ejercicios) resultList.get(0));
			return lastEjercicio;	
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EjercicioFindLast()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EjercicioFindLast()", e);
		}
		return null;	
	}
	
	public  Ejercicios EjercicioFindById(Object parentFrame, Integer id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Ejercicios where ejercicio=" + id);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Ejercicios) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EjercicioFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EjercicioFindById()", e);
		}
		return null;
	}
	
	public  void EjercicioDelete(Object parentFrame, Ejercicios ejercicio) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Ejercicios where ejercicio=" + ejercicio.getEjercicio());
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.EjercicioDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EjercicioDelete()", e);
		}
	}
	
	public  Empleados EmpleadoFindById(Object parentFrame, EmpleadosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Empleados where id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.idEmpleado=" + id.getIdEmpleado());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Empleados) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpleadoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpleadoFindById()", e);
		}
		return null;
	}
	
        public  List<?> EmpleadosNominasFindByEmpleado(Object parentFrame, Integer idempleado) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Empleadosnominas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" +  session.getEjercicio().getEjercicio() + " and idempleado=" + idempleado);
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpleadosNominasFindByEmpleado()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpleadosNominasFindByEmpleado()", e);
		}
		return null;
	}
        
	public  void EmpleadoDelete(Object parentFrame, Empleados empleado) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Empleados where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idEmpleado=" + empleado.getId().getIdEmpleado());
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.EmpleadoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpleadoDelete()", e);
		}
	}
	
	public  List<?> EmpleadoGetEmbargosMes(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Empleadosnominas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and importeEmbargo <> 0 and mes=" + mes);
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpleadoGetEmbargosMes()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpleadoGetEmbargosMes()", e);
		}
		return null;
	}
	
	public  List<?> EmpleadoGetAutonomosMes(Object parentFrame, Integer mes) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Empleadosnominas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and importeSegAutonomo <> 0 and mes=" + mes);
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpleadoGetAutonomosMes()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpleadoGetAutonomosMes()", e);
		}
		return null;
	}
	
	public  List<?> EmpresaFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Empresas");
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaFindAll()", e);
		}
		return null;
	}
	
	public  List<?> EmpresaFindCuentas(Object parentFrame, int empresa) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Empresascuentas where id.empresas.idEmpresa = " + empresa + " order by id.cuentaBancaria");
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaFindCuentas()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaFindCuentas()", e);
		}
		return null;
	}
	
	public  Empresas EmpresaFindActivada(Object parentFrame) {
		try {	
			String strquery = "FROM Empresas WHERE activada <> 0";
			
			Query q = session.getSession().createQuery(strquery);
			List<?> resultList = q.list();
			Empresas empresa = ((Empresas) resultList.get(0));
			return empresa;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaFindActivada()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaFindActivada()", e);
		}
		return null;
	}
	
	public  void EmpresaSetActivada(Object parentFrame, Empresas empresa) {
		
		try {	
			getSession().getSession().beginTransaction();
			String strquery = "UPDATE Empresas SET activada=0 WHERE activada <> 0";
			Query q = session.getSession().createQuery(strquery);
			q.executeUpdate();
			strquery = "UPDATE Empresas SET activada=1 WHERE idEmpresa = " + empresa.getIdEmpresa();
			q = session.getSession().createQuery(strquery);
			q.executeUpdate();
			getSession().getSession().getTransaction().commit();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaSetActivada()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaSetActivada()", e);
		}
	}

	public  Empresas EmpresaFindById(Object parentFrame, Integer id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Empresas where idEmpresa=" + id);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Empresas) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaFindById()", e);
		}
		return null;
	}
	
	public  Empresas EmpresaFindByNombre(Object parentFrame, String value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Empresas where nombre='" + value + "'");
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Empresas) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaFindByNombre()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaFindByNombre()", e);
		}
		return null;
	}
	
	public  void EmpresaDelete(Object parentFrame, Empresas empresa) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Empresas where idEmpresa=" + empresa.getIdEmpresa());
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.EmpresaDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaDelete()", e);
		}
	}
	
	public List<?> EmpresaGetCuentasBancarias(Object parentFrame) {
		try {
			
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Empresascuentas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa());
			getSession().getSession().getTransaction().commit();
		
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaGetCuentasBancarias()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaGetCuentasBancarias()", e);
		}
		return null;
	}
	
	public String EmpresaGetCuentaContableOfCuentaBanco(Object parentFrame, String cuentabanco) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select cuentaContable From Empresascuentas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.cuentaBancaria='" + cuentabanco + "'");
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (String) q.list().get(0);
			else
				return "";
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EmpresaGetCuentaContableOfCuentaBanco()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EmpresaGetCuentaContableOfCuentaBanco()", e);
		}
		return "";
	}
	
	public  Entradascabecera EntradaFindByCosecheroSemana(Object parentFrame, Integer idCosechero, Integer semana) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Entradascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and idCosechero=" + idCosechero + " and semana=" + semana);
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Entradascabecera) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EntradaFindByCosecheroSemana()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradaFindByCosecheroSemana()", e);
		}
		return null;
	}
	
	public  List<?> EntradasBySemanaCosechero(Object parentFrame, Integer semanadesde, Integer semanahasta, Integer idCosechero) {
		try {
			Query q = null;
			getSession().getSession().beginTransaction();
			if (idCosechero == 0)
				q = getSession().getSession().createQuery("From Viewentradasquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta);
			else
				q = getSession().getSession().createQuery("From Viewentradasquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idCosechero=" + idCosechero + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta);
			getSession().getSession().getTransaction().commit(); 
			
			return q.list();
			
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EntradasBySemanaCosechero()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradasBySemanaCosechero()", e);
		}
		return null;
	}
	
	public  List<?> EntradasCategoriasBySemanaCosechero(Object parentFrame, Integer semanadesde, Integer semanahasta, Integer idCosechero) {
		try {
			Query q = null;
			getSession().getSession().beginTransaction();
			if (idCosechero == 0)
				q = getSession().getSession().createQuery("From Viewentradascategoriasquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta);
			else
				q = getSession().getSession().createQuery("From Viewentradascategoriascosecheroquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idCosechero=" + idCosechero + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta);
			getSession().getSession().getTransaction().commit(); 
			
			return q.list();
			
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EntradasCategoriasBySemanaCosechero()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradasCategoriasBySemanaCosechero()", e);
		}
		return null;
	}
	
	public  Entradascabecera EntradascabeceraFindById(Object parentFrame, EntradascabeceraId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Entradascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idEntrada=" + id.getIdEntrada());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Entradascabecera) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.EntradascabeceraFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradascabeceraFindById()", e);
		}
		return null;
	}
	
	public  void EntradaDelete(Object parentFrame, Entradascabecera entrada) {
		try {
			getSession().getSession().beginTransaction();
			Query qlineas = getSession().getSession().createQuery("Delete From Entradaslineas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idEntrada=" + entrada.getId().getIdEntrada());
			Query q = getSession().getSession().createQuery("Delete From Entradascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idEntrada=" + entrada.getId().getIdEntrada());
			try {
				qlineas.executeUpdate();
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.EntradaDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradaDelete()", e);
		}
	}
	
	public  void EntradaaimportacionDeleteAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Entradasimportacion where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio());
			q.executeUpdate();
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.EntradaaimportacionDeleteAll()", e);
		}
	}
	
	public  Facturascabecera FacturascabeceraFindById(Object parentFrame, FacturascabeceraId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Facturascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idFactura=" + id.getIdFactura());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Facturascabecera) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.FacturascabeceraFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.FacturascabeceraFindById()", e);
		}
		return null;
	}
	
	public  void FacturaDelete(Object parentFrame, Facturascabecera factura) {
		try {
			getSession().getSession().beginTransaction();
			Query qlineas = getSession().getSession().createQuery("Delete From Facturaslineas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idFactura=" + factura.getId().getIdFactura());
			Query q = getSession().getSession().createQuery("Delete From Facturascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idFactura=" + factura.getId().getIdFactura());
			try {
				qlineas.executeUpdate();
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.FacturaDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.FacturaDelete()", e);
		}
	}
	
	public  Facturaspagocabecera FacturaspagocabeceraFindById(Object parentFrame, FacturaspagocabeceraId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Facturaspagocabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idFactura=" + id.getIdFactura());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Facturaspagocabecera) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.FacturaspagocabeceraFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.FacturaspagocabeceraFindById()", e);
		}
		return null;
	}
	
	public  void FacturapagoDelete(Object parentFrame, Facturaspagocabecera factura) {
		try {
			getSession().getSession().beginTransaction();
			Query qlineas = getSession().getSession().createQuery("Delete From Facturaspagolineas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idFactura=" + factura.getId().getIdFactura());
			Query q = getSession().getSession().createQuery("Delete From Facturaspagocabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idFactura=" + factura.getId().getIdFactura());
			try {
				qlineas.executeUpdate();
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.FacturapagoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.FacturapagoDelete()", e);
		}
	}
	
	public  List<?> IdentidadFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Identidades WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindAll()", e);
		}
		return null;
	}
	
	public  List<?> IdentidadFindClientes(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Identidades WHERE Cliente <> 0 and id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindClientes()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindClientes()", e);
		}
		return null;
	}
	
	public  List<?> IdentidadFindProveedores(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Identidades WHERE Proveedor <> 0 and id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindProveedores()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindProveedores()", e);
		}
		return null;
	}
	
	public  Identidades IdentidadFindById(Object parentFrame,  IdentidadesId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Identidades WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.identidad=" + id.getIdentidad());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Identidades) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindById()", e);
		}
		return null;
	}
	
	public  Identidades IdentidadFindByIdentidad(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Identidades WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.identidad=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Identidades) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindByIdentidad()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindByIdentidad()", e);
		}
		return null;
	}
	
	public  List<?> IdentidadFindByNombreIdentidad(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Identidades WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreIdentidad='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.IdentidadFindByNombreIdentidad()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadFindByNombreIdentidad()", e);
		}
		return null;
	}
	
	public  void IdentidadDelete(Object parentFrame, Identidades identidad) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Identidades WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.identidad=" + identidad.getId().getIdentidad());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.IdentidadDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.IdentidadDelete()", e);
		}
	}
	
	public  Precios PrecioFindById(Object parentFrame, PreciosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Precios WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCategoria=" + id.getIdCategoria() + " and id.semana=" + id.getSemana());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Precios) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.PrecioFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PrecioFindById()", e);
		}
		return null;
	}
	
	public  void PrecioDelete(Object parentFrame, Precios precio) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Precios WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idCategoria=" + precio.getId().getIdCategoria() + " and id.semana=" + precio.getId().getSemana());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.PrecioDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PrecioDelete()", e);
		}
	}
	
	public  List<?> PuertoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Puertos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.PuertoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PuertoFindAll()", e);
		}
		return null;
	}
	
	public  Puertos PuertosFindById(Object parentFrame, Integer id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Puertos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idPuerto=" + id);
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Puertos) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.PuertosFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PuertosFindById()", e);
		}
		return null;
	}
	
	public  Puertos PuertoFindByIdPuerto(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Puertos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and idPuerto=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Puertos) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.PuertoFindByIdPuerto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PuertoFindByIdPuerto()", e);
		}
		return null;
	}
	
	public  List<?> PuertoFindByNombrePuerto(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Puertos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombrePuerto='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.PuertoFindByNombrePuerto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.PuertoFindByNombrePuerto()", e);
		}
		return null;
	}
	
	public  List<?> ReceptorFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ReceptorFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorFindAll()", e);
		}
		return null;
	}
	
	public  Receptores ReceptorFindById(Object parentFrame, ReceptoresId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idReceptor=" + id.getIdReceptor());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Receptores) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ReceptorFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorFindById()", e);
		}
		return null;
	}
	
	public  Receptores ReceptorFindByIdReceptor(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idReceptor=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Receptores) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ReceptorFindByIdReceptor()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorFindByIdReceptor()", e);
		}
		return null;
	}
	
	public  List<?> ReceptorFindByNombreReceptor(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombre='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			/*Message.ShowHibernateError(parentFrame, "Entity.ReceptorFindByNombreReceptor()", he);*/
			he.printStackTrace();
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorFindByNombreReceptor()", e);
		}
		return null;
	}
	
	public  void ReceptorDelete(Object parentFrame, Receptores receptor) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idReceptor=" + receptor.getId().getIdReceptor());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.ReceptorDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorDelete()", e);
		}
	}
	
	public  Integer ReceptorGetZona(Object parentFrame, Integer idReceptor) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Select idZona From Receptores WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and idReceptor=" + idReceptor);
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Integer) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ReceptorGetZona()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ReceptorGetZona()", e);
		}
		return null;
	}
	
	public  List<?> TipoGastoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Tiposgasto WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoGastoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoGastoFindAll()", e);
		}
		return null;
	}
	
	public  Tiposgasto TipoGastoFindById(Object parentFrame, TiposgastoId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposgasto where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idTipoGasto=" + id.getIdTipoGasto());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Tiposgasto) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoGastoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoGastoFindById()", e);
		}
		return null;
	}
	
	public  Tiposgasto  TipoGastoFindByIdTipoGasto(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposgasto WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idTipoGasto=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Tiposgasto) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoGastoFindByIdTipoGasto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoGastoFindByIdTipoGasto()", e);
		}
		return null;
	}
	
	public  List<?> TipoGastoFindByDescTipoGasto(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposgasto WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and descTipoGasto='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoGastoFindByDescTipoGasto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoGastoFindByDescTipoGasto()", e);
		}
		return null;
	}
	
	public  void TipoGastoDelete(Object parentFrame, Tiposgasto tiposgasto) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Tiposgasto WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idTipoGasto=" + tiposgasto.getId().getIdTipoGasto());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.TiposgastoDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TiposgastoDelete()", e);
		}
	}
	
	public  List<?> TipoCosteFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Tiposcoste WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoCosteFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoCosteFindAll()", e);
		}
		return null;
	}
	
	public  Tiposcoste TipoCosteFindById(Object parentFrame, TiposcosteId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposgasto where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idTipoCoste=" + id.getIdTipoCoste());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Tiposcoste) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoCosteFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoCosteFindById()", e);
		}
		return null;
	}
	
	public  Tiposcoste  TipoCosteFindByIdTipoCoste(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposcoste WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idTipoCoste=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Tiposcoste) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoCosteFindByIdTipoCoste()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoCosteFindByIdTipoCoste()", e);
		}
		return null;
	}
	
	public  List<?> TipoCosteFindByDescTipoCoste(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Tiposcoste WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and descTipoCoste='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.TipoCosteFindByDescTipoGasto()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TipoCosteFindByDescTipoGasto()", e);
		}
		return null;
	}
	
	public  void TipoCosteDelete(Object parentFrame, Tiposcoste tiposcoste) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Tiposcoste WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idTipoCoste=" + tiposcoste.getId().getIdTipoCoste());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.TiposcosteDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.TiposcosteDelete()", e);
		}
	}

	public  List<?> VehiculoFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Vehiculos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VehiculoFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VehiculoFindAll()", e);
		}
		return null;
	}
	
	public  Vehiculos VehiculoFindById(Object parentFrame, VehiculosId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Vehiculos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idVehiculo=" + id.getIdVehiculo());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Vehiculos) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VehiculoFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VehiculoFindById()", e);
		}
		return null;
	}
	
	public  Vehiculos VehiculoFindByIdVehiculo(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Vehiculos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idVehiculo=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Vehiculos) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VehiculoFindByIdVehiculo()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VehiculoFindByIdVehiculo()", e);
		}
		return null;
	}
	
	public  List<?> VehiculoFindByMatricula(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Vehiculos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and matricula='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VehiculoFindByMatricula()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VehiculoFindByMatricula()", e);
		}
		return null;
	}
	
	public  void VehiculoDelete(Object parentFrame, Vehiculos vehiculo) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Vehiculos WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idVehiculo=" + vehiculo.getId().getIdVehiculo());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.VehiculosDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VehiculosDelete()", e);
		}
	}
	
	public  Ventascabecera VentascabeceraFindById(Object parentFrame, VentascabeceraId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Ventascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + id.getEjercicios().getEjercicio() + " and id.idVenta=" + id.getIdVenta());
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Ventascabecera) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VentascabeceraFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VentascabeceraFindById()", e);
		}
		return null;
	}
	
	public  List<?> VentasBySemanaReceptor(Object parentFrame, Integer semanadesde, Integer semanahasta, Integer idReceptor, Integer tipoMercado) {
		try {
			Query q = null;
			String strfilterReceptor = "";
			String strfilterMercado = "";
			getSession().getSession().beginTransaction();
			if (idReceptor != 0)
				strfilterReceptor =  " and id.idReceptor=" + idReceptor;
			if (tipoMercado == 1)
				strfilterMercado =  " and id.mercadoLocal<>0" ;
			else
				if (tipoMercado == 2)
					strfilterMercado =  " and id.mercadoLocal=0" ;
			
			q = getSession().getSession().createQuery("From Viewventasquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio()  + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta + strfilterReceptor + strfilterMercado);
			
			getSession().getSession().getTransaction().commit(); 
			
			return q.list();
			
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VentasBySemanaReceptor()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VentasBySemanaReceptor()", e);
		}
		return null;
	}
	
	public  List<?> VentasCategoriasBySemanaReceptor(Object parentFrame, Integer semanadesde, Integer semanahasta, Integer idReceptor, Integer tipoMercado) {
		try {
			Query q = null;
			getSession().getSession().beginTransaction();
			String strfilterReceptor = "";
			String strfilterMercado = "";
			if (idReceptor != 0)
				strfilterReceptor =  " and id.idReceptor=" + idReceptor;
			if (tipoMercado == 1)
				strfilterMercado =  " and id.mercadoLocal<>0" ;
			else
				if (tipoMercado == 2)
					strfilterMercado =  " and id.mercadoLocal=0" ;
			
			if ((idReceptor == 0) && (tipoMercado == 0))
				q = getSession().getSession().createQuery("From Viewventascategoriasquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta);
			else
				q = getSession().getSession().createQuery("From Viewventascategoriasreceptorquery where id.empresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.semana>=" + semanadesde + " and id.semana<=" + semanahasta + strfilterReceptor + strfilterMercado);
			getSession().getSession().getTransaction().commit(); 
			
			return q.list();
			
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.VentasCategoriasBySemanaReceptor()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VentasCategoriasBySemanaReceptor()", e);
		}
		return null;
	}
	
	public  void VentaDelete(Object parentFrame, Ventascabecera venta) {
		try {
			getSession().getSession().beginTransaction();
			Query qlineas = getSession().getSession().createQuery("Delete From Ventaslineas where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idVenta=" + venta.getId().getIdVenta());
			Query q = getSession().getSession().createQuery("Delete From Ventascabecera where id.empresas.idEmpresa=" + session.getEmpresa().getIdEmpresa() + " and id.ejercicios.ejercicio=" + session.getEjercicio().getEjercicio() + " and id.idVenta=" + venta.getId().getIdVenta());
			try {
				qlineas.executeUpdate();
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.VentaDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.VentaDelete()", e);
		}
	}
	
	public  List<?> ZonaFindAll(Object parentFrame) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("from Zonas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio());
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ZonaFindAll()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ZonaFindAll()", e);
		}
		return null;
	}
	
	public  Zonas ZonaFindById(Object parentFrame, ZonasId id) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Zonas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idZona=" + id.getIdZona());
			
			getSession().getSession().getTransaction().commit();
			if (q.list().size() > 0) 
				return (Zonas) q.list().get(0);
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ZonaFindById()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ZonaFindById()", e);
		}
		return null;
	}
	
	public  Zonas ZonaFindByIdZona(Object parentFrame, Integer Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Zonas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idZona=" + Value);
			
			getSession().getSession().getTransaction().commit();
			List<?> resultList = q.list();
			if (resultList.size() > 0) 
				return (Zonas) resultList.get(0); 
			else
				return null;
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ZonaFindByIdZona()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ZonaFindByIdZona()", e);
		}
		return null;
	}
	
	public  List<?> ZonaFindByNombreZona(Object parentFrame, String Value) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("From Zonas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and nombreZona='" + Value + "'");
			
			getSession().getSession().getTransaction().commit();
			return q.list();
		} 
		catch (HibernateException he) {
			Message.ShowHibernateError(parentFrame, "Entity.ZonaFindByNombreZona()", he);
		}
		catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ZonaFindByNombreZona()", e);
		}
		return null;
	}
	
	public  void ZonaDelete(Object parentFrame, Zonas zona) {
		try {
			getSession().getSession().beginTransaction();
			Query q = getSession().getSession().createQuery("Delete From Zonas WHERE id.empresas.idEmpresa="
			+ session.getEmpresa().getIdEmpresa()
			+ " and id.ejercicios.ejercicio="
			+ session.getEjercicio().getEjercicio() + " and id.idVehiculo=" + zona.getId().getIdZona());
			
			try {
				q.executeUpdate();
				Message.ShowInformationMessage(parentFrame, "El registro se ha eliminado con �xito.");
			} catch (HibernateException he) {
				if (he.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class)
					Message.ShowInformationMessage(parentFrame, "El registro no se puede eliminar porque se est� usando.");
				else
					Message.ShowHibernateError(parentFrame, "Entity.ZonasDelete()", he);
			}
			getSession().getSession().getTransaction().commit();
		} catch (RuntimeException e) {
			Message.ShowRuntimeError(parentFrame, "Entity.ZonasDelete()", e);
		}
	}

}
