package entitiesPackage;

/**
 * LiquidacioneslineasId entity. @author MyEclipse Persistence Tools
 */

public class LiquidacioneslineasId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer numeroFactura;
	private Integer idCategoria;


	/** default constructor */
	public LiquidacioneslineasId() {
	}

	/** full constructor */
	public LiquidacioneslineasId(Empresas empresas, Ejercicios ejercicios,
			Integer numeroFactura, Integer idCategoria) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.numeroFactura = numeroFactura;
		this.idCategoria = idCategoria;
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

	public Integer getNumeroFactura() {
		return this.numeroFactura;
	}

	public void setNumeroFactura(Integer numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LiquidacioneslineasId))
			return false;
		LiquidacioneslineasId castOther = (LiquidacioneslineasId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getNumeroFactura() == castOther.getNumeroFactura()) || (this
						.getNumeroFactura() != null
						&& castOther.getNumeroFactura() != null && this
						.getNumeroFactura()
						.equals(castOther.getNumeroFactura())))
				&& ((this.getIdCategoria() == castOther.getIdCategoria()) || (this
						.getIdCategoria() != null
						&& castOther.getIdCategoria() != null && this
						.getIdCategoria().equals(castOther.getIdCategoria())));
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
				+ (getNumeroFactura() == null ? 0 : this.getNumeroFactura()
						.hashCode());
		result = 37
				* result
				+ (getIdCategoria() == null ? 0 : this.getIdCategoria()
						.hashCode());
		return result;
	}

}
