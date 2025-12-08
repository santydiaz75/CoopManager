package entitiesPackage;

/**
 * EmpleadosId entity. @author MyEclipse Persistence Tools
 */

public class EmpleadosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idEmpleado;

	// Constructors

	/** default constructor */
	public EmpleadosId() {
	}

	/** full constructor */
	public EmpleadosId(Empresas empresas, Ejercicios ejercicios,
			Integer idEmpleado) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idEmpleado = idEmpleado;
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

	public Integer getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmpleadosId))
			return false;
		EmpleadosId castOther = (EmpleadosId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getIdEmpleado() == castOther.getIdEmpleado()) || (this
						.getIdEmpleado() != null
						&& castOther.getIdEmpleado() != null && this
						.getIdEmpleado().equals(castOther.getIdEmpleado())));
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
				+ (getIdEmpleado() == null ? 0 : this.getIdEmpleado()
						.hashCode());
		return result;
	}

}