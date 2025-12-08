package entitiesPackage;

/**
 * IdentidadesId entity. @author MyEclipse Persistence Tools
 */

public class IdentidadesId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer identidad;

	// Constructors

	/** default constructor */
	public IdentidadesId() {
	}

	/** full constructor */
	public IdentidadesId(Empresas empresas, Ejercicios ejercicios,
			Integer identidad) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.identidad = identidad;
	}

	// Property accessors

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

	public Integer getIdentidad() {
		return this.identidad;
	}

	public void setIdentidad(Integer identidad) {
		this.identidad = identidad;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IdentidadesId))
			return false;
		IdentidadesId castOther = (IdentidadesId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdentidad() == castOther.getIdentidad()) || (this
						.getIdentidad() != null
						&& castOther.getIdentidad() != null && this
						.getIdentidad().equals(castOther.getIdentidad())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		result = 37 * result
				+ (getIdentidad() == null ? 0 : this.getIdentidad().hashCode());
		return result;
	}

}