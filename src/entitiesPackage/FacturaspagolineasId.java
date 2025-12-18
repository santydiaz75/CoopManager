package entitiesPackage;

/**
 * FacturaspagolineasId entity. @author MyEclipse Persistence Tools
 */

public class FacturaspagolineasId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idFactura;
	private Integer linea;


	/** default constructor */
	public FacturaspagolineasId() {
	}

	/** full constructor */
	public FacturaspagolineasId(Empresas empresas, Ejercicios ejercicios,
			Integer idFactura, Integer linea) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idFactura = idFactura;
		this.linea = linea;
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

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Integer getLinea() {
		return this.linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FacturaspagolineasId))
			return false;
		FacturaspagolineasId castOther = (FacturaspagolineasId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdFactura() == castOther.getIdFactura()) || (this
						.getIdFactura() != null
						&& castOther.getIdFactura() != null && this
						.getIdFactura().equals(castOther.getIdFactura())))
				&& ((this.getLinea() == castOther.getLinea()) || (this
						.getLinea() != null
						&& castOther.getLinea() != null && this.getLinea()
						.equals(castOther.getLinea())));
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
				+ (getIdFactura() == null ? 0 : this.getIdFactura().hashCode());
		result = 37 * result
				+ (getLinea() == null ? 0 : this.getLinea().hashCode());
		return result;
	}

}
