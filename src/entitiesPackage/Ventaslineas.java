package entitiesPackage;

import java.util.Date;

/**
 * Ventaslineas entity. @author MyEclipse Persistence Tools
 */

public class Ventaslineas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VentaslineasId id;
	private Integer numCajas;
	private Float numKilos;
	private Float importe;
	private Float precio;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Ventaslineas() {
	}

	/** minimal constructor */
	public Ventaslineas(VentaslineasId id) {
		this.id = id;
	}

	/** full constructor */
	public Ventaslineas(VentaslineasId id, Integer numCajas, Float numKilos,
			Float importe, Float precio, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.numCajas = numCajas;
		this.numKilos = numKilos;
		this.importe = importe;
		this.precio = precio;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public VentaslineasId getId() {
		return this.id;
	}

	public void setId(VentaslineasId id) {
		this.id = id;
	}

	public Integer getNumCajas() {
		return this.numCajas;
	}

	public void setNumCajas(Integer numCajas) {
		this.numCajas = numCajas;
	}

	public Float getNumKilos() {
		return this.numKilos;
	}

	public void setNumKilos(Float numKilos) {
		this.numKilos = numKilos;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
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
