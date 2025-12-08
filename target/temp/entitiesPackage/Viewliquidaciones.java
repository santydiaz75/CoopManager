package entitiesPackage;

/**
 * Viewliquidaciones entity. @author MyEclipse Persistence Tools
 */

public class Viewliquidaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewliquidacionesId id;

	// Constructors

	/** default constructor */
	public Viewliquidaciones() {
	}

	/** full constructor */
	public Viewliquidaciones(ViewliquidacionesId id) {
		this.id = id;
	}

	// Property accessors

	public ViewliquidacionesId getId() {
		return this.id;
	}

	public void setId(ViewliquidacionesId id) {
		this.id = id;
	}

}