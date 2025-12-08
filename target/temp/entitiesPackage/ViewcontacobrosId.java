package entitiesPackage;

import java.util.Date;

/**
 * ViewcontacobrosId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontacobrosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Date fechaCobro;
	private String concepto;
	private Long identidad;
	private String nombre;
	private String cuentaContableCliente;
	private String cuentaBancaria;
	private String nombreBanco;
	private String cuentaContableBanco;
	private Double importeCobro;

	// Constructors

	/** default constructor */
	public ViewcontacobrosId() {
	}

	/** minimal constructor */
	public ViewcontacobrosId(Integer empresa, Integer ejercicio, Long identidad) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.identidad = identidad;
	}

	/** full constructor */
	public ViewcontacobrosId(Integer empresa, Integer ejercicio,
			Date fechaCobro, String concepto, Long identidad,
			String nombre, String cuentaContableCliente, String cuentaBancaria,
			String nombreBanco, String cuentaContableBanco, Double importeCobro) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.fechaCobro = fechaCobro;
		this.concepto = concepto;
		this.identidad = identidad;
		this.nombre = nombre;
		this.cuentaContableCliente = cuentaContableCliente;
		this.cuentaBancaria = cuentaBancaria;
		this.nombreBanco = nombreBanco;
		this.cuentaContableBanco = cuentaContableBanco;
		this.importeCobro = importeCobro;
	}

	// Property accessors

	public Integer getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getFechaCobro() {
		return this.fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Long getIdentidad() {
		return this.identidad;
	}

	public void setIdentidad(Long identidad) {
		this.identidad = identidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuentaContableCliente() {
		return this.cuentaContableCliente;
	}

	public void setCuentaContableCliente(String cuentaContableCliente) {
		this.cuentaContableCliente = cuentaContableCliente;
	}

	public String getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public String getNombreBanco() {
		return this.nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getCuentaContableBanco() {
		return this.cuentaContableBanco;
	}

	public void setCuentaContableBanco(String cuentaContableBanco) {
		this.cuentaContableBanco = cuentaContableBanco;
	}

	public Double getImporteCobro() {
		return this.importeCobro;
	}

	public void setImporteCobro(Double importeCobro) {
		this.importeCobro = importeCobro;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewcontacobrosId))
			return false;
		ViewcontacobrosId castOther = (ViewcontacobrosId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getFechaCobro() == castOther.getFechaCobro()) || (this
						.getFechaCobro() != null
						&& castOther.getFechaCobro() != null && this
						.getFechaCobro().equals(castOther.getFechaCobro())))
				&& ((this.getConcepto() == castOther.getConcepto()) || (this
						.getConcepto() != null
						&& castOther.getConcepto() != null && this
						.getConcepto().equals(castOther.getConcepto())))
				&& ((this.getIdentidad() == castOther.getIdentidad()) || (this
						.getIdentidad() != null
						&& castOther.getIdentidad() != null && this
						.getIdentidad().equals(castOther.getIdentidad())))
				&& ((this.getNombre() == castOther.getNombre()) || (this
						.getNombre() != null
						&& castOther.getNombre() != null && this.getNombre()
						.equals(castOther.getNombre())))
				&& ((this.getCuentaContableCliente() == castOther
						.getCuentaContableCliente()) || (this
						.getCuentaContableCliente() != null
						&& castOther.getCuentaContableCliente() != null && this
						.getCuentaContableCliente().equals(
								castOther.getCuentaContableCliente())))
				&& ((this.getCuentaBancaria() == castOther.getCuentaBancaria()) || (this
						.getCuentaBancaria() != null
						&& castOther.getCuentaBancaria() != null && this
						.getCuentaBancaria().equals(
								castOther.getCuentaBancaria())))
				&& ((this.getNombreBanco() == castOther.getNombreBanco()) || (this
						.getNombreBanco() != null
						&& castOther.getNombreBanco() != null && this
						.getNombreBanco().equals(castOther.getNombreBanco())))
				&& ((this.getCuentaContableBanco() == castOther
						.getCuentaContableBanco()) || (this
						.getCuentaContableBanco() != null
						&& castOther.getCuentaContableBanco() != null && this
						.getCuentaContableBanco().equals(
								castOther.getCuentaContableBanco())))
				&& ((this.getImporteCobro() == castOther.getImporteCobro()) || (this
						.getImporteCobro() != null
						&& castOther.getImporteCobro() != null && this
						.getImporteCobro().equals(castOther.getImporteCobro())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37
				* result
				+ (getFechaCobro() == null ? 0 : this.getFechaCobro()
						.hashCode());
		result = 37 * result
				+ (getConcepto() == null ? 0 : this.getConcepto().hashCode());
		result = 37 * result
				+ (getIdentidad() == null ? 0 : this.getIdentidad().hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		result = 37
				* result
				+ (getCuentaContableCliente() == null ? 0 : this
						.getCuentaContableCliente().hashCode());
		result = 37
				* result
				+ (getCuentaBancaria() == null ? 0 : this.getCuentaBancaria()
						.hashCode());
		result = 37
				* result
				+ (getNombreBanco() == null ? 0 : this.getNombreBanco()
						.hashCode());
		result = 37
				* result
				+ (getCuentaContableBanco() == null ? 0 : this
						.getCuentaContableBanco().hashCode());
		result = 37
				* result
				+ (getImporteCobro() == null ? 0 : this.getImporteCobro()
						.hashCode());
		return result;
	}

}