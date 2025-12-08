package entitiesPackage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Empresas entity. @author MyEclipse Persistence Tools
 */

public class Empresas implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEmpresa;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String provincia;
	private String codigoPostal;
	private String nif;
	private String telefono;
	private String fax;
	private String correoElectronico;
	private Short activada;
        private String lopd;
	private String sid;
	private Date lmd;
	private Integer version;
	private Set<Object> vehiculosgastoses = new HashSet<Object>(0);
	private Set<Object> vehiculoses = new HashSet<Object>(0);
	private Set<Object> identidadeses = new HashSet<Object>(0);
	private Set<Object> receptoreses = new HashSet<Object>(0);
	private Set<Object> facturascabeceras = new HashSet<Object>(0);
	private Set<Object> barcoses = new HashSet<Object>(0);
	private Set<Object> empleadosnominases = new HashSet<Object>(0);
	private Set<Object> paiseses = new HashSet<Object>(0);
	private Set<Object> empresascuentases = new HashSet<Object>(0);
	private Set<Object> cosecherosparcelases = new HashSet<Object>(0);
	private Set<Object> conductoreses = new HashSet<Object>(0);
	private Set<Object> conceptoses = new HashSet<Object>(0);
	private Set<Object> facturaspagocabeceras = new HashSet<Object>(0);
	private Set<Object> liquidacioneses = new HashSet<Object>(0);
	private Set<Object> puertoses = new HashSet<Object>(0);
	private Set<Object> facturaspagolineases = new HashSet<Object>(0);
	private Set<Object> conceptospagos = new HashSet<Object>(0);
	private Set<Object> empleadoscontratoses = new HashSet<Object>(0);
	private Set<Object> entradascabeceras = new HashSet<Object>(0);
	private Set<Object> empleadoses = new HashSet<Object>(0);
	private Set<Object> kilosinutilizadoses = new HashSet<Object>(0);
	private Set<Object> bimestreses = new HashSet<Object>(0);
	private Set<Object> ventascabeceras = new HashSet<Object>(0);
	private Set<Object> bancoses = new HashSet<Object>(0);
	private Set<Object> precioses = new HashSet<Object>(0);
	private Set<Object> facturaslineases = new HashSet<Object>(0);
	private Set<Object> ayudases = new HashSet<Object>(0);
	private Set<Object> empleadoshorasextrases = new HashSet<Object>(0);
	private Set<Object> entradasimportacions = new HashSet<Object>(0);
	private Set<Object> calendarios = new HashSet<Object>(0);
	private Set<Object> liquidacioneslineases = new HashSet<Object>(0);
	private Set<Object> cosecheroses = new HashSet<Object>(0);
	private Set<Object> tiposgastos = new HashSet<Object>(0);
	private Set<Object> entradaslineases = new HashSet<Object>(0);
	private Set<Object> ventaslineases = new HashSet<Object>(0);
	private Set<Object> categoriases = new HashSet<Object>(0);
	private Set<Object> empleadosvacacioneses = new HashSet<Object>(0);
	private Set<Object> zonases = new HashSet<Object>(0);

	// Constructors

	/** default constructor */
	public Empresas() {
	}

	/** minimal constructor */
	public Empresas(Integer idEmpresa, Short activada) {
		this.idEmpresa = idEmpresa;
		this.activada = activada;
	}

	/** full constructor */
	public Empresas(Integer idEmpresa, String nombre, String direccion,
			String poblacion, String provincia, String codigoPostal,
			String nif, String telefono, String fax, String correoElectronico,
			Short activada, String lopd, String sid, Date lmd, Integer version,
			Set<Object> vehiculosgastoses, Set<Object> vehiculoses, Set<Object> identidadeses,
			Set<Object> receptoreses, Set<Object> facturascabeceras, Set<Object> barcoses,
			Set<Object> empleadosnominases, Set<Object> paiseses, Set<Object> empresascuentases,
			Set<Object> cosecherosparcelases, Set<Object> conductoreses, Set<Object> conceptoses,
			Set<Object> facturaspagocabeceras, Set<Object> liquidacioneses, Set<Object> puertoses,
			Set<Object> facturaspagolineases, Set<Object> conceptospagos,
			Set<Object> empleadoscontratoses, Set<Object> entradascabeceras, Set<Object> empleadoses,
			Set<Object> kilosinutilizadoses, Set<Object> bimestreses, Set<Object> ventascabeceras,
			Set<Object> bancoses, Set<Object> precioses, Set<Object> facturaslineases, Set<Object> ayudases,
			Set<Object> empleadoshorasextrases, Set<Object> entradasimportacions,
			Set<Object> calendarios, Set<Object> liquidacioneslineases, Set<Object> cosecheroses,
			Set<Object> tiposgastos, Set<Object> entradaslineases, Set<Object> ventaslineases,
			Set<Object> categoriases, Set<Object> empleadosvacacioneses, Set<Object> zonases) {
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.nif = nif;
		this.telefono = telefono;
		this.fax = fax;
		this.correoElectronico = correoElectronico;
		this.activada = activada;
                this.lopd = lopd;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
		this.vehiculosgastoses = vehiculosgastoses;
		this.vehiculoses = vehiculoses;
		this.identidadeses = identidadeses;
		this.receptoreses = receptoreses;
		this.facturascabeceras = facturascabeceras;
		this.barcoses = barcoses;
		this.empleadosnominases = empleadosnominases;
		this.paiseses = paiseses;
		this.empresascuentases = empresascuentases;
		this.cosecherosparcelases = cosecherosparcelases;
		this.conductoreses = conductoreses;
		this.conceptoses = conceptoses;
		this.facturaspagocabeceras = facturaspagocabeceras;
		this.liquidacioneses = liquidacioneses;
		this.puertoses = puertoses;
		this.facturaspagolineases = facturaspagolineases;
		this.conceptospagos = conceptospagos;
		this.empleadoscontratoses = empleadoscontratoses;
		this.entradascabeceras = entradascabeceras;
		this.empleadoses = empleadoses;
		this.kilosinutilizadoses = kilosinutilizadoses;
		this.bimestreses = bimestreses;
		this.ventascabeceras = ventascabeceras;
		this.bancoses = bancoses;
		this.precioses = precioses;
		this.facturaslineases = facturaslineases;
		this.ayudases = ayudases;
		this.empleadoshorasextrases = empleadoshorasextrases;
		this.entradasimportacions = entradasimportacions;
		this.calendarios = calendarios;
		this.liquidacioneslineases = liquidacioneslineases;
		this.cosecheroses = cosecheroses;
		this.tiposgastos = tiposgastos;
		this.entradaslineases = entradaslineases;
		this.ventaslineases = ventaslineases;
		this.categoriases = categoriases;
		this.empleadosvacacioneses = empleadosvacacioneses;
		this.zonases = zonases;
	}

	// Property accessors

	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
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

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Short getActivada() {
		return this.activada;
	}

	public void setActivada(Short activada) {
		this.activada = activada;
	}
        
        public String getLopd() {
		return this.lopd;
	}

	public void setLopd(String lopd) {
		this.lopd = lopd;
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

	public Set<Object> getVehiculosgastoses() {
		return this.vehiculosgastoses;
	}

	public void setVehiculosgastoses(Set<Object> vehiculosgastoses) {
		this.vehiculosgastoses = vehiculosgastoses;
	}

	public Set<Object> getVehiculoses() {
		return this.vehiculoses;
	}

	public void setVehiculoses(Set<Object> vehiculoses) {
		this.vehiculoses = vehiculoses;
	}

	public Set<Object> getIdentidadeses() {
		return this.identidadeses;
	}

	public void setIdentidadeses(Set<Object> identidadeses) {
		this.identidadeses = identidadeses;
	}

	public Set<Object> getReceptoreses() {
		return this.receptoreses;
	}

	public void setReceptoreses(Set<Object> receptoreses) {
		this.receptoreses = receptoreses;
	}

	public Set<Object> getFacturascabeceras() {
		return this.facturascabeceras;
	}

	public void setFacturascabeceras(Set<Object> facturascabeceras) {
		this.facturascabeceras = facturascabeceras;
	}

	public Set<Object> getBarcoses() {
		return this.barcoses;
	}

	public void setBarcoses(Set<Object> barcoses) {
		this.barcoses = barcoses;
	}

	public Set<Object> getEmpleadosnominases() {
		return this.empleadosnominases;
	}

	public void setEmpleadosnominases(Set<Object> empleadosnominases) {
		this.empleadosnominases = empleadosnominases;
	}

	public Set<Object> getPaiseses() {
		return this.paiseses;
	}

	public void setPaiseses(Set<Object> paiseses) {
		this.paiseses = paiseses;
	}

	public Set<Object> getEmpresascuentases() {
		return this.empresascuentases;
	}

	public void setEmpresascuentases(Set<Object> empresascuentases) {
		this.empresascuentases = empresascuentases;
	}

	public Set<Object> getCosecherosparcelases() {
		return this.cosecherosparcelases;
	}

	public void setCosecherosparcelases(Set<Object> cosecherosparcelases) {
		this.cosecherosparcelases = cosecherosparcelases;
	}

	public Set<Object> getConductoreses() {
		return this.conductoreses;
	}

	public void setConductoreses(Set<Object> conductoreses) {
		this.conductoreses = conductoreses;
	}

	public Set<Object> getConceptoses() {
		return this.conceptoses;
	}

	public void setConceptoses(Set<Object> conceptoses) {
		this.conceptoses = conceptoses;
	}

	public Set<Object> getFacturaspagocabeceras() {
		return this.facturaspagocabeceras;
	}

	public void setFacturaspagocabeceras(Set<Object> facturaspagocabeceras) {
		this.facturaspagocabeceras = facturaspagocabeceras;
	}

	public Set<Object> getLiquidacioneses() {
		return this.liquidacioneses;
	}

	public void setLiquidacioneses(Set<Object> liquidacioneses) {
		this.liquidacioneses = liquidacioneses;
	}

	public Set<Object> getPuertoses() {
		return this.puertoses;
	}

	public void setPuertoses(Set<Object> puertoses) {
		this.puertoses = puertoses;
	}

	public Set<Object> getFacturaspagolineases() {
		return this.facturaspagolineases;
	}

	public void setFacturaspagolineases(Set<Object> facturaspagolineases) {
		this.facturaspagolineases = facturaspagolineases;
	}

	public Set<Object> getConceptospagos() {
		return this.conceptospagos;
	}

	public void setConceptospagos(Set<Object> conceptospagos) {
		this.conceptospagos = conceptospagos;
	}

	public Set<Object> getEmpleadoscontratoses() {
		return this.empleadoscontratoses;
	}

	public void setEmpleadoscontratoses(Set<Object> empleadoscontratoses) {
		this.empleadoscontratoses = empleadoscontratoses;
	}

	public Set<Object> getEntradascabeceras() {
		return this.entradascabeceras;
	}

	public void setEntradascabeceras(Set<Object> entradascabeceras) {
		this.entradascabeceras = entradascabeceras;
	}

	public Set<Object> getEmpleadoses() {
		return this.empleadoses;
	}

	public void setEmpleadoses(Set<Object> empleadoses) {
		this.empleadoses = empleadoses;
	}

	public Set<Object> getKilosinutilizadoses() {
		return this.kilosinutilizadoses;
	}

	public void setKilosinutilizadoses(Set<Object> kilosinutilizadoses) {
		this.kilosinutilizadoses = kilosinutilizadoses;
	}

	public Set<Object> getBimestreses() {
		return this.bimestreses;
	}

	public void setBimestreses(Set<Object> bimestreses) {
		this.bimestreses = bimestreses;
	}

	public Set<Object> getVentascabeceras() {
		return this.ventascabeceras;
	}

	public void setVentascabeceras(Set<Object> ventascabeceras) {
		this.ventascabeceras = ventascabeceras;
	}

	public Set<Object> getBancoses() {
		return this.bancoses;
	}

	public void setBancoses(Set<Object> bancoses) {
		this.bancoses = bancoses;
	}

	public Set<Object> getPrecioses() {
		return this.precioses;
	}

	public void setPrecioses(Set<Object> precioses) {
		this.precioses = precioses;
	}

	public Set<Object> getFacturaslineases() {
		return this.facturaslineases;
	}

	public void setFacturaslineases(Set<Object> facturaslineases) {
		this.facturaslineases = facturaslineases;
	}

	public Set<Object> getAyudases() {
		return this.ayudases;
	}

	public void setAyudases(Set<Object> ayudases) {
		this.ayudases = ayudases;
	}

	public Set<Object> getEmpleadoshorasextrases() {
		return this.empleadoshorasextrases;
	}

	public void setEmpleadoshorasextrases(Set<Object> empleadoshorasextrases) {
		this.empleadoshorasextrases = empleadoshorasextrases;
	}

	public Set<Object> getEntradasimportacions() {
		return this.entradasimportacions;
	}

	public void setEntradasimportacions(Set<Object> entradasimportacions) {
		this.entradasimportacions = entradasimportacions;
	}

	public Set<Object> getCalendarios() {
		return this.calendarios;
	}

	public void setCalendarios(Set<Object> calendarios) {
		this.calendarios = calendarios;
	}

	public Set<Object> getLiquidacioneslineases() {
		return this.liquidacioneslineases;
	}

	public void setLiquidacioneslineases(Set<Object> liquidacioneslineases) {
		this.liquidacioneslineases = liquidacioneslineases;
	}

	public Set<Object> getCosecheroses() {
		return this.cosecheroses;
	}

	public void setCosecheroses(Set<Object> cosecheroses) {
		this.cosecheroses = cosecheroses;
	}

	public Set<Object> getTiposgastos() {
		return this.tiposgastos;
	}

	public void setTiposgastos(Set<Object> tiposgastos) {
		this.tiposgastos = tiposgastos;
	}

	public Set<Object> getEntradaslineases() {
		return this.entradaslineases;
	}

	public void setEntradaslineases(Set<Object> entradaslineases) {
		this.entradaslineases = entradaslineases;
	}

	public Set<Object> getVentaslineases() {
		return this.ventaslineases;
	}

	public void setVentaslineases(Set<Object> ventaslineases) {
		this.ventaslineases = ventaslineases;
	}

	public Set<Object> getCategoriases() {
		return this.categoriases;
	}

	public void setCategoriases(Set<Object> categoriases) {
		this.categoriases = categoriases;
	}

	public Set<Object> getEmpleadosvacacioneses() {
		return this.empleadosvacacioneses;
	}

	public void setEmpleadosvacacioneses(Set<Object> empleadosvacacioneses) {
		this.empleadosvacacioneses = empleadosvacacioneses;
	}

	public Set<Object> getZonases() {
		return this.zonases;
	}

	public void setZonases(Set<Object> zonases) {
		this.zonases = zonases;
	}

}