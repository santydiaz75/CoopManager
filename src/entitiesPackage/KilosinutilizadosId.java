package entitiesPackage;

/**
 * KilosinutilizadosId entity. @author MyEclipse Persistence Tools
 */

public class KilosinutilizadosId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idCosechero;
	private Integer semana;
	private Integer idCategoria;


	/** default constructor */
	public KilosinutilizadosId() {
	}

	/** full constructor */
	public KilosinutilizadosId(Empresas empresas, Ejercicios ejercicios,
			Integer idCosechero, Integer semana, Integer idCategoria) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idCosechero = idCosechero;
		this.semana = semana;
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

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
	}

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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof KilosinutilizadosId))
			return false;
		KilosinutilizadosId castOther = (KilosinutilizadosId) other;

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
				&& ((this.getSemana() == castOther.getSemana()) || (this
						.getSemana() != null
						&& castOther.getSemana() != null && this.getSemana()
						.equals(castOther.getSemana())))
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
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
						.hashCode());
		result = 37 * result
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		result = 37
				* result
				+ (getIdCategoria() == null ? 0 : this.getIdCategoria()
						.hashCode());
		return result;
	}

}
