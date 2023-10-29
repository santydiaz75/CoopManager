package entitiesPackage;

import java.util.Date;

/**
 * Entradascabecera entity. @author MyEclipse Persistence Tools
 */

public class Entradascabecera implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntradascabeceraId id;
	private Integer semana;
	private Date fecha;
	private Integer idCosechero;
	private Integer idZona;
	private Float numPinas;
	private Short recogidaPropia;
	private Float importeBonificacion;
	private Float kilosBonificacion;
	private Float numPinasBonificacion;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Entradascabecera() {
	}

	/** minimal constructor */
	public Entradascabecera(EntradascabeceraId id) {
		this.id = id;
	}

	/** full constructor */
	public Entradascabecera(EntradascabeceraId id, Integer semana,
			Date fecha, Integer idCosechero, Integer idZona,
			Float numPinas, Short recogidaPropia, Float importeBonificacion, Float kilosBonificacion, Float numPinasBonificacion, String sid, Date lmd, Integer version) {
		this.id = id;
		this.semana = semana;
		this.fecha = fecha;
		this.idCosechero = idCosechero;
		this.idZona = idZona;
		this.numPinas = numPinas;
		this.recogidaPropia = recogidaPropia;
		this.importeBonificacion = importeBonificacion;
		this.kilosBonificacion = importeBonificacion;
		this.numPinasBonificacion = importeBonificacion;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EntradascabeceraId getId() {
		return this.id;
	}

	public void setId(EntradascabeceraId id) {
		this.id = id;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
	}

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public Float getNumPinas() {
		return this.numPinas;
	}

	public void setNumPinas(Float numPinas) {
		this.numPinas = numPinas;
	}
	
	public Short getRecogidaPropia() {
		return this.recogidaPropia;
	}

	public void setRecogidaPropia(Short recogidaPropia) {
		this.recogidaPropia = recogidaPropia;
	}
	
	public Float getImporteBonificacion() {
		return this.importeBonificacion;
	}

	public void setImporteBonificacion(Float importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
	}
	
	public Float getKilosBonificacion() {
		return this.kilosBonificacion;
	}

	public void setKilosBonificacion(Float kilosBonificacion) {
		this.kilosBonificacion = kilosBonificacion;
	}

	public Float getNumPinasBonificacion() {
		return this.numPinasBonificacion;
	}

	public void setNumPinasBonificacion(Float numPinasBonificacion) {
		this.numPinasBonificacion = numPinasBonificacion;
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