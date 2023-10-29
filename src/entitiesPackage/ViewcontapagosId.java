package entitiesPackage;

import java.util.Date;

/**
 * ViewcontapagosId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontapagosId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Date fechaPago;
	private Long idFactura;
	private String concepto;
	private Long identidad;
	private String nombre;
	private String cuentaContableProveedor;
	private String cuentaBancaria;
	private Long talon;
	private String nombreBanco;
	private String cuentaContableBanco;
	private String cuentaContablePago;
	private Double importePago;
	private Double comision;
	private Long tipoImpuesto;
	private Double importeImpuesto;
	private Long tipoIrpf;
	private Double importeIrpf;
	private Double baseImponible;
	private Double importeFactura;

	// Constructors

	/** default constructor */
	public ViewcontapagosId() {
	}

	/** minimal constructor */
	public ViewcontapagosId(Integer empresa, Integer ejercicio, Long identidad) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.identidad = identidad;
	}

	/** full constructor */
	public ViewcontapagosId(Integer empresa, Integer ejercicio,
			Date fechaPago, Long idFactura, String concepto,
			Long identidad, String nombre, String cuentaContableProveedor,
			String cuentaBancaria, Long talon, String nombreBanco,
			String cuentaContableBanco, String cuentaContablePago,
			Double importePago, Double comision, Long tipoImpuesto,
			Double importeImpuesto, Long tipoIrpf, Double importeIrpf,
			Double baseImponible, Double importeFactura) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.fechaPago = fechaPago;
		this.idFactura = idFactura;
		this.concepto = concepto;
		this.identidad = identidad;
		this.nombre = nombre;
		this.cuentaContableProveedor = cuentaContableProveedor;
		this.cuentaBancaria = cuentaBancaria;
		this.talon = talon;
		this.nombreBanco = nombreBanco;
		this.cuentaContableBanco = cuentaContableBanco;
		this.cuentaContablePago = cuentaContablePago;
		this.importePago = importePago;
		this.comision = comision;
		this.tipoImpuesto = tipoImpuesto;
		this.importeImpuesto = importeImpuesto;
		this.tipoIrpf = tipoIrpf;
		this.importeIrpf = importeIrpf;
		this.baseImponible = baseImponible;
		this.importeFactura = importeFactura;
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

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Long getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
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

	public String getCuentaContableProveedor() {
		return this.cuentaContableProveedor;
	}

	public void setCuentaContableProveedor(String cuentaContableProveedor) {
		this.cuentaContableProveedor = cuentaContableProveedor;
	}

	public String getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Long getTalon() {
		return this.talon;
	}

	public void setTalon(Long talon) {
		this.talon = talon;
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

	public String getCuentaContablePago() {
		return this.cuentaContablePago;
	}

	public void setCuentaContablePago(String cuentaContablePago) {
		this.cuentaContablePago = cuentaContablePago;
	}

	public Double getImportePago() {
		return this.importePago;
	}

	public void setImportePago(Double importePago) {
		this.importePago = importePago;
	}

	public Double getComision() {
		return this.comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Long getTipoImpuesto() {
		return this.tipoImpuesto;
	}

	public void setTipoImpuesto(Long tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public Double getImporteImpuesto() {
		return this.importeImpuesto;
	}

	public void setImporteImpuesto(Double importeImpuesto) {
		this.importeImpuesto = importeImpuesto;
	}

	public Long getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Long tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
	}

	public Double getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Double importeIrpf) {
		this.importeIrpf = importeIrpf;
	}

	public Double getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Double getImporteFactura() {
		return this.importeFactura;
	}

	public void setImporteFactura(Double importeFactura) {
		this.importeFactura = importeFactura;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewcontapagosId))
			return false;
		ViewcontapagosId castOther = (ViewcontapagosId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getFechaPago() == castOther.getFechaPago()) || (this
						.getFechaPago() != null
						&& castOther.getFechaPago() != null && this
						.getFechaPago().equals(castOther.getFechaPago())))
				&& ((this.getIdFactura() == castOther.getIdFactura()) || (this
						.getIdFactura() != null
						&& castOther.getIdFactura() != null && this
						.getIdFactura().equals(castOther.getIdFactura())))
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
				&& ((this.getCuentaContableProveedor() == castOther
						.getCuentaContableProveedor()) || (this
						.getCuentaContableProveedor() != null
						&& castOther.getCuentaContableProveedor() != null && this
						.getCuentaContableProveedor().equals(
								castOther.getCuentaContableProveedor())))
				&& ((this.getCuentaBancaria() == castOther.getCuentaBancaria()) || (this
						.getCuentaBancaria() != null
						&& castOther.getCuentaBancaria() != null && this
						.getCuentaBancaria().equals(
								castOther.getCuentaBancaria())))
				&& ((this.getTalon() == castOther.getTalon()) || (this
						.getTalon() != null
						&& castOther.getTalon() != null && this.getTalon()
						.equals(castOther.getTalon())))
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
				&& ((this.getCuentaContablePago() == castOther
						.getCuentaContablePago()) || (this
						.getCuentaContablePago() != null
						&& castOther.getCuentaContablePago() != null && this
						.getCuentaContablePago().equals(
								castOther.getCuentaContablePago())))
				&& ((this.getImportePago() == castOther.getImportePago()) || (this
						.getImportePago() != null
						&& castOther.getImportePago() != null && this
						.getImportePago().equals(castOther.getImportePago())))
				&& ((this.getComision() == castOther.getComision()) || (this
						.getComision() != null
						&& castOther.getComision() != null && this
						.getComision().equals(castOther.getComision())))
				&& ((this.getTipoImpuesto() == castOther.getTipoImpuesto()) || (this
						.getTipoImpuesto() != null
						&& castOther.getTipoImpuesto() != null && this
						.getTipoImpuesto().equals(castOther.getTipoImpuesto())))
				&& ((this.getImporteImpuesto() == castOther
						.getImporteImpuesto()) || (this.getImporteImpuesto() != null
						&& castOther.getImporteImpuesto() != null && this
						.getImporteImpuesto().equals(
								castOther.getImporteImpuesto())))
				&& ((this.getTipoIrpf() == castOther.getTipoIrpf()) || (this
						.getTipoIrpf() != null
						&& castOther.getTipoIrpf() != null && this
						.getTipoIrpf().equals(castOther.getTipoIrpf())))
				&& ((this.getImporteIrpf() == castOther.getImporteIrpf()) || (this
						.getImporteIrpf() != null
						&& castOther.getImporteIrpf() != null && this
						.getImporteIrpf().equals(castOther.getImporteIrpf())))
				&& ((this.getBaseImponible() == castOther.getBaseImponible()) || (this
						.getBaseImponible() != null
						&& castOther.getBaseImponible() != null && this
						.getBaseImponible()
						.equals(castOther.getBaseImponible())))
				&& ((this.getImporteFactura() == castOther.getImporteFactura()) || (this
						.getImporteFactura() != null
						&& castOther.getImporteFactura() != null && this
						.getImporteFactura().equals(
								castOther.getImporteFactura())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result
				+ (getFechaPago() == null ? 0 : this.getFechaPago().hashCode());
		result = 37 * result
				+ (getIdFactura() == null ? 0 : this.getIdFactura().hashCode());
		result = 37 * result
				+ (getConcepto() == null ? 0 : this.getConcepto().hashCode());
		result = 37 * result
				+ (getIdentidad() == null ? 0 : this.getIdentidad().hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		result = 37
				* result
				+ (getCuentaContableProveedor() == null ? 0 : this
						.getCuentaContableProveedor().hashCode());
		result = 37
				* result
				+ (getCuentaBancaria() == null ? 0 : this.getCuentaBancaria()
						.hashCode());
		result = 37 * result
				+ (getTalon() == null ? 0 : this.getTalon().hashCode());
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
				+ (getCuentaContablePago() == null ? 0 : this
						.getCuentaContablePago().hashCode());
		result = 37
				* result
				+ (getImportePago() == null ? 0 : this.getImportePago()
						.hashCode());
		result = 37 * result
				+ (getComision() == null ? 0 : this.getComision().hashCode());
		result = 37
				* result
				+ (getTipoImpuesto() == null ? 0 : this.getTipoImpuesto()
						.hashCode());
		result = 37
				* result
				+ (getImporteImpuesto() == null ? 0 : this.getImporteImpuesto()
						.hashCode());
		result = 37 * result
				+ (getTipoIrpf() == null ? 0 : this.getTipoIrpf().hashCode());
		result = 37
				* result
				+ (getImporteIrpf() == null ? 0 : this.getImporteIrpf()
						.hashCode());
		result = 37
				* result
				+ (getBaseImponible() == null ? 0 : this.getBaseImponible()
						.hashCode());
		result = 37
				* result
				+ (getImporteFactura() == null ? 0 : this.getImporteFactura()
						.hashCode());
		return result;
	}

}