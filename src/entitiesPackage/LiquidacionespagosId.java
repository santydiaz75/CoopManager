package entitiesPackage;

/**
 * LiquidacionespagosId entity. @author MyEclipse Persistence Tools
 */

public class LiquidacionespagosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idCosechero;
	private Integer pago;

	// Constructors

	/** default constructor */
	public LiquidacionespagosId() {
	}

	/** full constructor */
	public LiquidacionespagosId(Empresas empresas, Ejercicios ejercicios,
			Integer idCosechero, Integer pago) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idCosechero = idCosechero;
		this.pago = pago;
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

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
	}

	public Integer getPago() {
		return this.pago;
	}

	public void setPago(Integer pago) {
		this.pago = pago;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LiquidacionespagosId))
			return false;
		LiquidacionespagosId castOther = (LiquidacionespagosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdCosechero() == castOther.getIdCosechero()) || (this
						.getIdCosechero() != null
						&& castOther.getIdCosechero() != null && this
						.getIdCosechero().equals(castOther.getIdCosechero())))
				&& ((this.getPago() == castOther.getPago()) || (this.getPago() != null
						&& castOther.getPago() != null && this.getPago()
						.equals(castOther.getPago())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		result = 37
				* result
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
						.hashCode());
		result = 37 * result
				+ (getPago() == null ? 0 : this.getPago().hashCode());
		return result;
	}

}