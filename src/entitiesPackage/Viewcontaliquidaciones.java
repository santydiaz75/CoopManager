package entitiesPackage;

/**
 * Viewcontaliquidaciones entity. @author MyEclipse Persistence Tools
 */

public class Viewcontaliquidaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontaliquidacionesId id;

	// Constructors

	/** default constructor */
	public Viewcontaliquidaciones() {
	}

	/** full constructor */
	public Viewcontaliquidaciones(ViewcontaliquidacionesId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontaliquidacionesId getId() {
		return this.id;
	}

	public void setId(ViewcontaliquidacionesId id) {
		this.id = id;
	}

}