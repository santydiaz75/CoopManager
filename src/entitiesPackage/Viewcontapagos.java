package entitiesPackage;

/**
 * Viewcontapagos entity. @author MyEclipse Persistence Tools
 */

public class Viewcontapagos implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontapagosId id;

	// Constructors

	/** default constructor */
	public Viewcontapagos() {
	}

	/** full constructor */
	public Viewcontapagos(ViewcontapagosId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontapagosId getId() {
		return this.id;
	}

	public void setId(ViewcontapagosId id) {
		this.id = id;
	}

}