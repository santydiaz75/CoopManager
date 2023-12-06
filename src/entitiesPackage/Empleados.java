package entitiesPackage;

import java.util.Date;

/**
 * Empleados entity. @author MyEclipse Persistence Tools
 */

public class Empleados implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpleadosId id;
	private String apellidos;
	private Date fechaNacimiento;
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
	private String categoria;
	private Date fechaAntiguedad;
	private String observaciones;
	private Short fijo;
	private String correoElectronico;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Empleados() {
	}

	/** minimal constructor */
	public Empleados(EmpleadosId id, Short fijo) {
		this.id = id;
		this.fijo = fijo;
	}

	/** full constructor */
	public Empleados(EmpleadosId id, String apellidos,
			Date fechaNacimiento, String nombre, String direccion,
			String poblacion, String codigoPostal, String telefono, String nif,
			String digitoControl, String idBanco, String idSucursal,
			String cuentaBancaria, String iban, String categoria, Date fechaAntiguedad,
			String observaciones, Short fijo, String correoElectronico, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
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
		this.categoria = categoria;
		this.fechaAntiguedad = fechaAntiguedad;
		this.observaciones = observaciones;
		this.fijo = fijo;
		this.correoElectronico = correoElectronico;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EmpleadosId getId() {
		return this.id;
	}

	public void setId(EmpleadosId id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
        
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getFechaAntiguedad() {
		return this.fechaAntiguedad;
	}

	public void setFechaAntiguedad(Date fechaAntiguedad) {
		this.fechaAntiguedad = fechaAntiguedad;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Short getFijo() {
		return this.fijo;
	}

	public void setFijo(Short fijo) {
		this.fijo = fijo;
	}
	
	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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