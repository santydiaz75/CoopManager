package entitiesPackage;

/**
 * ViewcontaindemnizacionesId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontaindemnizacionesId implements java.io.Serializable {


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
	private Float importeLiquidacion;
	private Float importeIrpf;
	private String nombre;


	/** default constructor */
	public ViewcontaindemnizacionesId() {
	}

	/** minimal constructor */
	public ViewcontaindemnizacionesId(Integer empresa, Integer ejercicio,
			String cuentaBancaria, Float importeLiquidacion, Float importeIrpf) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.cuentaBancaria = cuentaBancaria;
		this.importeLiquidacion = importeLiquidacion;
		this.importeIrpf = importeIrpf;
	}

	/** full constructor */
	public ViewcontaindemnizacionesId(Integer empresa, Integer ejercicio,
			Integer mes, String cuentaBancaria, String nombreBanco,
			String cuentaContable, Float importeLiquidacion, Float importeIrpf,
			String nombre) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaBancaria = cuentaBancaria;
		this.nombreBanco = nombreBanco;
		this.cuentaContable = cuentaContable;
		this.importeLiquidacion = importeLiquidacion;
		this.importeIrpf = importeIrpf;
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

	public Float getImporteLiquidacion() {
		return this.importeLiquidacion;
	}

	public void setImporteLiquidacion(Float importeLiquidacion) {
		this.importeLiquidacion = importeLiquidacion;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
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
		if (!(other instanceof ViewcontaindemnizacionesId))
			return false;
		ViewcontaindemnizacionesId castOther = (ViewcontaindemnizacionesId) other;

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
				&& ((this.getImporteLiquidacion() == castOther
						.getImporteLiquidacion()) || (this
						.getImporteLiquidacion() != null
						&& castOther.getImporteLiquidacion() != null && this
						.getImporteLiquidacion().equals(
								castOther.getImporteLiquidacion())))
				&& ((this.getImporteIrpf() == castOther.getImporteIrpf()) || (this
						.getImporteIrpf() != null
						&& castOther.getImporteIrpf() != null && this
						.getImporteIrpf().equals(castOther.getImporteIrpf())))
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
				+ (getImporteLiquidacion() == null ? 0 : this
						.getImporteLiquidacion().hashCode());
		result = 37
				* result
				+ (getImporteIrpf() == null ? 0 : this.getImporteIrpf()
						.hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		return result;
	}

}
