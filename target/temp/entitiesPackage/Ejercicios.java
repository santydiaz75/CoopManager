package entitiesPackage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Ejercicios entity. @author MyEclipse Persistence Tools
 */

public class Ejercicios implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ejercicio;
	private Date desdeFecha;
	private Date hastaFecha;
	private String comentarios;
	private String sid;
	private Date lmd;
	private Integer version;
	private Set<Object> empleadosvacacioneses = new HashSet<Object>(0);
	private Set<Object> facturaslineases = new HashSet<Object>(0);
	private Set<Object> empleadoshorasextrases = new HashSet<Object>(0);
	private Set<Object> calendarios = new HashSet<Object>(0);
	private Set<Object> facturascabeceras = new HashSet<Object>(0);
	private Set<Object> categoriases = new HashSet<Object>(0);
	private Set<Object> vehiculosgastoses = new HashSet<Object>(0);
	private Set<Object> liquidacioneses = new HashSet<Object>(0);
	private Set<Object> entradascabeceras = new HashSet<Object>(0);
	private Set<Object> ventaslineases = new HashSet<Object>(0);
	private Set<Object> identidadeses = new HashSet<Object>(0);
	private Set<Object> conductoreses = new HashSet<Object>(0);
	private Set<Object> ventascabeceras = new HashSet<Object>(0);
	private Set<Object> cosecheroses = new HashSet<Object>(0);
	private Set<Object> bancoses = new HashSet<Object>(0);
	private Set<Object> vehiculoses = new HashSet<Object>(0);
	private Set<Object> entradaslineases = new HashSet<Object>(0);
	private Set<Object> tiposgastos = new HashSet<Object>(0);
	private Set<Object> paiseses = new HashSet<Object>(0);
	private Set<Object> bimestreses = new HashSet<Object>(0);
	private Set<Object> empleadosnominases = new HashSet<Object>(0);
	private Set<Object> receptoreses = new HashSet<Object>(0);
	private Set<Object> zonases = new HashSet<Object>(0);
	private Set<Object> precioses = new HashSet<Object>(0);
	private Set<Object> barcoses = new HashSet<Object>(0);
	private Set<Object> puertoses = new HashSet<Object>(0);

	// Constructors

	/** default constructor */
	public Ejercicios() {
	}

	/** minimal constructor */
	public Ejercicios(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	/** full constructor */
	public Ejercicios(Integer ejercicio, Date desdeFecha,
			Date hastaFecha, String comentarios, String sid,
			Date lmd, Integer version, Set<Object> empleadosvacacioneses,
			Set<Object> facturaslineases, Set<Object> empleadoshorasextrases, Set<Object> calendarios,
			Set<Object> facturascabeceras, Set<Object> categoriases, Set<Object> vehiculosgastoses,
			Set<Object> liquidacioneses, Set<Object> entradascabeceras, Set<Object> ventaslineases,
			Set<Object> identidadeses, Set<Object> conductoreses, Set<Object> ventascabeceras,
			Set<Object> cosecheroses, Set<Object> bancoses, Set<Object> vehiculoses,
			Set<Object> entradaslineases, Set<Object> tiposgastos, Set<Object> paiseses,
			Set<Object> bimestreses, Set<Object> empleadosnominases, Set<Object> receptoreses,
			Set<Object> zonases, Set<Object> precioses, Set<Object> barcoses, Set<Object> puertoses) {
		this.ejercicio = ejercicio;
		this.desdeFecha = desdeFecha;
		this.hastaFecha = hastaFecha;
		this.comentarios = comentarios;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
		this.empleadosvacacioneses = empleadosvacacioneses;
		this.facturaslineases = facturaslineases;
		this.empleadoshorasextrases = empleadoshorasextrases;
		this.calendarios = calendarios;
		this.facturascabeceras = facturascabeceras;
		this.categoriases = categoriases;
		this.vehiculosgastoses = vehiculosgastoses;
		this.liquidacioneses = liquidacioneses;
		this.entradascabeceras = entradascabeceras;
		this.ventaslineases = ventaslineases;
		this.identidadeses = identidadeses;
		this.conductoreses = conductoreses;
		this.ventascabeceras = ventascabeceras;
		this.cosecheroses = cosecheroses;
		this.bancoses = bancoses;
		this.vehiculoses = vehiculoses;
		this.entradaslineases = entradaslineases;
		this.tiposgastos = tiposgastos;
		this.paiseses = paiseses;
		this.bimestreses = bimestreses;
		this.empleadosnominases = empleadosnominases;
		this.receptoreses = receptoreses;
		this.zonases = zonases;
		this.precioses = precioses;
		this.barcoses = barcoses;
		this.puertoses = puertoses;
	}

	// Property accessors

	public Integer getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getDesdeFecha() {
		return this.desdeFecha;
	}

	public void setDesdeFecha(Date desdeFecha) {
		this.desdeFecha = desdeFecha;
	}

	public Date getHastaFecha() {
		return this.hastaFecha;
	}

	public void setHastaFecha(Date hastaFecha) {
		this.hastaFecha = hastaFecha;
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

	public Set<Object> getEmpleadosvacacioneses() {
		return this.empleadosvacacioneses;
	}

	public void setEmpleadosvacacioneses(Set<Object> empleadosvacacioneses) {
		this.empleadosvacacioneses = empleadosvacacioneses;
	}

	public Set<Object> getFacturaslineases() {
		return this.facturaslineases;
	}

	public void setFacturaslineases(Set<Object> facturaslineases) {
		this.facturaslineases = facturaslineases;
	}

	public Set<Object> getEmpleadoshorasextrases() {
		return this.empleadoshorasextrases;
	}

	public void setEmpleadoshorasextrases(Set<Object> empleadoshorasextrases) {
		this.empleadoshorasextrases = empleadoshorasextrases;
	}

	public Set<Object> getCalendarios() {
		return this.calendarios;
	}

	public void setCalendarios(Set<Object> calendarios) {
		this.calendarios = calendarios;
	}

	public Set<Object> getFacturascabeceras() {
		return this.facturascabeceras;
	}

	public void setFacturascabeceras(Set<Object> facturascabeceras) {
		this.facturascabeceras = facturascabeceras;
	}

	public Set<Object> getCategoriases() {
		return this.categoriases;
	}

	public void setCategoriases(Set<Object> categoriases) {
		this.categoriases = categoriases;
	}

	public Set<Object> getVehiculosgastoses() {
		return this.vehiculosgastoses;
	}

	public void setVehiculosgastoses(Set<Object> vehiculosgastoses) {
		this.vehiculosgastoses = vehiculosgastoses;
	}

	public Set<Object> getLiquidacioneses() {
		return this.liquidacioneses;
	}

	public void setLiquidacioneses(Set<Object> liquidacioneses) {
		this.liquidacioneses = liquidacioneses;
	}

	public Set<Object> getEntradascabeceras() {
		return this.entradascabeceras;
	}

	public void setEntradascabeceras(Set<Object> entradascabeceras) {
		this.entradascabeceras = entradascabeceras;
	}

	public Set<Object> getVentaslineases() {
		return this.ventaslineases;
	}

	public void setVentaslineases(Set<Object> ventaslineases) {
		this.ventaslineases = ventaslineases;
	}

	public Set<Object> getIdentidadeses() {
		return this.identidadeses;
	}

	public void setIdentidadeses(Set<Object> identidadeses) {
		this.identidadeses = identidadeses;
	}

	public Set<Object> getConductoreses() {
		return this.conductoreses;
	}

	public void setConductoreses(Set<Object> conductoreses) {
		this.conductoreses = conductoreses;
	}

	public Set<Object> getVentascabeceras() {
		return this.ventascabeceras;
	}

	public void setVentascabeceras(Set<Object> ventascabeceras) {
		this.ventascabeceras = ventascabeceras;
	}

	public Set<Object> getCosecheroses() {
		return this.cosecheroses;
	}

	public void setCosecheroses(Set<Object> cosecheroses) {
		this.cosecheroses = cosecheroses;
	}

	public Set<Object> getBancoses() {
		return this.bancoses;
	}

	public void setBancoses(Set<Object> bancoses) {
		this.bancoses = bancoses;
	}

	public Set<Object> getVehiculoses() {
		return this.vehiculoses;
	}

	public void setVehiculoses(Set<Object> vehiculoses) {
		this.vehiculoses = vehiculoses;
	}

	public Set<Object> getEntradaslineases() {
		return this.entradaslineases;
	}

	public void setEntradaslineases(Set<Object> entradaslineases) {
		this.entradaslineases = entradaslineases;
	}

	public Set<Object> getTiposgastos() {
		return this.tiposgastos;
	}

	public void setTiposgastos(Set<Object> tiposgastos) {
		this.tiposgastos = tiposgastos;
	}

	public Set<Object> getPaiseses() {
		return this.paiseses;
	}

	public void setPaiseses(Set<Object> paiseses) {
		this.paiseses = paiseses;
	}

	public Set<Object> getBimestreses() {
		return this.bimestreses;
	}

	public void setBimestreses(Set<Object> bimestreses) {
		this.bimestreses = bimestreses;
	}

	public Set<Object> getEmpleadosnominases() {
		return this.empleadosnominases;
	}

	public void setEmpleadosnominases(Set<Object> empleadosnominases) {
		this.empleadosnominases = empleadosnominases;
	}

	public Set<Object> getReceptoreses() {
		return this.receptoreses;
	}

	public void setReceptoreses(Set<Object> receptoreses) {
		this.receptoreses = receptoreses;
	}

	public Set<Object> getZonases() {
		return this.zonases;
	}

	public void setZonases(Set<Object> zonases) {
		this.zonases = zonases;
	}

	public Set<Object> getPrecioses() {
		return this.precioses;
	}

	public void setPrecioses(Set<Object> precioses) {
		this.precioses = precioses;
	}

	public Set<Object> getBarcoses() {
		return this.barcoses;
	}

	public void setBarcoses(Set<Object> barcoses) {
		this.barcoses = barcoses;
	}

	public Set<Object> getPuertoses() {
		return this.puertoses;
	}

	public void setPuertoses(Set<Object> puertoses) {
		this.puertoses = puertoses;
	}

}