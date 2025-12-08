package entitiesPackage;

/**
 * Entradasimportacion entity. @author MyEclipse Persistence Tools
 */

public class Entradasimportacion implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntradasimportacionId id;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public Entradasimportacion() {
	}

	/** full constructor */
	public Entradasimportacion(EntradasimportacionId id, Empresas empresas,
			Ejercicios ejercicios) {
		this.id = id;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public EntradasimportacionId getId() {
		return this.id;
	}

	public void setId(EntradasimportacionId id) {
		this.id = id;
	}

	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public Ejercicios getEjercicios() {
		return this.ejercicios;
	}

	public void setEjercicios(Ejercicios ejercicios) {
		this.ejercicios = ejercicios;
	}

}