package entitiesPackage;

import java.util.Date;


/**
 * Cobros entity. @author MyEclipse Persistence Tools
 */

public class Cobros  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

     private CobrosId id;
     private Date fechaCobro;
     private String concepto;
     private String cuentaBancaria;
     private Float importe;
     private String sid;
     private Date lmd;
     private Integer version;



    /** default constructor */
    public Cobros() {
    }

	/** minimal constructor */
    public Cobros(CobrosId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Cobros(CobrosId id, Date fechaCobro, String concepto, String cuentaBancaria, Float importe, String sid, Date lmd, Integer version) {
        this.id = id;
        this.fechaCobro = fechaCobro;
        this.concepto = concepto;
        this.cuentaBancaria = cuentaBancaria;
        this.importe = importe;
        this.sid = sid;
        this.lmd = lmd;
        this.version = version;
    }

   

    public CobrosId getId() {
        return this.id;
    }
    
    public void setId(CobrosId id) {
        this.id = id;
    }

    public Date getFechaCobro() {
        return this.fechaCobro;
    }
    
    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getConcepto() {
        return this.concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCuentaBancaria() {
        return this.cuentaBancaria;
    }
    
    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Float getImporte() {
        return this.importe;
    }
    
    public void setImporte(Float importe) {
        this.importe = importe;
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
