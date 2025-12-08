package entitiesPackage;

import java.util.Date;

/**
 * Barcos entity. @author MyEclipse Persistence Tools
 */

public class Barcos implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarcosId id;
	private String nombreBarco;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Barcos() {
	}

	/** minimal constructor */
	public Barcos(BarcosId id) {
		this.id = id;
	}

	/** full constructor */
	public Barcos(BarcosId id, String nombreBarco, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.nombreBarco = nombreBarco;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public BarcosId getId() {
		return this.id;
	}

	public void setId(BarcosId id) {
		this.id = id;
	}

	public String getNombreBarco() {
		return this.nombreBarco;
	}

	public void setNombreBarco(String nombreBarco) {
		this.nombreBarco = nombreBarco;
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