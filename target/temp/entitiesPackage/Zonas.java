package entitiesPackage;

import java.util.Date;

/**
 * Zonas entity. @author MyEclipse Persistence Tools
 */

public class Zonas implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZonasId id;
	private String nombreZona;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Zonas() {
	}

	/** minimal constructor */
	public Zonas(ZonasId id) {
		this.id = id;
	}

	/** full constructor */
	public Zonas(ZonasId id, String nombreZona, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.nombreZona = nombreZona;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public ZonasId getId() {
		return this.id;
	}

	public void setId(ZonasId id) {
		this.id = id;
	}

	public String getNombreZona() {
		return this.nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
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