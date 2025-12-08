package entitiesPackage;

/**
 * Viewentradascategoriasquery entity. @author MyEclipse Persistence Tools
 */

public class Viewentradascategoriasquery implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewentradascategoriasqueryId id;

	// Constructors

	/** default constructor */
	public Viewentradascategoriasquery() {
	}

	/** full constructor */
	public Viewentradascategoriasquery(ViewentradascategoriasqueryId id) {
		this.id = id;
	}

	// Property accessors

	public ViewentradascategoriasqueryId getId() {
		return this.id;
	}

	public void setId(ViewentradascategoriasqueryId id) {
		this.id = id;
	}

}