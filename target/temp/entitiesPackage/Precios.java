package entitiesPackage;

import java.util.Date;

/**
 * Precios entity. @author MyEclipse Persistence Tools
 */

public class Precios implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PreciosId id;
	private Float precio;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Precios() {
	}

	/** minimal constructor */
	public Precios(PreciosId id, Float precio) {
		this.id = id;
		this.precio = precio;
	}

	/** full constructor */
	public Precios(PreciosId id, Float precio, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.precio = precio;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public PreciosId getId() {
		return this.id;
	}

	public void setId(PreciosId id) {
		this.id = id;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
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