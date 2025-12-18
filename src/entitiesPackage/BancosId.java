package entitiesPackage;

/**
 * BancosId entity. @author MyEclipse Persistence Tools
 */

public class BancosId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idBanco;
	private String idSucursal;
	private Empresas empresas;
	private Ejercicios ejercicios;


	/** default constructor */
	public BancosId() {
	}

	/** full constructor */
	public BancosId(String idBanco, String idSucursal, Empresas empresas,
			Ejercicios ejercicios) {
		this.idBanco = idBanco;
		this.idSucursal = idSucursal;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}


	public String getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
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
		if (!(other instanceof BancosId))
			return false;
		BancosId castOther = (BancosId) other;

		return ((this.getIdBanco() == castOther.getIdBanco()) || (this
				.getIdBanco() != null
				&& castOther.getIdBanco() != null && this.getIdBanco().equals(
				castOther.getIdBanco())))
				&& ((this.getIdSucursal() == castOther.getIdSucursal()) || (this
						.getIdSucursal() != null
						&& castOther.getIdSucursal() != null && this
						.getIdSucursal().equals(castOther.getIdSucursal())))
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
				+ (getIdBanco() == null ? 0 : this.getIdBanco().hashCode());
		result = 37
				* result
				+ (getIdSucursal() == null ? 0 : this.getIdSucursal()
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
