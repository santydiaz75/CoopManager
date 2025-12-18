package entitiesPackage;

import java.util.Date;

/**
 * Facturaslineas entity. @author MyEclipse Persistence Tools
 */

public class Facturaslineas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FacturaslineasId id;
	private Float unidades;
	private Integer idConcepto;
	private String concepto;
	private Float precio;
	private Float importe;
	private Float pctImpuesto;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Facturaslineas() {
	}

	/** minimal constructor */
	public Facturaslineas(FacturaslineasId id) {
		this.id = id;
	}

	/** full constructor */
	public Facturaslineas(FacturaslineasId id, Float unidades,
			Integer idConcepto, String concepto, Float precio, Float importe, Float pctImpuesto,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.unidades = unidades;
		this.idConcepto = idConcepto;
		this.concepto = concepto;
		this.precio = precio;
		this.importe = importe;
		this.pctImpuesto = pctImpuesto;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public FacturaslineasId getId() {
		return this.id;
	}

	public void setId(FacturaslineasId id) {
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
	
	public Float getPctImpuesto() {
		return this.pctImpuesto;
	}

	public void setPctImpuesto(Float pctImpuesto) {
		this.pctImpuesto = pctImpuesto;
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
