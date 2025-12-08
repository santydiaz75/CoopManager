package entitiesPackage;

import java.util.Date;

/**
 * Facturascabecera entity. @author MyEclipse Persistence Tools
 */

public class Facturascabecera implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacturascabeceraId id;
	private Integer semana;
	private Date fecha;
	private Integer identidad;
	private String cuentaCliente;
	private String nif;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String provincia;
	private String codigoPostal;
	private Float baseImponible;
	private Float tipoImpuesto;
	private Float importeImpuesto;
	private Float tipoIrpf;
	private Float importeIrpf;
	private Float importeFactura;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Facturascabecera() {
	}

	/** minimal constructor */
	public Facturascabecera(FacturascabeceraId id) {
		this.id = id;
	}

	/** full constructor */
	public Facturascabecera(FacturascabeceraId id, Integer semana,
			Date fecha, Integer identidad, String cuentaCliente,
			String nif, String nombre, String direccion, String poblacion,
			String provincia, String codigoPostal, Float baseImponible,
			Float tipoImpuesto, Float importeImpuesto, Float tipoIrpf,
			Float importeIrpf, Float importeFactura, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.semana = semana;
		this.fecha = fecha;
		this.identidad = identidad;
		this.cuentaCliente = cuentaCliente;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.baseImponible = baseImponible;
		this.tipoImpuesto = tipoImpuesto;
		this.importeImpuesto = importeImpuesto;
		this.tipoIrpf = tipoIrpf;
		this.importeIrpf = importeIrpf;
		this.importeFactura = importeFactura;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public FacturascabeceraId getId() {
		return this.id;
	}

	public void setId(FacturascabeceraId id) {
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

	public Integer getIdentidad() {
		return this.identidad;
	}

	public void setIdentidad(Integer identidad) {
		this.identidad = identidad;
	}

	public String getCuentaCliente() {
		return this.cuentaCliente;
	}

	public void setCuentaCliente(String cuentaCliente) {
		this.cuentaCliente = cuentaCliente;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return this.poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Float getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(Float baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Float getTipoImpuesto() {
		return this.tipoImpuesto;
	}

	public void setTipoImpuesto(Float tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public Float getImporteImpuesto() {
		return this.importeImpuesto;
	}

	public void setImporteImpuesto(Float importeImpuesto) {
		this.importeImpuesto = importeImpuesto;
	}

	public Float getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Float tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
	}

	public Float getImporteFactura() {
		return this.importeFactura;
	}

	public void setImporteFactura(Float importeFactura) {
		this.importeFactura = importeFactura;
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