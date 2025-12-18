package entitiesPackage;

/**
 * ConceptosId entity. @author MyEclipse Persistence Tools
 */

public class ConceptosId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer concepto;


	/** default constructor */
	public ConceptosId() {
	}

	/** full constructor */
	public ConceptosId(Empresas empresas, Ejercicios ejercicios,
			Integer concepto) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.concepto = concepto;
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

	public Integer getConcepto() {
		return this.concepto;
	}

	public void setConcepto(Integer concepto) {
		this.concepto = concepto;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ConceptosId))
			return false;
		ConceptosId castOther = (ConceptosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getConcepto() == castOther.getConcepto()) || (this
						.getConcepto() != null
						&& castOther.getConcepto() != null && this
						.getConcepto().equals(castOther.getConcepto())));
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
				+ (getConcepto() == null ? 0 : this.getConcepto().hashCode());
		return result;
	}

}
