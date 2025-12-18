package entitiesPackage;

/**
 * ViewcontabonificacionesId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontabonificacionesId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Integer mes;
	private String cuentaBancaria;
	private String nombreBanco;
	private String cuentaContable;
	private Float importeBonificacion;
	private String nombre;


	/** default constructor */
	public ViewcontabonificacionesId() {
	}

	/** minimal constructor */
	public ViewcontabonificacionesId(Integer empresa, Integer ejercicio,
			Integer mes, String cuentaBancaria, Float importeBonificacion) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaBancaria = cuentaBancaria;
		this.importeBonificacion = importeBonificacion;
	}

	/** full constructor */
	public ViewcontabonificacionesId(Integer empresa, Integer ejercicio,
			Integer mes, String cuentaBancaria, String nombreBanco,
			String cuentaContable, Float importeBonificacion, String nombre) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaBancaria = cuentaBancaria;
		this.nombreBanco = nombreBanco;
		this.cuentaContable = cuentaContable;
		this.importeBonificacion = importeBonificacion;
		this.nombre = nombre;
	}


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

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
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

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Float getImporteBonificacion() {
		return this.importeBonificacion;
	}

	public void setImporteBonificacion(Float importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewcontabonificacionesId))
			return false;
		ViewcontabonificacionesId castOther = (ViewcontabonificacionesId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getMes() == castOther.getMes()) || (this.getMes() != null
						&& castOther.getMes() != null && this.getMes().equals(
						castOther.getMes())))
				&& ((this.getCuentaBancaria() == castOther.getCuentaBancaria()) || (this
						.getCuentaBancaria() != null
						&& castOther.getCuentaBancaria() != null && this
						.getCuentaBancaria().equals(
								castOther.getCuentaBancaria())))
				&& ((this.getNombreBanco() == castOther.getNombreBanco()) || (this
						.getNombreBanco() != null
						&& castOther.getNombreBanco() != null && this
						.getNombreBanco().equals(castOther.getNombreBanco())))
				&& ((this.getCuentaContable() == castOther.getCuentaContable()) || (this
						.getCuentaContable() != null
						&& castOther.getCuentaContable() != null && this
						.getCuentaContable().equals(
								castOther.getCuentaContable())))
				&& ((this.getImporteBonificacion() == castOther
						.getImporteBonificacion()) || (this
						.getImporteBonificacion() != null
						&& castOther.getImporteBonificacion() != null && this
						.getImporteBonificacion().equals(
								castOther.getImporteBonificacion())))
				&& ((this.getNombre() == castOther.getNombre()) || (this
						.getNombre() != null
						&& castOther.getNombre() != null && this.getNombre()
						.equals(castOther.getNombre())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result
				+ (getMes() == null ? 0 : this.getMes().hashCode());
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
				+ (getCuentaContable() == null ? 0 : this.getCuentaContable()
						.hashCode());
		result = 37
				* result
				+ (getImporteBonificacion() == null ? 0 : this
						.getImporteBonificacion().hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		return result;
	}

}
