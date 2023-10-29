package entitiesPackage;

/**
 * ZonasId entity. @author MyEclipse Persistence Tools
 */

public class ZonasId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idZona;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public ZonasId() {
	}

	/** full constructor */
	public ZonasId(Integer idZona, Empresas empresas, Ejercicios ejercicios) {
		this.idZona = idZona;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ZonasId))
			return false;
		ZonasId castOther = (ZonasId) other;

		return ((this.getIdZona() == castOther.getIdZona()) || (this
				.getIdZona() != null
				&& castOther.getIdZona() != null && this.getIdZona().equals(
				castOther.getIdZona())))
				&& ((this.getEmpresas() == castOther.getEmpresas()) || (this
						.getEmpresas() != null
						&& castOther.getEmpresas() != null && this
						.getEmpresas().equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdZona() == null ? 0 : this.getIdZona().hashCode());
		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		return result;
	}

}