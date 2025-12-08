package entitiesPackage;

import java.util.Date;

/**
 * Empleadosvacaciones entity. @author MyEclipse Persistence Tools
 */

public class Empleadosvacaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpleadosvacacionesId id;
	private Float numDias;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Empleadosvacaciones() {
	}

	/** minimal constructor */
	public Empleadosvacaciones(EmpleadosvacacionesId id, Float numDias) {
		this.id = id;
		this.numDias = numDias;
	}

	/** full constructor */
	public Empleadosvacaciones(EmpleadosvacacionesId id, Float numDias,
			String comentarios, String sid, Date lmd, Integer version) {
		this.id = id;
		this.numDias = numDias;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EmpleadosvacacionesId getId() {
		return this.id;
	}

	public void setId(EmpleadosvacacionesId id) {
		this.id = id;
	}

	public Float getNumDias() {
		return this.numDias;
	}

	public void setNumDias(Float numDias) {
		this.numDias = numDias;
	}

	public String getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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