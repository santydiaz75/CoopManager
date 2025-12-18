package entitiesPackage;

import java.util.Date;

/**
 * Identidades entity. @author MyEclipse Persistence Tools
 */

public class Identidades implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IdentidadesId id;
	private String nombreIdentidad;
	private String direccion;
	private String poblacion;
	private String provincia;
	private String codigoPostal;
	private String telefono;
	private String nif;
	private String digitoControl;
	private String idBanco;
	private String idSucursal;
	private String cuentaBancaria;
        private String iban;
	private String cuentaContable;
	private Short cliente;
	private Short proveedor;
	private Integer tipoImpuesto;
	private Integer tipoIrpf;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Identidades() {
	}

	/** minimal constructor */
	public Identidades(IdentidadesId id, Short cliente, Short proveedor) {
		this.id = id;
		this.cliente = cliente;
		this.proveedor = proveedor;
	}

	/** full constructor */
	public Identidades(IdentidadesId id, String nombreIdentidad,
			String direccion, String poblacion, String provincia,
			String codigoPostal, String telefono, String nif,
			String digitoControl, String idBanco, String idSucursal,
			String cuentaBancaria, String iban, String cuentaContable, Short cliente,
			Short proveedor, Integer tipoImpuesto, Integer tipoIrpf,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.nombreIdentidad = nombreIdentidad;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.nif = nif;
		this.digitoControl = digitoControl;
		this.idBanco = idBanco;
		this.idSucursal = idSucursal;
		this.cuentaBancaria = cuentaBancaria;
                this.iban = iban;
		this.cuentaContable = cuentaContable;
		this.cliente = cliente;
		this.proveedor = proveedor;
		this.tipoImpuesto = tipoImpuesto;
		this.tipoIrpf = tipoIrpf;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public IdentidadesId getId() {
		return this.id;
	}

	public void setId(IdentidadesId id) {
		this.id = id;
	}

	public String getNombreIdentidad() {
		return this.nombreIdentidad;
	}

	public void setNombreIdentidad(String nombreIdentidad) {
		this.nombreIdentidad = nombreIdentidad;
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

	public Short getCliente() {
		return this.cliente;
	}

	public void setCliente(Short cliente) {
		this.cliente = cliente;
	}

	public Short getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Short proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getTipoImpuesto() {
		return this.tipoImpuesto;
	}

	public void setTipoImpuesto(Integer tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public Integer getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Integer tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
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
