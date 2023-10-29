package entitiesPackage;

import java.util.Date;

/**
 * Paises entity. @author MyEclipse Persistence Tools
 */

public class Paises implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaisesId id;
	private String nombrePais;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Paises() {
	}

	/** minimal constructor */
	public Paises(PaisesId id) {
		this.id = id;
	}

	/** full constructor */
	public Paises(PaisesId id, String nombrePais, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.nombrePais = nombrePais;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public PaisesId getId() {
		return this.id;
	}

	public void setId(PaisesId id) {
		this.id = id;
	}

	public String getNombrePais() {
		return this.nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
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