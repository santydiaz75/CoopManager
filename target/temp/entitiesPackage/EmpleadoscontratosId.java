package entitiesPackage;

import java.util.Date;

/**
 * EmpleadoscontratosId entity. @author MyEclipse Persistence Tools
 */

public class EmpleadoscontratosId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer idEmpleado;
	private Date fechaAlta;

	// Constructors

	/** default constructor */
	public EmpleadoscontratosId() {
	}

	/** full constructor */
	public EmpleadoscontratosId(Empresas empresas, Ejercicios ejercicios,
			Integer idEmpleado, Date fechaAlta) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.idEmpleado = idEmpleado;
		this.fechaAlta = fechaAlta;
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

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmpleadoscontratosId))
			return false;
		EmpleadoscontratosId castOther = (EmpleadoscontratosId) other;

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
				&& ((this.getFechaAlta() == castOther.getFechaAlta()) || (this
						.getFechaAlta() != null
						&& castOther.getFechaAlta() != null && this
						.getFechaAlta().equals(castOther.getFechaAlta())));
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
				+ (getFechaAlta() == null ? 0 : this.getFechaAlta().hashCode());
		return result;
	}

}