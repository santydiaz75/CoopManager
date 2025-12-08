package entitiesPackage;

import java.util.Date;

/**
 * Empleadoscontratos entity. @author MyEclipse Persistence Tools
 */

public class Empleadoscontratos implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private EmpleadoscontratosId id;
	private Date fechaBaja;
	private String duracion;
	private Float importeLiquidacion;
	private Float importeIrpf;
	private String cuentaBancariaPago;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Empleadoscontratos() {
	}

	/** minimal constructor */
	public Empleadoscontratos(EmpleadoscontratosId id,
			Float importeLiquidacion, Float importeIrpf) {
		this.id = id;
		this.importeLiquidacion = importeLiquidacion;
		this.importeIrpf = importeIrpf;
	}

	/** full constructor */
	public Empleadoscontratos(EmpleadoscontratosId id, Date fechaBaja,
			String duracion, Float importeLiquidacion, Float importeIrpf,
			String cuentaBancariaPago, String comentarios, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.fechaBaja = fechaBaja;
		this.duracion = duracion;
		this.importeLiquidacion = importeLiquidacion;
		this.importeIrpf = importeIrpf;
		this.cuentaBancariaPago = cuentaBancariaPago;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EmpleadoscontratosId getId() {
		return this.id;
	}

	public void setId(EmpleadoscontratosId id) {
		this.id = id;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getDuracion() {
		return this.duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Float getImporteLiquidacion() {
		return this.importeLiquidacion;
	}

	public void setImporteLiquidacion(Float importeLiquidacion) {
		this.importeLiquidacion = importeLiquidacion;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
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