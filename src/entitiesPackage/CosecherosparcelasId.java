package entitiesPackage;

/**
 * CosecherosparcelasId entity. @author MyEclipse Persistence Tools
 */

public class CosecherosparcelasId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idCosechero;
	private Integer idParcela;

	// Constructors

	/** default constructor */
	public CosecherosparcelasId() {
	}

	/** full constructor */
	public CosecherosparcelasId(Empresas empresas, Ejercicios ejercicios,
			Integer idCosechero, Integer idParcela) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idCosechero = idCosechero;
		this.idParcela = idParcela;
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

	public Integer getIdParcela() {
		return this.idParcela;
	}

	public void setIdParcela(Integer idParcela) {
		this.idParcela = idParcela;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CosecherosparcelasId))
			return false;
		CosecherosparcelasId castOther = (CosecherosparcelasId) other;

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
				&& ((this.getIdParcela() == castOther.getIdParcela()) || (this
						.getIdParcela() != null
						&& castOther.getIdParcela() != null && this
						.getIdParcela().equals(castOther.getIdParcela())));
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
				+ (getIdParcela() == null ? 0 : this.getIdParcela().hashCode());
		return result;
	}

}