package entitiesPackage;

import java.sql.Timestamp;

/**
 * Conceptospago entity. @author MyEclipse Persistence Tools
 */

public class Conceptospago implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConceptospagoId id;
	private String conceptoPagoDesc;
	private String cuentaContable;
	private String sid;
	private Timestamp lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Conceptospago() {
	}

	/** minimal constructor */
	public Conceptospago(ConceptospagoId id) {
		this.id = id;
	}

	/** full constructor */
	public Conceptospago(ConceptospagoId id, String conceptoPagoDesc,
			String cuentaContable, String sid, Timestamp lmd, Integer version) {
		this.id = id;
		this.conceptoPagoDesc = conceptoPagoDesc;
		this.cuentaContable = cuentaContable;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public ConceptospagoId getId() {
		return this.id;
	}

	public void setId(ConceptospagoId id) {
		this.id = id;
	}

	public String getConceptoPagoDesc() {
		return this.conceptoPagoDesc;
	}

	public void setConceptoPagoDesc(String conceptoPagoDesc) {
		this.conceptoPagoDesc = conceptoPagoDesc;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Timestamp getLmd() {
		return this.lmd;
	}

	public void setLmd(Timestamp lmd) {
		this.lmd = lmd;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}