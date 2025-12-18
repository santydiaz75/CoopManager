package entitiesPackage;

/**
 * ViewentradascategoriascosecheroqueryId entity. @author MyEclipse Persistence
 * Tools
 */

public class ViewentradascategoriascosecheroqueryId implements
		java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Integer idCosechero;
	private Integer semana;
	private Integer idCategoria;
	private String nombreCategoria;
	private Double numKilos;


	/** default constructor */
	public ViewentradascategoriascosecheroqueryId() {
	}

	/** minimal constructor */
	public ViewentradascategoriascosecheroqueryId(Integer empresa,
			Integer ejercicio, Integer idCategoria) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.idCategoria = idCategoria;
	}

	/** full constructor */
	public ViewentradascategoriascosecheroqueryId(Integer empresa,
			Integer ejercicio, Integer idCosechero, Integer semana,
			Integer idCategoria, String nombreCategoria, Double numKilos) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.idCosechero = idCosechero;
		this.semana = semana;
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.numKilos = numKilos;
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

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewentradascategoriascosecheroqueryId))
			return false;
		ViewentradascategoriascosecheroqueryId castOther = (ViewentradascategoriascosecheroqueryId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getIdCosechero() == castOther.getIdCosechero()) || (this
						.getIdCosechero() != null
						&& castOther.getIdCosechero() != null && this
						.getIdCosechero().equals(castOther.getIdCosechero())))
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
						.getNumKilos().equals(castOther.getNumKilos())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37
				* result
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
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
		return result;
	}

}
