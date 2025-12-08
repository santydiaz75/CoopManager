package entitiesPackage;

/**
 * BimestresId entity. @author MyEclipse Persistence Tools
 */

public class BimestresId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer bimestre;

	// Constructors

	/** default constructor */
	public BimestresId() {
	}

	/** full constructor */
	public BimestresId(Empresas empresas, Ejercicios ejercicios,
			Integer bimestre) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.bimestre = bimestre;
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

	public Integer getBimestre() {
		return this.bimestre;
	}

	public void setBimestre(Integer bimestre) {
		this.bimestre = bimestre;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BimestresId))
			return false;
		BimestresId castOther = (BimestresId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getBimestre() == castOther.getBimestre()) || (this
						.getBimestre() != null
						&& castOther.getBimestre() != null && this
						.getBimestre().equals(castOther.getBimestre())));
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
				+ (getBimestre() == null ? 0 : this.getBimestre().hashCode());
		return result;
	}

}