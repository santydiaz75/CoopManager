package entitiesPackage;

/**
 * TiposcosteId entity. @author MyEclipse Persistence Tools
 */

public class TiposcosteId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTipoCoste;
	private Empresas empresas;
	private Ejercicios ejercicios;


	/** default constructor */
	public TiposcosteId() {
	}

	/** full constructor */
	public TiposcosteId(Integer idTipoCoste, Empresas empresas,
			Ejercicios ejercicios) {
		this.idTipoCoste = idTipoCoste;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}


	public Integer getIdTipoCoste() {
		return this.idTipoCoste;
	}

	public void setIdTipoCoste(Integer idTipoCoste) {
		this.idTipoCoste = idTipoCoste;
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
		if (!(other instanceof TiposcosteId))
			return false;
		TiposcosteId castOther = (TiposcosteId) other;

		return ((this.getIdTipoCoste() == castOther.getIdTipoCoste()) || (this
				.getIdTipoCoste() != null
				&& castOther.getIdTipoCoste() != null && this.getIdTipoCoste()
				.equals(castOther.getIdTipoCoste())))
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
				+ (getIdTipoCoste() == null ? 0 : this.getIdTipoCoste()
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
