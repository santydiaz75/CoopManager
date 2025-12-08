package entitiesPackage;

import java.util.Date;

/**
 * Facturaspagolineas entity. @author MyEclipse Persistence Tools
 */

public class Facturaspagolineas implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacturaspagolineasId id;
	private Float unidades;
	private Integer idConcepto;
	private String concepto;
	private Float precio;
	private Float importe;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Facturaspagolineas() {
	}

	/** minimal constructor */
	public Facturaspagolineas(FacturaspagolineasId id) {
		this.id = id;
	}

	/** full constructor */
	public Facturaspagolineas(FacturaspagolineasId id, Float unidades,
			Integer idConcepto, String concepto, Float precio, Float importe,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.unidades = unidades;
		this.idConcepto = idConcepto;
		this.concepto = concepto;
		this.precio = precio;
		this.importe = importe;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public FacturaspagolineasId getId() {
		return this.id;
	}

	public void setId(FacturaspagolineasId id) {
		this.id = id;
	}

	public Float getUnidades() {
		return this.unidades;
	}

	public void setUnidades(Float unidades) {
		this.unidades = unidades;
	}

	public Integer getIdConcepto() {
		return this.idConcepto;
	}

	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
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