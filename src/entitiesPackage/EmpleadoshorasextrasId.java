package entitiesPackage;

/**
 * EmpleadoshorasextrasId entity. @author MyEclipse Persistence Tools
 */

public class EmpleadoshorasextrasId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idEmpleado;
	private Integer semana;


	/** default constructor */
	public EmpleadoshorasextrasId() {
	}

	/** full constructor */
	public EmpleadoshorasextrasId(Empresas empresas, Ejercicios ejercicios,
			Integer idEmpleado, Integer semana) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idEmpleado = idEmpleado;
		this.semana = semana;
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

	public Integer getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmpleadoshorasextrasId))
			return false;
		EmpleadoshorasextrasId castOther = (EmpleadoshorasextrasId) other;

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
				&& ((this.getSemana() == castOther.getSemana()) || (this
						.getSemana() != null
						&& castOther.getSemana() != null && this.getSemana()
						.equals(castOther.getSemana())));
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
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		return result;
	}

}
