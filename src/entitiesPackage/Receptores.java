package entitiesPackage;

import java.util.Date;

/**
 * Receptores entity. @author MyEclipse Persistence Tools
 */

public class Receptores implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReceptoresId id;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String codigoPostal;
	private String telefono;
	private String fax;
	private String nif;
	private String cuentaContable;
	private Integer idZona;
	private Short mercadoLocal;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Receptores() {
	}

	/** minimal constructor */
	public Receptores(ReceptoresId id) {
		this.id = id;
	}

	/** full constructor */
	public Receptores(ReceptoresId id, String nombre, String direccion,
			String poblacion, String codigoPostal, String telefono, String fax,
			String nif, String cuentaContable, Integer idZona,
			Short mercadoLocal, String sid, Date lmd, Integer version) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.fax = fax;
		this.nif = nif;
		this.cuentaContable = cuentaContable;
		this.idZona = idZona;
		this.mercadoLocal = mercadoLocal;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public ReceptoresId getId() {
		return this.id;
	}

	public void setId(ReceptoresId id) {
		this.id = id;
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

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public Short getMercadoLocal() {
		return this.mercadoLocal;
	}

	public void setMercadoLocal(Short mercadoLocal) {
		this.mercadoLocal = mercadoLocal;
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