package entitiesPackage;

/**
 * Viewventasquery entity. @author MyEclipse Persistence Tools
 */

public class Viewventasquery implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewventasqueryId id;

	// Constructors

	/** default constructor */
	public Viewventasquery() {
	}

	/** full constructor */
	public Viewventasquery(ViewventasqueryId id) {
		this.id = id;
	}

	// Property accessors

	public ViewventasqueryId getId() {
		return this.id;
	}

	public void setId(ViewventasqueryId id) {
		this.id = id;
	}

}