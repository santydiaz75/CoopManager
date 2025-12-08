package entitiesPackage;

/**
 * ConductoresId entity. @author MyEclipse Persistence Tools
 */

public class ConductoresId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Integer idConductor;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public ConductoresId() {
	}

	/** full constructor */
	public ConductoresId(Empresas empresas, Integer idConductor,
			Ejercicios ejercicios) {
		this.empresas = empresas;
		this.idConductor = idConductor;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public Integer getIdConductor() {
		return this.idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
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
		if (!(other instanceof ConductoresId))
			return false;
		ConductoresId castOther = (ConductoresId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getIdConductor() == castOther.getIdConductor()) || (this
						.getIdConductor() != null
						&& castOther.getIdConductor() != null && this
						.getIdConductor().equals(castOther.getIdConductor())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getIdConductor() == null ? 0 : this.getIdConductor()
						.hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		return result;
	}

}