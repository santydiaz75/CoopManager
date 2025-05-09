package sessionPackage;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate.encryptor.HibernatePBEEncryptorRegistry;

/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

    /** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private  static Configuration configuration = new Configuration();    
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;

	public static String getConnectionURL() {
		String server = WindowsRegistry.readRegistry("HKLM\\Software\\GestCoop", "Server");
		if (server == null)
		      server = WindowsRegistry.readRegistry("HKLM\\Software\\Wow6432Node\\GestCoop", "Server");
		if (server == null)
			server = "127.0.0.1";
		String dataBase = WindowsRegistry.readRegistry("HKLM\\Software\\GestCoop", "DataBase");
		if (dataBase == null)
	       dataBase = WindowsRegistry.readRegistry("HKLM\\Software\\Wow6432Node\\GestCoop", "DataBase");
		if (dataBase == null)
			dataBase = "coopmanagerdb";
		return "jdbc:mysql://" + server + "/" + dataBase;
	}
	
	static {
    	try {
			configuration.configure(configFile);
			configuration.setProperty("hibernate.connection.url", getConnectionURL());
			PBEStringEncryptor encryptor =
			      new StandardPBEStringEncryptor();
			// La clave se puede obtener consultar en web,
			// en una variable del programa o de entorno...
			encryptor.setPassword("santi");
			HibernatePBEEncryptorRegistry registry =
			      HibernatePBEEncryptorRegistry.getInstance();

			// Asignar el mismo nombre que en hibernate-mapping
			registry.registerPBEStringEncryptor(
			      "hibernateEncryptor", encryptor);
			
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
    private HibernateSessionFactory() {
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}
        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			
			PBEStringEncryptor encryptor =
			      new StandardPBEStringEncryptor();
			// La clave se puede obtener consultar en web,
			// en una variable del programa o de entorno...
			encryptor.setPassword("santi");
			HibernatePBEEncryptorRegistry registry =
			      HibernatePBEEncryptorRegistry.getInstance();

			// Asignar el mismo nombre que en hibernate-mapping
			registry.registerPBEStringEncryptor(
			      "hibernateEncryptor", encryptor);
			
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}

}