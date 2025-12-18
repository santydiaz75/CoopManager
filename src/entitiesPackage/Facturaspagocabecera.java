package entitiesPackage;

import java.util.Date;

/**
 * Facturaspagocabecera entity. @author MyEclipse Persistence Tools
 */

public class Facturaspagocabecera implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacturaspagocabeceraId id;
	private String referencia;
	private Date fecha;
	private Integer identidad;
	private String cuentaProveedor;
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
	private Integer tipoCoste;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Facturaspagocabecera() {
	}

	/** minimal constructor */
	public Facturaspagocabecera(FacturaspagocabeceraId id) {
		this.id = id;
	}

	/** full constructor */
	public Facturaspagocabecera(FacturaspagocabeceraId id, String referencia,
			Date fecha, Integer identidad, String cuentaProveedor,
			String nif, String nombre, String direccion, String poblacion,
			String provincia, String codigoPostal, Float baseImponible,
			Float tipoImpuesto, Float importeImpuesto, Float tipoIrpf,
			Float importeIrpf, Float importeFactura, Integer tipoCoste,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.referencia = referencia;
		this.fecha = fecha;
		this.identidad = identidad;
		this.cuentaProveedor = cuentaProveedor;
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
		this.tipoCoste = tipoCoste;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public FacturaspagocabeceraId getId() {
		return this.id;
	}

	public void setId(FacturaspagocabeceraId id) {
		this.id = id;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public String getCuentaProveedor() {
		return this.cuentaProveedor;
	}

	public void setCuentaProveedor(String cuentaProveedor) {
		this.cuentaProveedor = cuentaProveedor;
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

	public Integer getTipoCoste() {
		return this.tipoCoste;
	}

	public void setTipoCoste(Integer tipoCoste) {
		this.tipoCoste = tipoCoste;
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
