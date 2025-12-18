package entitiesPackage;

/**
 * Viewventasquery entity. @author MyEclipse Persistence Tools
 */

public class Viewventasquery implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewventasqueryId id;


	/** default constructor */
	public Viewventasquery() {
	}

	/** full constructor */
	public Viewventasquery(ViewventasqueryId id) {
		this.id = id;
	}


	public ViewventasqueryId getId() {
		return this.id;
	}

	public void setId(ViewventasqueryId id) {
		this.id = id;
	}

}
