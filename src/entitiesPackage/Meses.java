package entitiesPackage;

import java.sql.Timestamp;

/**
 * Meses entity. @author MyEclipse Persistence Tools
 */

public class Meses implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mes;
	private String nombreMes;
	private String sid;
	private Timestamp lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Meses() {
	}

	/** minimal constructor */
	public Meses(Integer mes) {
		this.mes = mes;
	}

	/** full constructor */
	public Meses(Integer mes, String nombreMes, String sid, Timestamp lmd,
			Integer version) {
		this.mes = mes;
		this.nombreMes = nombreMes;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getNombreMes() {
		return this.nombreMes;
	}

	public void setNombreMes(String nombreMes) {
		this.nombreMes = nombreMes;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Timestamp getLmd() {
		return this.lmd;
	}

	public void setLmd(Timestamp lmd) {
		this.lmd = lmd;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}