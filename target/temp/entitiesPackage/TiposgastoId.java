package entitiesPackage;

/**
 * TiposgastoId entity. @author MyEclipse Persistence Tools
 */

public class TiposgastoId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTipoGasto;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public TiposgastoId() {
	}

	/** full constructor */
	public TiposgastoId(Integer idTipoGasto, Empresas empresas,
			Ejercicios ejercicios) {
		this.idTipoGasto = idTipoGasto;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Integer getIdTipoGasto() {
		return this.idTipoGasto;
	}

	public void setIdTipoGasto(Integer idTipoGasto) {
		this.idTipoGasto = idTipoGasto;
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
		if (!(other instanceof TiposgastoId))
			return false;
		TiposgastoId castOther = (TiposgastoId) other;

		return ((this.getIdTipoGasto() == castOther.getIdTipoGasto()) || (this
				.getIdTipoGasto() != null
				&& castOther.getIdTipoGasto() != null && this.getIdTipoGasto()
				.equals(castOther.getIdTipoGasto())))
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
				+ (getIdTipoGasto() == null ? 0 : this.getIdTipoGasto()
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