package entitiesPackage;

import java.util.Date;

/**
 * Entradaslineas entity. @author MyEclipse Persistence Tools
 */

public class Entradaslineas implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntradaslineasId id;
	private Float numKilos;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Entradaslineas() {
	}

	/** minimal constructor */
	public Entradaslineas(EntradaslineasId id, Float numKilos) {
		this.id = id;
		this.numKilos = numKilos;
	}

	/** full constructor */
	public Entradaslineas(EntradaslineasId id, Float numKilos, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.numKilos = numKilos;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public EntradaslineasId getId() {
		return this.id;
	}

	public void setId(EntradaslineasId id) {
		this.id = id;
	}

	public Float getNumKilos() {
		return this.numKilos;
	}

	public void setNumKilos(Float numKilos) {
		this.numKilos = numKilos;
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
