package entitiesPackage;

/**
 * ViewcontanominasId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontanominasId implements java.io.Serializable {

	// Fields

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
	private Double totalDevengado;
	private Double importeIrpf;
	private Double importeSegAutonomo;
	private Double importeEmbargo;
	private Double importeSegSoc;
	private Double totalLiquido;

	// Constructors

	/** default constructor */
	public ViewcontanominasId() {
	}

	/** minimal constructor */
	public ViewcontanominasId(Integer empresa, Integer ejercicio, Integer mes,
			String cuentaBancaria) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaBancaria = cuentaBancaria;
	}

	/** full constructor */
	public ViewcontanominasId(Integer empresa, Integer ejercicio, Integer mes,
			String cuentaBancaria, String nombreBanco, String cuentaContable,
			Double totalDevengado, Double importeIrpf,
			Double importeSegAutonomo, Double importeEmbargo,
			Double importeSegSoc, Double totalLiquido) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaBancaria = cuentaBancaria;
		this.nombreBanco = nombreBanco;
		this.cuentaContable = cuentaContable;
		this.totalDevengado = totalDevengado;
		this.importeIrpf = importeIrpf;
		this.importeSegAutonomo = importeSegAutonomo;
		this.importeEmbargo = importeEmbargo;
		this.importeSegSoc = importeSegSoc;
		this.totalLiquido = totalLiquido;
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

	public Double getTotalDevengado() {
		return this.totalDevengado;
	}

	public void setTotalDevengado(Double totalDevengado) {
		this.totalDevengado = totalDevengado;
	}

	public Double getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Double importeIrpf) {
		this.importeIrpf = importeIrpf;
	}

	public Double getImporteSegAutonomo() {
		return this.importeSegAutonomo;
	}

	public void setImporteSegAutonomo(Double importeSegAutonomo) {
		this.importeSegAutonomo = importeSegAutonomo;
	}

	public Double getImporteEmbargo() {
		return this.importeEmbargo;
	}

	public void setImporteEmbargo(Double importeEmbargo) {
		this.importeEmbargo = importeEmbargo;
	}

	public Double getImporteSegSoc() {
		return this.importeSegSoc;
	}

	public void setImporteSegSoc(Double importeSegSoc) {
		this.importeSegSoc = importeSegSoc;
	}

	public Double getTotalLiquido() {
		return this.totalLiquido;
	}

	public void setTotalLiquido(Double totalLiquido) {
		this.totalLiquido = totalLiquido;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewcontanominasId))
			return false;
		ViewcontanominasId castOther = (ViewcontanominasId) other;

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
				&& ((this.getTotalDevengado() == castOther.getTotalDevengado()) || (this
						.getTotalDevengado() != null
						&& castOther.getTotalDevengado() != null && this
						.getTotalDevengado().equals(
								castOther.getTotalDevengado())))
				&& ((this.getImporteIrpf() == castOther.getImporteIrpf()) || (this
						.getImporteIrpf() != null
						&& castOther.getImporteIrpf() != null && this
						.getImporteIrpf().equals(castOther.getImporteIrpf())))
				&& ((this.getImporteSegAutonomo() == castOther
						.getImporteSegAutonomo()) || (this
						.getImporteSegAutonomo() != null
						&& castOther.getImporteSegAutonomo() != null && this
						.getImporteSegAutonomo().equals(
								castOther.getImporteSegAutonomo())))
				&& ((this.getImporteEmbargo() == castOther.getImporteEmbargo()) || (this
						.getImporteEmbargo() != null
						&& castOther.getImporteEmbargo() != null && this
						.getImporteEmbargo().equals(
								castOther.getImporteEmbargo())))
				&& ((this.getImporteSegSoc() == castOther.getImporteSegSoc()) || (this
						.getImporteSegSoc() != null
						&& castOther.getImporteSegSoc() != null && this
						.getImporteSegSoc()
						.equals(castOther.getImporteSegSoc())))
				&& ((this.getTotalLiquido() == castOther.getTotalLiquido()) || (this
						.getTotalLiquido() != null
						&& castOther.getTotalLiquido() != null && this
						.getTotalLiquido().equals(castOther.getTotalLiquido())));
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
				+ (getTotalDevengado() == null ? 0 : this.getTotalDevengado()
						.hashCode());
		result = 37
				* result
				+ (getImporteIrpf() == null ? 0 : this.getImporteIrpf()
						.hashCode());
		result = 37
				* result
				+ (getImporteSegAutonomo() == null ? 0 : this
						.getImporteSegAutonomo().hashCode());
		result = 37
				* result
				+ (getImporteEmbargo() == null ? 0 : this.getImporteEmbargo()
						.hashCode());
		result = 37
				* result
				+ (getImporteSegSoc() == null ? 0 : this.getImporteSegSoc()
						.hashCode());
		result = 37
				* result
				+ (getTotalLiquido() == null ? 0 : this.getTotalLiquido()
						.hashCode());
		return result;
	}

}