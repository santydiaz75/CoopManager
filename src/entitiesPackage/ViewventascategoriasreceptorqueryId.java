package entitiesPackage;

import java.math.BigDecimal;

/**
 * ViewventascategoriasreceptorqueryId entity. @author MyEclipse Persistence
 * Tools
 */

public class ViewventascategoriasreceptorqueryId implements
		java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Integer idReceptor;
	private Short mercadoLocal;
	private Integer semana;
	private Integer idCategoria;
	private String nombreCategoria;
	private Double numKilos;
	private BigDecimal numCajas;
	private Double impore;


	/** default constructor */
	public ViewventascategoriasreceptorqueryId() {
	}

	/** minimal constructor */
	public ViewventascategoriasreceptorqueryId(Integer empresa,
			Integer ejercicio, Short mercadoLocal, Integer idCategoria) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mercadoLocal = mercadoLocal;
		this.idCategoria = idCategoria;
	}

	/** full constructor */
	public ViewventascategoriasreceptorqueryId(Integer empresa,
			Integer ejercicio, Integer idReceptor, Short mercadoLocal,
			Integer semana, Integer idCategoria, String nombreCategoria,
			Double numKilos, BigDecimal numCajas, Double impore) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.idReceptor = idReceptor;
		this.mercadoLocal = mercadoLocal;
		this.semana = semana;
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.numKilos = numKilos;
		this.numCajas = numCajas;
		this.impore = impore;
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

	public Integer getIdReceptor() {
		return this.idReceptor;
	}

	public void setIdReceptor(Integer idReceptor) {
		this.idReceptor = idReceptor;
	}

	public Short getMercadoLocal() {
		return this.mercadoLocal;
	}

	public void setMercadoLocal(Short mercadoLocal) {
		this.mercadoLocal = mercadoLocal;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return this.nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public Double getNumKilos() {
		return this.numKilos;
	}

	public void setNumKilos(Double numKilos) {
		this.numKilos = numKilos;
	}

	public BigDecimal getNumCajas() {
		return this.numCajas;
	}

	public void setNumCajas(BigDecimal numCajas) {
		this.numCajas = numCajas;
	}

	public Double getImpore() {
		return this.impore;
	}

	public void setImpore(Double impore) {
		this.impore = impore;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewventascategoriasreceptorqueryId))
			return false;
		ViewventascategoriasreceptorqueryId castOther = (ViewventascategoriasreceptorqueryId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getIdReceptor() == castOther.getIdReceptor()) || (this
						.getIdReceptor() != null
						&& castOther.getIdReceptor() != null && this
						.getIdReceptor().equals(castOther.getIdReceptor())))
				&& ((this.getMercadoLocal() == castOther.getMercadoLocal()) || (this
						.getMercadoLocal() != null
						&& castOther.getMercadoLocal() != null && this
						.getMercadoLocal().equals(castOther.getMercadoLocal())))
				&& ((this.getSemana() == castOther.getSemana()) || (this
						.getSemana() != null
						&& castOther.getSemana() != null && this.getSemana()
						.equals(castOther.getSemana())))
				&& ((this.getIdCategoria() == castOther.getIdCategoria()) || (this
						.getIdCategoria() != null
						&& castOther.getIdCategoria() != null && this
						.getIdCategoria().equals(castOther.getIdCategoria())))
				&& ((this.getNombreCategoria() == castOther
						.getNombreCategoria()) || (this.getNombreCategoria() != null
						&& castOther.getNombreCategoria() != null && this
						.getNombreCategoria().equals(
								castOther.getNombreCategoria())))
				&& ((this.getNumKilos() == castOther.getNumKilos()) || (this
						.getNumKilos() != null
						&& castOther.getNumKilos() != null && this
						.getNumKilos().equals(castOther.getNumKilos())))
				&& ((this.getNumCajas() == castOther.getNumCajas()) || (this
						.getNumCajas() != null
						&& castOther.getNumCajas() != null && this
						.getNumCajas().equals(castOther.getNumCajas())))
				&& ((this.getImpore() == castOther.getImpore()) || (this
						.getImpore() != null
						&& castOther.getImpore() != null && this.getImpore()
						.equals(castOther.getImpore())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37
				* result
				+ (getIdReceptor() == null ? 0 : this.getIdReceptor()
						.hashCode());
		result = 37
				* result
				+ (getMercadoLocal() == null ? 0 : this.getMercadoLocal()
						.hashCode());
		result = 37 * result
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		result = 37
				* result
				+ (getIdCategoria() == null ? 0 : this.getIdCategoria()
						.hashCode());
		result = 37
				* result
				+ (getNombreCategoria() == null ? 0 : this.getNombreCategoria()
						.hashCode());
		result = 37 * result
				+ (getNumKilos() == null ? 0 : this.getNumKilos().hashCode());
		result = 37 * result
				+ (getNumCajas() == null ? 0 : this.getNumCajas().hashCode());
		result = 37 * result
				+ (getImpore() == null ? 0 : this.getImpore().hashCode());
		return result;
	}

}
