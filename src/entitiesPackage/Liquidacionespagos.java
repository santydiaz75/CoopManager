package entitiesPackage;

import java.util.Date;

/**
 * Liquidacionespagos entity. @author MyEclipse Persistence Tools
 */

public class Liquidacionespagos implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LiquidacionespagosId id;
	private Date fechaPago;
	private String concepto;
	private String cuentaBancaria;
	private Float importe;
	private Float comision;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Liquidacionespagos() {
	}

	/** minimal constructor */
	public Liquidacionespagos(LiquidacionespagosId id) {
		this.id = id;
	}

	/** full constructor */
	public Liquidacionespagos(LiquidacionespagosId id, Date fechaPago,
			String concepto, String cuentaBancaria, Float importe, Float comision, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.fechaPago = fechaPago;
		this.concepto = concepto;
		this.cuentaBancaria = cuentaBancaria;
		this.importe = importe;
		this.comision = comision;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public LiquidacionespagosId getId() {
		return this.id;
	}

	public void setId(LiquidacionespagosId id) {
		this.id = id;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
