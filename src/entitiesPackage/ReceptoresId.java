package entitiesPackage;

/**
 * ReceptoresId entity. @author MyEclipse Persistence Tools
 */

public class ReceptoresId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idReceptor;


	/** default constructor */
	public ReceptoresId() {
	}

	/** full constructor */
	public ReceptoresId(Empresas empresas, Ejercicios ejercicios,
			Integer idReceptor) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idReceptor = idReceptor;
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

	public Integer getIdReceptor() {
		return this.idReceptor;
	}

	public void setIdReceptor(Integer idReceptor) {
		this.idReceptor = idReceptor;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReceptoresId))
			return false;
		ReceptoresId castOther = (ReceptoresId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdReceptor() == castOther.getIdReceptor()) || (this
						.getIdReceptor() != null
						&& castOther.getIdReceptor() != null && this
						.getIdReceptor().equals(castOther.getIdReceptor())));
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
				+ (getIdReceptor() == null ? 0 : this.getIdReceptor()
						.hashCode());
		return result;
	}

}
