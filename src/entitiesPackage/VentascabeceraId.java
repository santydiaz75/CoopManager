package entitiesPackage;

/**
 * VentascabeceraId entity. @author MyEclipse Persistence Tools
 */

public class VentascabeceraId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idVenta;


	/** default constructor */
	public VentascabeceraId() {
	}

	/** full constructor */
	public VentascabeceraId(Empresas empresas, Ejercicios ejercicios,
			Integer idVenta) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idVenta = idVenta;
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

	public Integer getIdVenta() {
		return this.idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VentascabeceraId))
			return false;
		VentascabeceraId castOther = (VentascabeceraId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdVenta() == castOther.getIdVenta()) || (this
						.getIdVenta() != null
						&& castOther.getIdVenta() != null && this.getIdVenta()
						.equals(castOther.getIdVenta())));
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
				+ (getIdVenta() == null ? 0 : this.getIdVenta().hashCode());
		return result;
	}

}
