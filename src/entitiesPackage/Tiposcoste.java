package entitiesPackage;

import java.util.Date;

/**
 * Tiposcoste entity. @author MyEclipse Persistence Tools
 */

public class Tiposcoste implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TiposcosteId id;
	private String descTipoCoste;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Tiposcoste() {
	}

	/** minimal constructor */
	public Tiposcoste(TiposcosteId id) {
		this.id = id;
	}

	/** full constructor */
	public Tiposcoste(TiposcosteId id, String descTipoCoste, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.descTipoCoste = descTipoCoste;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public TiposcosteId getId() {
		return this.id;
	}

	public void setId(TiposcosteId id) {
		this.id = id;
	}

	public String getDescTipoCoste() {
		return this.descTipoCoste;
	}

	public void setDescTipoCoste(String descTipoCoste) {
		this.descTipoCoste = descTipoCoste;
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
