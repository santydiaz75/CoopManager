package entitiesPackage;

/**
 * Viewcontaindemnizaciones entity. @author MyEclipse Persistence Tools
 */

public class Viewcontaindemnizaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontaindemnizacionesId id;

	// Constructors

	/** default constructor */
	public Viewcontaindemnizaciones() {
	}

	/** full constructor */
	public Viewcontaindemnizaciones(ViewcontaindemnizacionesId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontaindemnizacionesId getId() {
		return this.id;
	}

	public void setId(ViewcontaindemnizacionesId id) {
		this.id = id;
	}

}