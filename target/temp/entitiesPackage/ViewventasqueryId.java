package entitiesPackage;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ViewventasqueryId entity. @author MyEclipse Persistence Tools
 */

public class ViewventasqueryId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idVenta;
	private Integer empresa;
	private Integer ejercicio;
	private Integer semana;
	private Timestamp fecha;
	private Integer idReceptor;
	private String nombre;
	private Short mercadoLocal;
	private BigDecimal numCajas;
	private Double numKilos;
	private Double importe;

	// Constructors

	/** default constructor */
	public ViewventasqueryId() {
	}

	/** minimal constructor */
	public ViewventasqueryId(Integer idVenta, Integer empresa, Integer ejercicio) {
		this.idVenta = idVenta;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
	}

	/** full constructor */
	public ViewventasqueryId(Integer idVenta, Integer empresa,
			Integer ejercicio, Integer semana, Timestamp fecha,
			Integer idReceptor, String nombre, Short mercadoLocal,
			BigDecimal numCajas, Double numKilos, Double importe) {
		this.idVenta = idVenta;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.semana = semana;
		this.fecha = fecha;
		this.idReceptor = idReceptor;
		this.nombre = nombre;
		this.mercadoLocal = mercadoLocal;
		this.numCajas = numCajas;
		this.numKilos = numKilos;
		this.importe = importe;
	}

	// Property accessors

	public Integer getIdVenta() {
		return this.idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Integer getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Integer getIdReceptor() {
		return this.idReceptor;
	}

	public void setIdReceptor(Integer idReceptor) {
		this.idReceptor = idReceptor;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Short getMercadoLocal() {
		return this.mercadoLocal;
	}

	public void setMercadoLocal(Short mercadoLocal) {
		this.mercadoLocal = mercadoLocal;
	}

	public BigDecimal getNumCajas() {
		return this.numCajas;
	}

	public void setNumCajas(BigDecimal numCajas) {
		this.numCajas = numCajas;
	}

	public Double getNumKilos() {
		return this.numKilos;
	}

	public void setNumKilos(Double numKilos) {
		this.numKilos = numKilos;
	}

	public Double getImporte() {
		return this.importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewventasqueryId))
			return false;
		ViewventasqueryId castOther = (ViewventasqueryId) other;

		return ((this.getIdVenta() == castOther.getIdVenta()) || (this
				.getIdVenta() != null
				&& castOther.getIdVenta() != null && this.getIdVenta().equals(
				castOther.getIdVenta())))
				&& ((this.getEmpresa() == castOther.getEmpresa()) || (this
						.getEmpresa() != null
						&& castOther.getEmpresa() != null && this.getEmpresa()
						.equals(castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getSemana() == castOther.getSemana()) || (this
						.getSemana() != null
						&& castOther.getSemana() != null && this.getSemana()
						.equals(castOther.getSemana())))
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null
						&& castOther.getFecha() != null && this.getFecha()
						.equals(castOther.getFecha())))
				&& ((this.getIdReceptor() == castOther.getIdReceptor()) || (this
						.getIdReceptor() != null
						&& castOther.getIdReceptor() != null && this
						.getIdReceptor().equals(castOther.getIdReceptor())))
				&& ((this.getNombre() == castOther.getNombre()) || (this
						.getNombre() != null
						&& castOther.getNombre() != null && this.getNombre()
						.equals(castOther.getNombre())))
				&& ((this.getMercadoLocal() == castOther.getMercadoLocal()) || (this
						.getMercadoLocal() != null
						&& castOther.getMercadoLocal() != null && this
						.getMercadoLocal().equals(castOther.getMercadoLocal())))
				&& ((this.getNumCajas() == castOther.getNumCajas()) || (this
						.getNumCajas() != null
						&& castOther.getNumCajas() != null && this
						.getNumCajas().equals(castOther.getNumCajas())))
				&& ((this.getNumKilos() == castOther.getNumKilos()) || (this
						.getNumKilos() != null
						&& castOther.getNumKilos() != null && this
						.getNumKilos().equals(castOther.getNumKilos())))
				&& ((this.getImporte() == castOther.getImporte()) || (this
						.getImporte() != null
						&& castOther.getImporte() != null && this.getImporte()
						.equals(castOther.getImporte())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdVenta() == null ? 0 : this.getIdVenta().hashCode());
		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		result = 37
				* result
				+ (getIdReceptor() == null ? 0 : this.getIdReceptor()
						.hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		result = 37
				* result
				+ (getMercadoLocal() == null ? 0 : this.getMercadoLocal()
						.hashCode());
		result = 37 * result
				+ (getNumCajas() == null ? 0 : this.getNumCajas().hashCode());
		result = 37 * result
				+ (getNumKilos() == null ? 0 : this.getNumKilos().hashCode());
		result = 37 * result
				+ (getImporte() == null ? 0 : this.getImporte().hashCode());
		return result;
	}

}