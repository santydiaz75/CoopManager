package entitiesPackage;

import java.sql.Timestamp;

/**
 * ViewcontaliquidacionesId entity. @author MyEclipse Persistence Tools
 */

public class ViewcontaliquidacionesId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empresa;
	private Integer ejercicio;
	private Integer mes;
	private String cuentaContable;
	private Timestamp fecha;
	private String concepto;
	private Float baseImponible;
	private Float importeIgic;
	private Float importeIrpf;
	private Integer tipoIgic;
	private Integer tipoIrpf;
	private Float importeBonificacion;

	// Constructors

	/** default constructor */
	public ViewcontaliquidacionesId() {
	}

	/** minimal constructor */
	public ViewcontaliquidacionesId(Integer empresa, Integer ejercicio,
			Float baseImponible, Float importeIgic, Float importeIrpf, Float importeBonificacion) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
		this.importeBonificacion = importeBonificacion;
	}

	/** full constructor */
	public ViewcontaliquidacionesId(Integer empresa, Integer ejercicio,
			Integer mes, String cuentaContable, Timestamp fecha,
			String concepto, Float baseImponible, Float importeIgic,
			Float importeIrpf, Integer tipoIgic, Integer tipoIrpf, Float importeBonificacion) {
		this.empresa = empresa;
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.cuentaContable = cuentaContable;
		this.fecha = fecha;
		this.concepto = concepto;
		this.baseImponible = baseImponible;
		this.importeIgic = importeIgic;
		this.importeIrpf = importeIrpf;
		this.tipoIgic = tipoIgic;
		this.tipoIrpf = tipoIrpf;
		this.importeBonificacion = importeBonificacion;
	}

	// Property accessors

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

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
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

	public Integer getTipoIgic() {
		return this.tipoIgic;
	}

	public void setTipoIgic(Integer tipoIgic) {
		this.tipoIgic = tipoIgic;
	}

	public Integer getTipoIrpf() {
		return this.tipoIrpf;
	}

	public void setTipoIrpf(Integer tipoIrpf) {
		this.tipoIrpf = tipoIrpf;
	}
	
	public Float getImporteBonificacion() {
		return this.importeBonificacion;
	}

	public void setImporteBonificacion(Float importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewcontaliquidacionesId))
			return false;
		ViewcontaliquidacionesId castOther = (ViewcontaliquidacionesId) other;

		return ((this.getEmpresa() == castOther.getEmpresa()) || (this
				.getEmpresa() != null
				&& castOther.getEmpresa() != null && this.getEmpresa().equals(
				castOther.getEmpresa())))
				&& ((this.getEjercicio() == castOther.getEjercicio()) || (this
						.getEjercicio() != null
						&& castOther.getEjercicio() != null && this
						.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getMes() == castOther.getMes()) || (this.getMes() != null
						&& castOther.getMes() != null && this.getMes().equals(
						castOther.getMes())))
				&& ((this.getCuentaContable() == castOther.getCuentaContable()) || (this
						.getCuentaContable() != null
						&& castOther.getCuentaContable() != null && this
						.getCuentaContable().equals(
								castOther.getCuentaContable())))
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null
						&& castOther.getFecha() != null && this.getFecha()
						.equals(castOther.getFecha())))
				&& ((this.getConcepto() == castOther.getConcepto()) || (this
						.getConcepto() != null
						&& castOther.getConcepto() != null && this
						.getConcepto().equals(castOther.getConcepto())))
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
						.getImporteIrpf().equals(castOther.getImporteIrpf())))
				&& ((this.getTipoIgic() == castOther.getTipoIgic()) || (this
						.getTipoIgic() != null
						&& castOther.getTipoIgic() != null && this
						.getTipoIgic().equals(castOther.getTipoIgic())))
				&& ((this.getTipoIrpf() == castOther.getTipoIrpf()) || (this
						.getTipoIrpf() != null
						&& castOther.getTipoIrpf() != null && this
						.getTipoIrpf().equals(castOther.getTipoIrpf())))
				&& ((this.getImporteBonificacion() == castOther.getImporteBonificacion()) || (this
						.getImporteBonificacion() != null
						&& castOther.getImporteBonificacion() != null && this
						.getImporteBonificacion().equals(castOther.getImporteBonificacion())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresa() == null ? 0 : this.getEmpresa().hashCode());
		result = 37 * result
				+ (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result
				+ (getMes() == null ? 0 : this.getMes().hashCode());
		result = 37
				* result
				+ (getCuentaContable() == null ? 0 : this.getCuentaContable()
						.hashCode());
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		result = 37 * result
				+ (getConcepto() == null ? 0 : this.getConcepto().hashCode());
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
		result = 37 * result
				+ (getTipoIgic() == null ? 0 : this.getTipoIgic().hashCode());
		result = 37 * result
				+ (getTipoIrpf() == null ? 0 : this.getTipoIrpf().hashCode());
		result = 37 * result
				+ (getImporteBonificacion() == null ? 0 : this.getImporteBonificacion().hashCode());
		return result;
	}

}