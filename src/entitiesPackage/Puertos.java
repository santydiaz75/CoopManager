package entitiesPackage;

import java.util.Date;

/**
 * Puertos entity. @author MyEclipse Persistence Tools
 */

public class Puertos implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PuertosId id;
	private String nombrePuerto;
	private Integer idPais;
	private String plaza;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Puertos() {
	}

	/** minimal constructor */
	public Puertos(PuertosId id, String plaza) {
		this.id = id;
		this.plaza = plaza;
	}

	/** full constructor */
	public Puertos(PuertosId id, String nombrePuerto, Integer idPais,
			String plaza, String sid, Date lmd, Integer version) {
		this.id = id;
		this.nombrePuerto = nombrePuerto;
		this.idPais = idPais;
		this.plaza = plaza;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public PuertosId getId() {
		return this.id;
	}

	public void setId(PuertosId id) {
		this.id = id;
	}

	public String getNombrePuerto() {
		return this.nombrePuerto;
	}

	public void setNombrePuerto(String nombrePuerto) {
		this.nombrePuerto = nombrePuerto;
	}

	public Integer getIdPais() {
		return this.idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getPlaza() {
		return this.plaza;
	}

	public void setPlaza(String plaza) {
		this.plaza = plaza;
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
