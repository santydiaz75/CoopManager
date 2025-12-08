package entitiesPackage;

/**
 * PagosId entity. @author MyEclipse Persistence Tools
 */

public class PagosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idProveedor;
	private Integer pago;

	// Constructors

	/** default constructor */
	public PagosId() {
	}

	/** full constructor */
	public PagosId(Empresas empresas, Ejercicios ejercicios,
			Integer idProveedor, Integer pago) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idProveedor = idProveedor;
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

	public Integer getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
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
		if (!(other instanceof PagosId))
			return false;
		PagosId castOther = (PagosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdProveedor() == castOther.getIdProveedor()) || (this
						.getIdProveedor() != null
						&& castOther.getIdProveedor() != null && this
						.getIdProveedor().equals(castOther.getIdProveedor())))
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
				+ (getIdProveedor() == null ? 0 : this.getIdProveedor()
						.hashCode());
		result = 37 * result
				+ (getPago() == null ? 0 : this.getPago().hashCode());
		return result;
	}

}