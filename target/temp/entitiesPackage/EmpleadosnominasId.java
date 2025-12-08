package entitiesPackage;

/**
 * EmpleadosnominasId entity. @author MyEclipse Persistence Tools
 */

public class EmpleadosnominasId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idEmpleado;
	private Integer mes;

	// Constructors

	/** default constructor */
	public EmpleadosnominasId() {
	}

	/** full constructor */
	public EmpleadosnominasId(Empresas empresas, Ejercicios ejercicios,
			Integer idEmpleado, Integer mes) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idEmpleado = idEmpleado;
		this.mes = mes;
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

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmpleadosnominasId))
			return false;
		EmpleadosnominasId castOther = (EmpleadosnominasId) other;

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
						.getIdEmpleado().equals(castOther.getIdEmpleado())))
				&& ((this.getMes() == castOther.getMes()) || (this.getMes() != null
						&& castOther.getMes() != null && this.getMes().equals(
						castOther.getMes())));
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
		result = 37 * result
				+ (getMes() == null ? 0 : this.getMes().hashCode());
		return result;
	}

}