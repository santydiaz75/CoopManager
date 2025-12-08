package entitiesPackage;

/**
 * BarcosId entity. @author MyEclipse Persistence Tools
 */

public class BarcosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Integer idBarco;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public BarcosId() {
	}

	/** full constructor */
	public BarcosId(Empresas empresas, Integer idBarco, Ejercicios ejercicios) {
		this.empresas = empresas;
		this.idBarco = idBarco;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public Integer getIdBarco() {
		return this.idBarco;
	}

	public void setIdBarco(Integer idBarco) {
		this.idBarco = idBarco;
	}

	public Ejercicios getEjercicios() {
		return this.ejercicios;
	}

	public void setEjercicios(Ejercicios ejercicios) {
		this.ejercicios = ejercicios;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BarcosId))
			return false;
		BarcosId castOther = (BarcosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getIdBarco() == castOther.getIdBarco()) || (this
						.getIdBarco() != null
						&& castOther.getIdBarco() != null && this.getIdBarco()
						.equals(castOther.getIdBarco())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37 * result
				+ (getIdBarco() == null ? 0 : this.getIdBarco().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		return result;
	}

}