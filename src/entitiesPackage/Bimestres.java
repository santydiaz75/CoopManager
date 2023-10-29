package entitiesPackage;

import java.util.Date;

/**
 * Bimestres entity. @author MyEclipse Persistence Tools
 */

public class Bimestres implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BimestresId id;
	private String descripcion;
	private Date desdeFecha;
	private Date hastaFecha;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Bimestres() {
	}

	/** minimal constructor */
	public Bimestres(BimestresId id) {
		this.id = id;
	}

	/** full constructor */
	public Bimestres(BimestresId id, String descripcion, Date desdeFecha,
			Date hastaFecha, String sid, Date lmd, Integer version) {
		this.id = id;
		this.descripcion = descripcion;
		this.desdeFecha = desdeFecha;
		this.hastaFecha = hastaFecha;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public BimestresId getId() {
		return this.id;
	}

	public void setId(BimestresId id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getDesdeFecha() {
		return this.desdeFecha;
	}

	public void setDesdeFecha(Date desdeFecha) {
		this.desdeFecha = desdeFecha;
	}

	public Date getHastaFecha() {
		return this.hastaFecha;
	}

	public void setHastaFecha(Date hastaFecha) {
		this.hastaFecha = hastaFecha;
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