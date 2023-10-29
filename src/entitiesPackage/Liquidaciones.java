package entitiesPackage;

import java.util.Date;

/**
 * Liquidaciones entity. @author MyEclipse Persistence Tools
 */

public class Liquidaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LiquidacionesId id;
	private Date fecha;
	private Integer idCosechero;
	private Integer mes;
	private Integer tipoIgic;
	private Integer tipoIrpf;
	private Float baseImponible;
	private Float importeIgic;
	private Float importeIrpf;
	private Float importeBonificacion;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Liquidaciones() {
	}

	/** minimal constructor */
	public Liquidaciones(LiquidacionesId id, Float baseImponible,
			Float importeIgic, Float importeIrpf, Float importeBonificacion) {
		this.id = id;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
		this.importeBonificacion = importeBonificacion;
	}

	/** full constructor */
	public Liquidaciones(LiquidacionesId id, Date fecha,
			Integer idCosechero, Integer mes, Integer tipoIgic,
			Integer tipoIrpf, Float baseImponible, Float importeIgic,
			Float importeIrpf, Float importeBonificacion, String sid, Date lmd, Integer version) {
		this.id = id;
		this.fecha = fecha;
		this.idCosechero = idCosechero;
		this.mes = mes;
		this.tipoIgic = tipoIgic;
		this.tipoIrpf = tipoIrpf;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
		this.importeBonificacion = importeBonificacion;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public LiquidacionesId getId() {
		return this.id;
	}

	public void setId(LiquidacionesId id) {
		this.id = id;
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

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getTipoIgic() {
		return this.tipoIgic;
	}

	public void setTipoIgic(Integer tipoIgic) {
		this.tipoIgic = tipoIgic;
	}

	public Integer getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Integer tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
	}

	public Float getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(Float baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Float getImporteIgic() {
		return this.importeIgic;
	}

	public void setImporteIgic(Float importeIgic) {
		this.importeIgic = importeIgic;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
	}
	
	public Float getImporteBonificacion() {
		return this.importeBonificacion;
	}

	public void setImporteBonificacion(Float importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
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