package entitiesPackage;

/**
 * Viewcontacobros entity. @author MyEclipse Persistence Tools
 */

public class Viewcontacobros implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontacobrosId id;

	// Constructors

	/** default constructor */
	public Viewcontacobros() {
	}

	/** full constructor */
	public Viewcontacobros(ViewcontacobrosId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontacobrosId getId() {
		return this.id;
	}

	public void setId(ViewcontacobrosId id) {
		this.id = id;
	}

}