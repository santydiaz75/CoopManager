package entitiesPackage;

import java.util.Date;

/**
 * EntradasimportacionId entity. @author MyEclipse Persistence Tools
 */

public class EntradasimportacionId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private Ejercicios ejercicios;
	private Integer semana;
	private Date fecha;
	private Integer idCosechero;
	private Float numPinas;
	private Integer idCategoria;
	private Float numKilos;
	private String vale;

	// Constructors

	/** default constructor */
	public EntradasimportacionId() {
	}

	/** minimal constructor */
	public EntradasimportacionId(Empresas empresas, Ejercicios ejercicios) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
	}

	/** full constructor */
	public EntradasimportacionId(Empresas empresas, Ejercicios ejercicios,
			Integer semana, Date fecha, Integer idCosechero,
			Float numPinas, Integer idCategoria, Float numKilos, String vale) {
		this.empresas = empresas;
		this.ejercicios = ejercicios;
		this.semana = semana;
		this.fecha = fecha;
		this.idCosechero = idCosechero;
		this.numPinas = numPinas;
		this.idCategoria = idCategoria;
		this.numKilos = numKilos;
		this.vale = vale;
	}

	// Property accessors

	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public Ejercicios getEjercicios() {
		return this.ejercicios;
	}

	public void setEjercicios(Ejercicios ejercicios) {
		this.ejercicios = ejercicios;
	}

	public Integer getSemana() {
		return this.semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
	}

	public Float getNumPinas() {
		return this.numPinas;
	}

	public void setNumPinas(Float numPinas) {
		this.numPinas = numPinas;
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Float getNumKilos() {
		return this.numKilos;
	}

	public void setNumKilos(Float numKilos) {
		this.numKilos = numKilos;
	}

	public String getVale() {
		return this.vale;
	}

	public void setVale(String vale) {
		this.vale = vale;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EntradasimportacionId))
			return false;
		EntradasimportacionId castOther = (EntradasimportacionId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getEjercicios() == castOther.getEjercicios()) || (this
						.getEjercicios() != null
						&& castOther.getEjercicios() != null && this
						.getEjercicios().equals(castOther.getEjercicios())))
				&& ((this.getSemana() == castOther.getSemana()) || (this
						.getSemana() != null
						&& castOther.getSemana() != null && this.getSemana()
						.equals(castOther.getSemana())))
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null
						&& castOther.getFecha() != null && this.getFecha()
						.equals(castOther.getFecha())))
				&& ((this.getIdCosechero() == castOther.getIdCosechero()) || (this
						.getIdCosechero() != null
						&& castOther.getIdCosechero() != null && this
						.getIdCosechero().equals(castOther.getIdCosechero())))
				&& ((this.getNumPinas() == castOther.getNumPinas()) || (this
						.getNumPinas() != null
						&& castOther.getNumPinas() != null && this
						.getNumPinas().equals(castOther.getNumPinas())))
				&& ((this.getIdCategoria() == castOther.getIdCategoria()) || (this
						.getIdCategoria() != null
						&& castOther.getIdCategoria() != null && this
						.getIdCategoria().equals(castOther.getIdCategoria())))
				&& ((this.getNumKilos() == castOther.getNumKilos()) || (this
						.getNumKilos() != null
						&& castOther.getNumKilos() != null && this
						.getNumKilos().equals(castOther.getNumKilos())))
				&& ((this.getVale() == castOther.getVale()) || (this.getVale() != null
						&& castOther.getVale() != null && this.getVale()
						.equals(castOther.getVale())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getEjercicios() == null ? 0 : this.getEjercicios()
						.hashCode());
		result = 37 * result
				+ (getSemana() == null ? 0 : this.getSemana().hashCode());
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		result = 37
				* result
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
						.hashCode());
		result = 37 * result
				+ (getNumPinas() == null ? 0 : this.getNumPinas().hashCode());
		result = 37
				* result
				+ (getIdCategoria() == null ? 0 : this.getIdCategoria()
						.hashCode());
		result = 37 * result
				+ (getNumKilos() == null ? 0 : this.getNumKilos().hashCode());
		result = 37 * result
				+ (getVale() == null ? 0 : this.getVale().hashCode());
		return result;
	}

}