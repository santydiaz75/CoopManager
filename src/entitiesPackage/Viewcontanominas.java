package entitiesPackage;

/**
 * Viewcontanominas entity. @author MyEclipse Persistence Tools
 */

public class Viewcontanominas implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewcontanominasId id;

	// Constructors

	/** default constructor */
	public Viewcontanominas() {
	}

	/** full constructor */
	public Viewcontanominas(ViewcontanominasId id) {
		this.id = id;
	}

	// Property accessors

	public ViewcontanominasId getId() {
		return this.id;
	}

	public void setId(ViewcontanominasId id) {
		this.id = id;
	}

}