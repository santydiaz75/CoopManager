package entitiesPackage;

import java.util.Date;

/**
 * Cosecheros entity. @author MyEclipse Persistence Tools
 */

public class Cosecheros implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CosecherosId id;
	private String nombre;
	private String apellidos;
	private String direccion;
	private String poblacion;
	private String codigoPostal;
	private String fax;
	private String telefono1;
	private String telefono2;
	private String nif;
	private String cuentaContable;
	private Integer tipoIgic;
	private Integer tipoIrpf;
	private Integer idZona;
	private String idBanco;
	private String idSucursal;
	private String digitoControl;
	private String cuentaBancaria;
        private String iban;
	private Short ocm;
	private Short grupoPladimisa;
	private String codigoIne;
	private Integer idGrupo;
	private Integer codigoAsesoria;
	private Float numKilosReferencia;
	private String email;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Cosecheros() {
	}

	/** minimal constructor */
	public Cosecheros(CosecherosId id, Short grupoPladimisa,
			Float numKilosReferencia) {
		this.id = id;
		this.grupoPladimisa = grupoPladimisa;
		this.numKilosReferencia = numKilosReferencia;
	}

	/** full constructor */
	public Cosecheros(CosecherosId id, String nombre, String apellidos,
			String direccion, String poblacion, String codigoPostal,
			String fax, String telefono1, String telefono2, String nif,
			String cuentaContable, Integer tipoIgic, Integer tipoIrpf,
			Integer idZona, String idBanco, String idSucursal,
			String digitoControl, String cuentaBancaria, String iban, Short ocm,
			Short grupoPladimisa, String codigoIne, Integer idGrupo,
			Integer codigoAsesoria, Float numKilosReferencia, String email,
			String sid, Date lmd, Integer version) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codigoPostal = codigoPostal;
		this.fax = fax;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.nif = nif;
		this.cuentaContable = cuentaContable;
		this.tipoIgic = tipoIgic;
		this.tipoIrpf = tipoIrpf;
		this.idZona = idZona;
		this.idBanco = idBanco;
		this.idSucursal = idSucursal;
		this.digitoControl = digitoControl;
		this.cuentaBancaria = cuentaBancaria;
                this.iban = iban;
		this.ocm = ocm;
		this.grupoPladimisa = grupoPladimisa;
		this.codigoIne = codigoIne;
		this.idGrupo = idGrupo;
		this.codigoAsesoria = codigoAsesoria;
		this.numKilosReferencia = numKilosReferencia;
		this.email = email;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public CosecherosId getId() {
		return this.id;
	}

	public void setId(CosecherosId id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
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

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
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
        
	public Short getOcm() {
		return this.ocm;
	}

	public void setOcm(Short ocm) {
		this.ocm = ocm;
	}

	public Short getGrupoPladimisa() {
		return this.grupoPladimisa;
	}

	public void setGrupoPladimisa(Short grupoPladimisa) {
		this.grupoPladimisa = grupoPladimisa;
	}

	public String getCodigoIne() {
		return this.codigoIne;
	}

	public void setCodigoIne(String codigoIne) {
		this.codigoIne = codigoIne;
	}

	public Integer getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getCodigoAsesoria() {
		return this.codigoAsesoria;
	}

	public void setCodigoAsesoria(Integer codigoAsesoria) {
		this.codigoAsesoria = codigoAsesoria;
	}

	public Float getNumKilosReferencia() {
		return this.numKilosReferencia;
	}

	public void setNumKilosReferencia(Float numKilosReferencia) {
		this.numKilosReferencia = numKilosReferencia;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
