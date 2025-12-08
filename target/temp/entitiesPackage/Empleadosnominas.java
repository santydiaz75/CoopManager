package entitiesPackage;

import java.util.Date;

/**
 * Empleadosnominas entity. @author MyEclipse Persistence Tools
 */

public class Empleadosnominas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private EmpleadosnominasId id;
	private Float totalDevengado;
	private Float importeIrpf;
	private Float importeSegSoc;
	private Float importeSegAutonomo;
	private Float importeEmbargo;
	private Float totalLiquido;
	private Float importeBonificacion;
	private String cuentaBancariaPago;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Empleadosnominas() {
	}

	/** minimal constructor */
	public Empleadosnominas(EmpleadosnominasId id, Float totalDevengado,
			Float importeIrpf, Float importeSegSoc, Float importeSegAutonomo,
			Float importeEmbargo, Float totalLiquido, Float importeBonificacion) {
		this.id = id;
		this.totalDevengado = totalDevengado;
		this.importeIrpf = importeIrpf;
		this.importeSegSoc = importeSegSoc;
		this.importeSegAutonomo = importeSegAutonomo;
		this.importeEmbargo = importeEmbargo;
		this.totalLiquido = totalLiquido;
		this.importeBonificacion = importeBonificacion;
	}

	/** full constructor */
	public Empleadosnominas(EmpleadosnominasId id, Float totalDevengado,
			Float importeIrpf, Float importeSegSoc, Float importeSegAutonomo,
			Float importeEmbargo, Float totalLiquido,
			Float importeBonificacion, String cuentaBancariaPago,
			String comentarios, String sid, Date lmd, Integer version) {
		this.id = id;
		this.totalDevengado = totalDevengado;
		this.importeIrpf = importeIrpf;
		this.importeSegSoc = importeSegSoc;
		this.importeSegAutonomo = importeSegAutonomo;
		this.importeEmbargo = importeEmbargo;
		this.totalLiquido = totalLiquido;
		this.importeBonificacion = importeBonificacion;
		this.cuentaBancariaPago = cuentaBancariaPago;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EmpleadosnominasId getId() {
		return this.id;
	}

	public void setId(EmpleadosnominasId id) {
		this.id = id;
	}

	public Float getTotalDevengado() {
		return this.totalDevengado;
	}

	public void setTotalDevengado(Float totalDevengado) {
		this.totalDevengado = totalDevengado;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
	}

	public Float getImporteSegSoc() {
		return this.importeSegSoc;
	}

	public void setImporteSegSoc(Float importeSegSoc) {
		this.importeSegSoc = importeSegSoc;
	}

	public Float getImporteSegAutonomo() {
		return this.importeSegAutonomo;
	}

	public void setImporteSegAutonomo(Float importeSegAutonomo) {
		this.importeSegAutonomo = importeSegAutonomo;
	}

	public Float getImporteEmbargo() {
		return this.importeEmbargo;
	}

	public void setImporteEmbargo(Float importeEmbargo) {
		this.importeEmbargo = importeEmbargo;
	}

	public Float getTotalLiquido() {
		return this.totalLiquido;
	}

	public void setTotalLiquido(Float totalLiquido) {
		this.totalLiquido = totalLiquido;
	}

	public Float getImporteBonificacion() {
		return this.importeBonificacion;
	}

	public void setImporteBonificacion(Float importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
	}

	public String getCuentaBancariaPago() {
		return this.cuentaBancariaPago;
	}

	public void setCuentaBancariaPago(String cuentaBancariaPago) {
		this.cuentaBancariaPago = cuentaBancariaPago;
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