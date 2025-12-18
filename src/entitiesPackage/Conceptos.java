package entitiesPackage;

import java.util.Date;

/**
 * Conceptos entity. @author MyEclipse Persistence Tools
 */

public class Conceptos implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConceptosId id;
	private String conceptoDesc;
	private String cuentaContable;
	private Short conIgic;
	private String sid;
	private Date lmd;
	private Integer version;


	/** default constructor */
	public Conceptos() {
	}

	/** minimal constructor */
	public Conceptos(ConceptosId id, Short conIgic) {
		this.id = id;
		this.conIgic = conIgic;
	}

	/** full constructor */
	public Conceptos(ConceptosId id, String conceptoDesc,
			String cuentaContable, Short conIgic, String sid, Date lmd,
			Integer version) {
		this.id = id;
		this.conceptoDesc = conceptoDesc;
		this.cuentaContable = cuentaContable;
		this.conIgic = conIgic;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}


	public ConceptosId getId() {
		return this.id;
	}

	public void setId(ConceptosId id) {
		this.id = id;
	}

	public String getConceptoDesc() {
		return this.conceptoDesc;
	}

	public void setConceptoDesc(String conceptoDesc) {
		this.conceptoDesc = conceptoDesc;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Short getConIgic() {
		return this.conIgic;
	}

	public void setConIgic(Short conIgic) {
		this.conIgic = conIgic;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getLmd() {
		return this.lmd;
	}

	public void setLmd(Date lmd) {
		this.lmd = lmd;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
