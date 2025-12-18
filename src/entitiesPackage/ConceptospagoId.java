package entitiesPackage;

/**
 * ConceptospagoId entity. @author MyEclipse Persistence Tools
 */

public class ConceptospagoId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer conceptoPago;
	private Empresas empresas;
	private Ejercicios ejercicios;


	/** default constructor */
	public ConceptospagoId() {
	}

	/** full constructor */
	public ConceptospagoId(Integer conceptoPago, Empresas empresas,
			Ejercicios ejercicios) {
		this.conceptoPago = conceptoPago;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}


	public Integer getConceptoPago() {
		return this.conceptoPago;
	}

	public void setConceptoPago(Integer conceptoPago) {
		this.conceptoPago = conceptoPago;
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
		if (!(other instanceof ConceptospagoId))
			return false;
		ConceptospagoId castOther = (ConceptospagoId) other;

		return ((this.getConceptoPago() == castOther.getConceptoPago()) || (this
				.getConceptoPago() != null
				&& castOther.getConceptoPago() != null && this
				.getConceptoPago().equals(castOther.getConceptoPago())))
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
				+ (getConceptoPago() == null ? 0 : this.getConceptoPago()
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
