package entitiesPackage;

import java.util.Date;

/**
 * Conductores entity. @author MyEclipse Persistence Tools
 */

public class Conductores implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConductoresId id;
	private String apellidos;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String codigoPostal;
	private String telefono;
	private String nif;
	private String digitoControl;
	private String idBanco;
	private String idSucursal;
	private String cuentaBancaria;
        private String iban;
	private String cuentaContable;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Conductores() {
	}

	/** minimal constructor */
	public Conductores(ConductoresId id) {
		this.id = id;
	}

	/** full constructor */
	public Conductores(ConductoresId id, String apellidos, String nombre,
			String direccion, String poblacion, String codigoPostal,
			String telefono, String nif, String digitoControl, String idBanco,
			String idSucursal, String cuentaBancaria, String iban, String cuentaContable,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.nif = nif;
		this.digitoControl = digitoControl;
		this.idBanco = idBanco;
		this.idSucursal = idSucursal;
		this.cuentaBancaria = cuentaBancaria;
                this.iban = iban;
		this.cuentaContable = cuentaContable;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public ConductoresId getId() {
		return this.id;
	}

	public void setId(ConductoresId id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDigitoControl() {
		return this.digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}

	public String getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
        
        public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
        
	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
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