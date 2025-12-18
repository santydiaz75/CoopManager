package entitiesPackage;

import java.util.Date;

/**
 * Vehiculos entity. @author MyEclipse Persistence Tools
 */

public class Vehiculos implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VehiculosId id;
	private String marca;
	private String matricula;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Vehiculos() {
	}

	/** minimal constructor */
	public Vehiculos(VehiculosId id) {
		this.id = id;
	}

	/** full constructor */
	public Vehiculos(VehiculosId id, String marca, String matricula,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.marca = marca;
		this.matricula = matricula;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public VehiculosId getId() {
		return this.id;
	}

	public void setId(VehiculosId id) {
		this.id = id;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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
