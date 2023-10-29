package entitiesPackage;

/**
 * VehiculosId entity. @author MyEclipse Persistence Tools
 */

public class VehiculosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idVehiculo;
	private Empresas empresas;
	private Ejercicios ejercicios;

	// Constructors

	/** default constructor */
	public VehiculosId() {
	}

	/** full constructor */
	public VehiculosId(Integer idVehiculo, Empresas empresas,
			Ejercicios ejercicios) {
		this.idVehiculo = idVehiculo;
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	// Property accessors

	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
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
		if (!(other instanceof VehiculosId))
			return false;
		VehiculosId castOther = (VehiculosId) other;

		return ((this.getIdVehiculo() == castOther.getIdVehiculo()) || (this
				.getIdVehiculo() != null
				&& castOther.getIdVehiculo() != null && this.getIdVehiculo()
				.equals(castOther.getIdVehiculo())))
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
				+ (getIdVehiculo() == null ? 0 : this.getIdVehiculo()
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