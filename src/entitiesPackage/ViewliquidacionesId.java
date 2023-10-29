package entitiesPackage;

import java.sql.Timestamp;

/**
 * ViewliquidacionesId entity. @author MyEclipse Persistence Tools
 */

public class ViewliquidacionesId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer numerofactura;
	private Integer empresa;
	private Integer ejercicio;
	private Integer mes;
	private Timestamp fecha;
	private String nombre;
	private String apellidos;
	private Long tipoIrpf;
	private Long tipoIgic;
	private Float baseImponible;
	private Float importeIgic;
	private Float importeIrpf;

	// Constructors

	/** default constructor */
	public ViewliquidacionesId() {
	}

	/** minimal constructor */
	public ViewliquidacionesId(Integer numerofactura, Integer empresa,
			Integer ejercicio, Float baseImponible, Float importeIgic,
			Float importeIrpf) {
		this.numerofactura = numerofactura;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
	}

	/** full constructor */
	public ViewliquidacionesId(Integer numerofactura, Integer empresa,
			Integer ejercicio, Integer mes, Timestamp fecha, String nombre,
			String apellidos, Long tipoIrpf, Long tipoIgic,
			Float baseImponible, Float importeIgic, Float importeIrpf) {
		this.numerofactura = numerofactura;
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.fecha = fecha;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipoIrpf = tipoIrpf;
		this.tipoIgic = tipoIgic;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
	}

	// Property accessors

	public Integer getNumerofactura() {
		return this.numerofactura;
	}

	public void setNumerofactura(Integer numerofactura) {
		this.numerofactura = numerofactura;
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

	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
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

	public Long getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Long tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
	}

	public Long getTipoIgic() {
		return this.tipoIgic;
	}

	public void setTipoIgic(Long tipoIgic) {
		this.tipoIgic = tipoIgic;
	}

	public Float getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(Float baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Float getImporteIgic() {
		return this.importeIgic;
	}

	public void setImporteIgic(Float importeIgic) {
		this.importeIgic = importeIgic;
	}

	public Float getImporteIrpf() {
		return this.importeIrpf;
	}

	public void setImporteIrpf(Float importeIrpf) {
		this.importeIrpf = importeIrpf;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewliquidacionesId))
			return false;
		ViewliquidacionesId castOther = (ViewliquidacionesId) other;

		return ((this.getNumerofactura() == castOther.getNumerofactura()) || (this
				.getNumerofactura() != null
				&& castOther.getNumerofactura() != null && this
				.getNumerofactura().equals(castOther.getNumerofactura())))
				&& ((this.getEmpresa() == castOther.getEmpresa()) || (this
						.getEmpresa() != null
						&& castOther.getEmpresa() != null && this.getEmpresa()
						.equals(castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getMes() == castOther.getMes()) || (this.getMes() != null
						&& castOther.getMes() != null && this.getMes().equals(
						castOther.getMes())))
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null
						&& castOther.getFecha() != null && this.getFecha()
						.equals(castOther.getFecha())))
				&& ((this.getNombre() == castOther.getNombre()) || (this
						.getNombre() != null
						&& castOther.getNombre() != null && this.getNombre()
						.equals(castOther.getNombre())))
				&& ((this.getApellidos() == castOther.getApellidos()) || (this
						.getApellidos() != null
						&& castOther.getApellidos() != null && this
						.getApellidos().equals(castOther.getApellidos())))
				&& ((this.getTipoIrpf() == castOther.getTipoIrpf()) || (this
						.getTipoIrpf() != null
						&& castOther.getTipoIrpf() != null && this
						.getTipoIrpf().equals(castOther.getTipoIrpf())))
				&& ((this.getTipoIgic() == castOther.getTipoIgic()) || (this
						.getTipoIgic() != null
						&& castOther.getTipoIgic() != null && this
						.getTipoIgic().equals(castOther.getTipoIgic())))
				&& ((this.getBaseImponible() == castOther.getBaseImponible()) || (this
						.getBaseImponible() != null
						&& castOther.getBaseImponible() != null && this
						.getBaseImponible()
						.equals(castOther.getBaseImponible())))
				&& ((this.getImporteIgic() == castOther.getImporteIgic()) || (this
						.getImporteIgic() != null
						&& castOther.getImporteIgic() != null && this
						.getImporteIgic().equals(castOther.getImporteIgic())))
				&& ((this.getImporteIrpf() == castOther.getImporteIrpf()) || (this
						.getImporteIrpf() != null
						&& castOther.getImporteIrpf() != null && this
						.getImporteIrpf().equals(castOther.getImporteIrpf())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNumerofactura() == null ? 0 : this.getNumerofactura()
						.hashCode());
		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result
				+ (getMes() == null ? 0 : this.getMes().hashCode());
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		result = 37 * result
				+ (getNombre() == null ? 0 : this.getNombre().hashCode());
		result = 37 * result
				+ (getApellidos() == null ? 0 : this.getApellidos().hashCode());
		result = 37 * result
				+ (getTipoIrpf() == null ? 0 : this.getTipoIrpf().hashCode());
		result = 37 * result
				+ (getTipoIgic() == null ? 0 : this.getTipoIgic().hashCode());
		result = 37
				* result
				+ (getBaseImponible() == null ? 0 : this.getBaseImponible()
						.hashCode());
		result = 37
				* result
				+ (getImporteIgic() == null ? 0 : this.getImporteIgic()
						.hashCode());
		result = 37
				* result
				+ (getImporteIrpf() == null ? 0 : this.getImporteIrpf()
						.hashCode());
		return result;
	}

}