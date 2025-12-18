package entitiesPackage;

/**
 * EntradaslineasId entity. @author MyEclipse Persistence Tools
 */

public class EntradaslineasId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEntrada;
	private Integer idCategoria;
	private Empresas empresas;
	private Ejercicios ejercicios;


	/** default constructor */
	public EntradaslineasId() {
	}

	/** full constructor */
	public EntradaslineasId(Integer idEntrada, Integer idCategoria,
			Empresas empresas, Ejercicios ejercicios) {
		this.idEntrada = idEntrada;
		this.idCategoria = idCategoria;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}


	public Integer getIdEntrada() {
		return this.idEntrada;
	}

	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
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
		if (!(other instanceof EntradaslineasId))
			return false;
		EntradaslineasId castOther = (EntradaslineasId) other;

		return ((this.getIdEntrada() == castOther.getIdEntrada()) || (this
				.getIdEntrada() != null
				&& castOther.getIdEntrada() != null && this.getIdEntrada()
				.equals(castOther.getIdEntrada())))
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
				+ (getIdEntrada() == null ? 0 : this.getIdEntrada().hashCode());
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
