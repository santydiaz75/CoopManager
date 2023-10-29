package entitiesPackage;

/**
 * Viewentradasquery entity. @author MyEclipse Persistence Tools
 */

public class Viewentradasquery implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewentradasqueryId id;

	// Constructors

	/** default constructor */
	public Viewentradasquery() {
	}

	/** full constructor */
	public Viewentradasquery(ViewentradasqueryId id) {
		this.id = id;
	}

	// Property accessors

	public ViewentradasqueryId getId() {
		return this.id;
	}

	public void setId(ViewentradasqueryId id) {
		this.id = id;
	}

}