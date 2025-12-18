package entitiesPackage;

import java.util.Date;

/**
 * Calendario entity. @author MyEclipse Persistence Tools
 */

public class Calendario implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalendarioId id;
	private Date desdeFecha;
	private Date hastaFecha;
	private Float porcKilosInutilizados;
	private String refAutoControl;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Calendario() {
	}

	/** minimal constructor */
	public Calendario(CalendarioId id, Float porcKilosInutilizados) {
		this.id = id;
		this.porcKilosInutilizados = porcKilosInutilizados;
	}

	/** full constructor */
	public Calendario(CalendarioId id, Date desdeFecha,
			Date hastaFecha, Float porcKilosInutilizados,
			String refAutoControl, String sid, Date lmd, Integer version) {
		this.id = id;
		this.desdeFecha = desdeFecha;
		this.hastaFecha = hastaFecha;
		this.porcKilosInutilizados = porcKilosInutilizados;
		this.refAutoControl = refAutoControl;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public CalendarioId getId() {
		return this.id;
	}

	public void setId(CalendarioId id) {
		this.id = id;
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

	public Float getPorcKilosInutilizados() {
		return this.porcKilosInutilizados;
	}

	public void setPorcKilosInutilizados(Float porcKilosInutilizados) {
		this.porcKilosInutilizados = porcKilosInutilizados;
	}

	public String getRefAutoControl() {
		return this.refAutoControl;
	}

	public void setRefAutoControl(String refAutoControl) {
		this.refAutoControl = refAutoControl;
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
