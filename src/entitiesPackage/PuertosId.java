package entitiesPackage;

/**
 * PuertosId entity. @author MyEclipse Persistence Tools
 */

public class PuertosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPuerto;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public PuertosId() {
	}

	/** full constructor */
	public PuertosId(Integer idPuerto, Empresas empresas, Ejercicios ejercicios) {
		this.idPuerto = idPuerto;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Integer getIdPuerto() {
		return this.idPuerto;
	}

	public void setIdPuerto(Integer idPuerto) {
		this.idPuerto = idPuerto;
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
		if (!(other instanceof PuertosId))
			return false;
		PuertosId castOther = (PuertosId) other;

		return ((this.getIdPuerto() == castOther.getIdPuerto()) || (this
				.getIdPuerto() != null
				&& castOther.getIdPuerto() != null && this.getIdPuerto()
				.equals(castOther.getIdPuerto())))
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
				+ (getIdPuerto() == null ? 0 : this.getIdPuerto().hashCode());
		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		return result;
	}

}