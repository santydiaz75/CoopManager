package entitiesPackage;

import java.util.Date;

/**
 * Ventascabecera entity. @author MyEclipse Persistence Tools
 */

public class Ventascabecera implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private VentascabeceraId id;
	private Date fecha;
	private Integer idReceptor;
	private Integer idBarco;
	private Integer idPuerto;
	private String plataforma;
	private Integer semana;
	private Integer idVehiculo;
	private Integer idConductor;
	private Integer idZona;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Ventascabecera() {
	}

	/** minimal constructor */
	public Ventascabecera(VentascabeceraId id) {
		this.id = id;
	}

	/** full constructor */
	public Ventascabecera(VentascabeceraId id, Date fecha,
			Integer idReceptor, Integer idBarco, Integer idPuerto,
			String plataforma, Integer semana, Integer idVehiculo,
			Integer idConductor, Integer idZona, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.fecha = fecha;
		this.idReceptor = idReceptor;
		this.idBarco = idBarco;
		this.idPuerto = idPuerto;
		this.plataforma = plataforma;
		this.semana = semana;
		this.idVehiculo = idVehiculo;
		this.idConductor = idConductor;
		this.idZona = idZona;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public VentascabeceraId getId() {
		return this.id;
	}

	public void setId(VentascabeceraId id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdReceptor() {
		return this.idReceptor;
	}

	public void setIdReceptor(Integer idReceptor) {
		this.idReceptor = idReceptor;
	}

	public Integer getIdBarco() {
		return this.idBarco;
	}

	public void setIdBarco(Integer idBarco) {
		this.idBarco = idBarco;
	}

	public Integer getIdPuerto() {
		return this.idPuerto;
	}

	public void setIdPuerto(Integer idPuerto) {
		this.idPuerto = idPuerto;
	}

	public String getPlataforma() {
		return this.plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Integer getIdConductor() {
		return this.idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
	}

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
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