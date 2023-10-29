package entitiesPackage;

import java.util.Date;

/**
 * Pagos entity. @author MyEclipse Persistence Tools
 */

public class Pagos implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private PagosId id;
	private Date fechaPago;
	private Integer idFactura;
	private String concepto;
	private String cuentaBancaria;
	private Integer talon;
	private Float importe;
	private Float comision;
	private String cuentaContable;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Pagos() {
	}

	/** minimal constructor */
	public Pagos(PagosId id) {
		this.id = id;
	}

	/** full constructor */
	public Pagos(PagosId id, Date fechaPago, Integer idFactura,
			String concepto, String cuentaBancaria, Integer talon,
			Float importe, Float comision, String cuentaContable, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.fechaPago = fechaPago;
		this.idFactura = idFactura;
		this.concepto = concepto;
		this.cuentaBancaria = cuentaBancaria;
		this.talon = talon;
		this.importe = importe;
		this.comision = comision;
		this.cuentaContable = cuentaContable;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public PagosId getId() {
		return this.id;
	}

	public void setId(PagosId id) {
		this.id = id;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Integer getTalon() {
		return this.talon;
	}

	public void setTalon(Integer talon) {
		this.talon = talon;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Float getComision() {
		return this.comision;
	}

	public void setComision(Float comision) {
		this.comision = comision;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getLmd() {
		return this.lmd;
	}

	public void setLmd(Date lmd) {
		this.lmd = lmd;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}