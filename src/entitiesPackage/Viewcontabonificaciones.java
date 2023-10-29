package entitiesPackage;

/**
 * Viewcontabonificaciones entity. @author MyEclipse Persistence Tools
 */

public class Viewcontabonificaciones implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontabonificacionesId id;

	// Constructors

	/** default constructor */
	public Viewcontabonificaciones() {
	}

	/** full constructor */
	public Viewcontabonificaciones(ViewcontabonificacionesId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontabonificacionesId getId() {
		return this.id;
	}

	public void setId(ViewcontabonificacionesId id) {
		this.id = id;
	}

}