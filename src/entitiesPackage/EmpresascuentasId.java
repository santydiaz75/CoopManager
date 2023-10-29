package entitiesPackage;

/**
 * EmpresascuentasId entity. @author MyEclipse Persistence Tools
 */

public class EmpresascuentasId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
	private String cuentaBancaria;

	// Constructors

	/** default constructor */
	public EmpresascuentasId() {
	}

	/** full constructor */
	public EmpresascuentasId(Empresas empresas, String cuentaBancaria) {
		this.empresas = empresas;
		this.cuentaBancaria = cuentaBancaria;
	}

	// Property accessors

	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public String getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmpresascuentasId))
			return false;
		EmpresascuentasId castOther = (EmpresascuentasId) other;

		return ((this.getEmpresas() == castOther.getEmpresas()) || (this
				.getEmpresas() != null
				&& castOther.getEmpresas() != null && this.getEmpresas()
				.equals(castOther.getEmpresas())))
				&& ((this.getCuentaBancaria() == castOther.getCuentaBancaria()) || (this
						.getCuentaBancaria() != null
						&& castOther.getCuentaBancaria() != null && this
						.getCuentaBancaria().equals(
								castOther.getCuentaBancaria())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpresas() == null ? 0 : this.getEmpresas().hashCode());
		result = 37
				* result
				+ (getCuentaBancaria() == null ? 0 : this.getCuentaBancaria()
						.hashCode());
		return result;
	}

}