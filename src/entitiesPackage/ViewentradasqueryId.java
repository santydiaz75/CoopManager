package entitiesPackage;

import java.sql.Timestamp;

/**
 * ViewentradasqueryId entity. @author MyEclipse Persistence Tools
 */

public class ViewentradasqueryId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEntrada;
	private Integer empresa;
	private Integer ejercicio;
	private Integer semana;
	private Timestamp fecha;
	private Integer idCosechero;
	private String nombre;
	private String apellidos;
	private Float numPinas;
	private Double numKilos;

	// Constructors

	/** default constructor */
	public ViewentradasqueryId() {
	}

	/** minimal constructor */
	public ViewentradasqueryId(Integer idEntrada, Integer empresa,
			Integer ejercicio) {
		this.idEntrada = idEntrada;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
	}

	/** full constructor */
	public ViewentradasqueryId(Integer idEntrada, Integer empresa,
			Integer ejercicio, Integer semana, Timestamp fecha,
			Integer idCosechero, String nombre, String apellidos,
			Float numPinas, Double numKilos) {
		this.idEntrada = idEntrada;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.semana = semana;
		this.fecha = fecha;
		this.idCosechero = idCosechero;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.numPinas = numPinas;
		this.numKilos = numKilos;
	}

	// Property accessors

	public Integer getIdEntrada() {
		return this.idEntrada;
	}

	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
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

	public Integer getIdCosechero() {
		return this.idCosechero;
	}

	public void setIdCosechero(Integer idCosechero) {
		this.idCosechero = idCosechero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Float getNumPinas() {
		return this.numPinas;
	}

	public void setNumPinas(Float numPinas) {
		this.numPinas = numPinas;
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
		if (!(other instanceof ViewentradasqueryId))
			return false;
		ViewentradasqueryId castOther = (ViewentradasqueryId) other;

		return ((this.getIdEntrada() == castOther.getIdEntrada()) || (this
				.getIdEntrada() != null
				&& castOther.getIdEntrada() != null && this.getIdEntrada()
				.equals(castOther.getIdEntrada())))
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
				&& ((this.getIdCosechero() == castOther.getIdCosechero()) || (this
						.getIdCosechero() != null
						&& castOther.getIdCosechero() != null && this
						.getIdCosechero().equals(castOther.getIdCosechero())))
				&& ((this.getNombre() == castOther.getNombre()) || (this
						.getNombre() != null
						&& castOther.getNombre() != null && this.getNombre()
						.equals(castOther.getNombre())))
				&& ((this.getApellidos() == castOther.getApellidos()) || (this
						.getApellidos() != null
						&& castOther.getApellidos() != null && this
						.getApellidos().equals(castOther.getApellidos())))
				&& ((this.getNumPinas() == castOther.getNumPinas()) || (this
						.getNumPinas() != null
						&& castOther.getNumPinas() != null && this
						.getNumPinas().equals(castOther.getNumPinas())))
				&& ((this.getNumKilos() == castOther.getNumKilos()) || (this
						.getNumKilos() != null
						&& castOther.getNumKilos() != null && this
						.getNumKilos().equals(castOther.getNumKilos())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdEntrada() == null ? 0 : this.getIdEntrada().hashCode());
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
				+ (getIdCosechero() == null ? 0 : this.getIdCosechero()
						.hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		result = 37 * result
				+ (getApellidos() == null ? 0 : this.getApellidos().hashCode());
		result = 37 * result
				+ (getNumPinas() == null ? 0 : this.getNumPinas().hashCode());
		result = 37 * result
				+ (getNumKilos() == null ? 0 : this.getNumKilos().hashCode());
		return result;
	}

}