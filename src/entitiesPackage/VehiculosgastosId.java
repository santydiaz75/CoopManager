package entitiesPackage;

/**
 * VehiculosgastosId entity. @author MyEclipse Persistence Tools
 */

public class VehiculosgastosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idGasto;
	private Integer idVehiculo;

	// Constructors

	/** default constructor */
	public VehiculosgastosId() {
	}

	/** full constructor */
	public VehiculosgastosId(Empresas empresas, Ejercicios ejercicios,
			Integer idGasto, Integer idVehiculo) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idGasto = idGasto;
		this.idVehiculo = idVehiculo;
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

	public Integer getIdGasto() {
		return this.idGasto;
	}

	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}

	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VehiculosgastosId))
			return false;
		VehiculosgastosId castOther = (VehiculosgastosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdGasto() == castOther.getIdGasto()) || (this
						.getIdGasto() != null
						&& castOther.getIdGasto() != null && this.getIdGasto()
						.equals(castOther.getIdGasto())))
				&& ((this.getIdVehiculo() == castOther.getIdVehiculo()) || (this
						.getIdVehiculo() != null
						&& castOther.getIdVehiculo() != null && this
						.getIdVehiculo().equals(castOther.getIdVehiculo())));
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
				+ (getIdGasto() == null ? 0 : this.getIdGasto().hashCode());
		result = 37
				* result
				+ (getIdVehiculo() == null ? 0 : this.getIdVehiculo()
						.hashCode());
		return result;
	}

}