package entitiesPackage;

import java.util.Date;

/**
 * Tiposgasto entity. @author MyEclipse Persistence Tools
 */

public class Tiposgasto implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TiposgastoId id;
	private String descTipoGasto;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Tiposgasto() {
	}

	/** minimal constructor */
	public Tiposgasto(TiposgastoId id) {
		this.id = id;
	}

	/** full constructor */
	public Tiposgasto(TiposgastoId id, String descTipoGasto, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.descTipoGasto = descTipoGasto;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public TiposgastoId getId() {
		return this.id;
	}

	public void setId(TiposgastoId id) {
		this.id = id;
	}

	public String getDescTipoGasto() {
		return this.descTipoGasto;
	}

	public void setDescTipoGasto(String descTipoGasto) {
		this.descTipoGasto = descTipoGasto;
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
