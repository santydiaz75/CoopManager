package entitiesPackage;

/**
 * PreciosId entity. @author MyEclipse Persistence Tools
 */

public class PreciosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer semana;
	private Integer idCategoria;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public PreciosId() {
	}

	/** full constructor */
	public PreciosId(Integer semana, Integer idCategoria, Empresas empresas,
			Ejercicios ejercicios) {
		this.semana = semana;
		this.idCategoria = idCategoria;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PreciosId))
			return false;
		PreciosId castOther = (PreciosId) other;

		return ((this.getSemana() == castOther.getSemana()) || (this
				.getSemana() != null
				&& castOther.getSemana() != null && this.getSemana().equals(
				castOther.getSemana())))
				&& ((this.getIdCategoria() == castOther.getIdCategoria()) || (this
						.getIdCategoria() != null
						&& castOther.getIdCategoria() != null && this
						.getIdCategoria().equals(castOther.getIdCategoria())))
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

		result = 37 * result
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		result = 37
				* result
				+ (getIdCategoria() == null ? 0 : this.getIdCategoria()
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