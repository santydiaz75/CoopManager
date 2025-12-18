package entitiesPackage;

/**
 * Viewentradascategoriasquery entity. @author MyEclipse Persistence Tools
 */

public class Viewentradascategoriasquery implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewentradascategoriasqueryId id;


	/** default constructor */
	public Viewentradascategoriasquery() {
	}

	/** full constructor */
	public Viewentradascategoriasquery(ViewentradascategoriasqueryId id) {
		this.id = id;
	}


	public ViewentradascategoriasqueryId getId() {
		return this.id;
	}

	public void setId(ViewentradascategoriasqueryId id) {
		this.id = id;
	}

}
