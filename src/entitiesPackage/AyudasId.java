package entitiesPackage;

/**
 * AyudasId entity. @author MyEclipse Persistence Tools
 */

public class AyudasId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idCosechero;
	private Empresas empresas;
	private Ejercicios ejercicios;


	/** default constructor */
	public AyudasId() {
	}

	/** full constructor */
	public AyudasId(Integer idCosechero, Empresas empresas,
			Ejercicios ejercicios) {
		this.idCosechero = idCosechero;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}


	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
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
		if (!(other instanceof AyudasId))
			return false;
		AyudasId castOther = (AyudasId) other;

		return ((this.getIdCosechero() == castOther.getIdCosechero()) || (this
				.getIdCosechero() != null
				&& castOther.getIdCosechero() != null && this.getIdCosechero()
				.equals(castOther.getIdCosechero())))
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

		result = 37
				* result
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
						.hashCode());
		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		return result;
	}

}
