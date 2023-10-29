package sessionPackage;

import org.hibernate.Session;

import entitiesPackage.Ejercicios;
import entitiesPackage.Empresas;

public class MySession {

	private Ejercicios ejercicio;
	private Empresas empresa;
	private Session session; 

	public MySession(Ejercicios ejercicio, Empresas empresa) {
		this.setEjercicio(ejercicio);
		this.setEmpresa(empresa);
	}

	public void setEjercicio(Ejercicios ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Ejercicios getEjercicio() {
		return ejercicio;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setSession(Session session) {
		this.session = HibernateSessionFactory.getSessionFactory().openSession();
	}

	public Session getSession() {
		session = HibernateSessionFactory.getSession(); 
		return session;
	}

}
