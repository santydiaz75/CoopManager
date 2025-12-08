package entitiesPackage;

import java.util.Date;

/**
 * Ayudas entity. @author MyEclipse Persistence Tools
 */

public class Ayudas implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AyudasId id;
	private String nombreApellidos;
	private String nif;
	private String direccion;
	private String poblacion;
	private String codigoPostal;
	private String telefono;
	private String idBanco;
	private String idSucursal;
	private String digitoControl;
	private String cuentaBancaria;
        private String iban;
	private String cuentaContable;
	private Float kilos;
	private Float numPinas;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Ayudas() {
	}

	/** minimal constructor
     * @param id
     * @param kilos
     * @param numPinas */
	public Ayudas(AyudasId id, Float kilos, Float numPinas) {
		this.id = id;
		this.kilos = kilos;
		this.numPinas = numPinas;
	}

	/** full constructor
     * @param id
     * @param direccion
     * @param poblacion
     * @param codigoPostal */
	public Ayudas(AyudasId id, String nombreApellidos, String nif,
			String direccion, String poblacion, String codigoPostal,
			String telefono, String idBanco, String idSucursal,
			String digitoControl, String cuentaBancaria, String iban, String cuentaContable,
			Float kilos, Float numPinas, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.nombreApellidos = nombreApellidos;
		this.nif = nif;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.idBanco = idBanco;
		this.idSucursal = idSucursal;
		this.digitoControl = digitoControl;
		this.cuentaBancaria = cuentaBancaria;
                this.iban = iban;
		this.cuentaContable = cuentaContable;
		this.kilos = kilos;
		this.numPinas = numPinas;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public AyudasId getId() {
		return this.id;
	}

	public void setId(AyudasId id) {
		this.id = id;
	}

	public String getNombreApellidos() {
		return this.nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public String getDigitoControl() {
		return this.digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
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

	public Float getKilos() {
		return this.kilos;
	}

	public void setKilos(Float kilos) {
		this.kilos = kilos;
	}

	public Float getNumPinas() {
		return this.numPinas;
	}

	public void setNumPinas(Float numPinas) {
		this.numPinas = numPinas;
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