package entitiesPackage;

/**
 * Viewventascategoriasreceptorquery entity. @author MyEclipse Persistence Tools
 */

public class Viewventascategoriasreceptorquery implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewventascategoriasreceptorqueryId id;

	// Constructors

	/** default constructor */
	public Viewventascategoriasreceptorquery() {
	}

	/** full constructor */
	public Viewventascategoriasreceptorquery(
			ViewventascategoriasreceptorqueryId id) {
		this.id = id;
	}

	// Property accessors

	public ViewventascategoriasreceptorqueryId getId() {
		return this.id;
	}

	public void setId(ViewventascategoriasreceptorqueryId id) {
		this.id = id;
	}

}