package entitiesPackage;

import java.util.Date;

/**
 * Vehiculosgastos entity. @author MyEclipse Persistence Tools
 */

public class Vehiculosgastos implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VehiculosgastosId id;
	private Integer idTipoGasto;
	private Integer mes;
	private Float importe;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Vehiculosgastos() {
	}

	/** minimal constructor */
	public Vehiculosgastos(VehiculosgastosId id, Float importe) {
		this.id = id;
		this.importe = importe;
	}

	/** full constructor */
	public Vehiculosgastos(VehiculosgastosId id, Integer idTipoGasto,
			Integer mes, Float importe, String comentarios, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.idTipoGasto = idTipoGasto;
		this.mes = mes;
		this.importe = importe;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public VehiculosgastosId getId() {
		return this.id;
	}

	public void setId(VehiculosgastosId id) {
		this.id = id;
	}

	public Integer getIdTipoGasto() {
		return this.idTipoGasto;
	}

	public void setIdTipoGasto(Integer idTipoGasto) {
		this.idTipoGasto = idTipoGasto;
	}

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
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
