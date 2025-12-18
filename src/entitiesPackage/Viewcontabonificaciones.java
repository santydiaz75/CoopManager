package entitiesPackage;

/**
 * Viewcontabonificaciones entity. @author MyEclipse Persistence Tools
 */

public class Viewcontabonificaciones implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontabonificacionesId id;


	/** default constructor */
	public Viewcontabonificaciones() {
	}

	/** full constructor */
	public Viewcontabonificaciones(ViewcontabonificacionesId id) {
		this.id = id;
	}


	public ViewcontabonificacionesId getId() {
		return this.id;
	}

	public void setId(ViewcontabonificacionesId id) {
		this.id = id;
	}

}
