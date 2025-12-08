package entitiesPackage;

import java.util.Date;

/**
 * Bancos entity. @author MyEclipse Persistence Tools
 */

public class Bancos implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BancosId id;
	private String nombreBanco;
	private String nombreSucursal;
	private String cuentaContable;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Bancos() {
	}

	/** minimal constructor */
	public Bancos(BancosId id) {
		this.id = id;
	}

	/** full constructor */
	public Bancos(BancosId id, String nombreBanco, String nombreSucursal,
			String cuentaContable, String sid, Date lmd, Integer version) {
		this.id = id;
		this.nombreBanco = nombreBanco;
		this.nombreSucursal = nombreSucursal;
		this.cuentaContable = cuentaContable;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public BancosId getId() {
		return this.id;
	}

	public void setId(BancosId id) {
		this.id = id;
	}

	public String getNombreBanco() {
		return this.nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getNombreSucursal() {
		return this.nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
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