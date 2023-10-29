package entitiesPackage;

import java.util.Date;

/**
 * Empleadoshorasextras entity. @author MyEclipse Persistence Tools
 */

public class Empleadoshorasextras implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private EmpleadoshorasextrasId id;
	private Float horasExtras;
	private Float horasDescontadas;
	private Float precio;
	private Float importe;
	private Short sabado;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Empleadoshorasextras() {
	}

	/** minimal constructor */
	public Empleadoshorasextras(EmpleadoshorasextrasId id, Float horasExtras,
			Float horasDescontadas, Float precio, Float importe, Short sabado) {
		this.id = id;
		this.horasExtras = horasExtras;
		this.horasDescontadas = horasDescontadas;
		this.precio = precio;
		this.importe = importe;
		this.sabado = sabado;
	}

	/** full constructor */
	public Empleadoshorasextras(EmpleadoshorasextrasId id, Float horasExtras,
			Float horasDescontadas, Float precio, Float importe, Short sabado,
			String comentarios, String sid, Date lmd, Integer version) {
		this.id = id;
		this.horasExtras = horasExtras;
		this.horasDescontadas = horasDescontadas;
		this.precio = precio;
		this.importe = importe;
		this.sabado = sabado;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public EmpleadoshorasextrasId getId() {
		return this.id;
	}

	public void setId(EmpleadoshorasextrasId id) {
		this.id = id;
	}

	public Float getHorasExtras() {
		return this.horasExtras;
	}

	public void setHorasExtras(Float horasExtras) {
		this.horasExtras = horasExtras;
	}

	public Float getHorasDescontadas() {
		return this.horasDescontadas;
	}

	public void setHorasDescontadas(Float horasDescontadas) {
		this.horasDescontadas = horasDescontadas;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Short getSabado() {
		return this.sabado;
	}

	public void setSabado(Short sabado) {
		this.sabado = sabado;
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