package entitiesPackage;

import java.util.Date;

/**
 * Cosecherosparcelas entity. @author MyEclipse Persistence Tools
 */

public class Cosecherosparcelas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private CosecherosparcelasId id;
	private String municipio;
	private String paraje;
	private String poligono;
	private String parcela;
	private String recinto;
	private Float superficie;
	private Float superficieCultivada;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Cosecherosparcelas() {
	}

	/** minimal constructor */
	public Cosecherosparcelas(CosecherosparcelasId id) {
		this.id = id;
	}

	/** full constructor */
	public Cosecherosparcelas(CosecherosparcelasId id, String municipio,
			String paraje, String poligono, String parcela, String recinto,
			Float superficie, Float superficieCultivada, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.municipio = municipio;
		this.paraje = paraje;
		this.poligono = poligono;
		this.parcela = parcela;
		this.recinto = recinto;
		this.superficie = superficie;
		this.superficieCultivada = superficieCultivada;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public CosecherosparcelasId getId() {
		return this.id;
	}

	public void setId(CosecherosparcelasId id) {
		this.id = id;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getParaje() {
		return this.paraje;
	}

	public void setParaje(String paraje) {
		this.paraje = paraje;
	}

	public String getPoligono() {
		return this.poligono;
	}

	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	public String getParcela() {
		return this.parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getRecinto() {
		return this.recinto;
	}

	public void setRecinto(String recinto) {
		this.recinto = recinto;
	}

	public Float getSuperficie() {
		return this.superficie;
	}

	public void setSuperficie(Float superficie) {
		this.superficie = superficie;
	}

	public Float getSuperficieCultivada() {
		return this.superficieCultivada;
	}

	public void setSuperficieCultivada(Float superficieCultivada) {
		this.superficieCultivada = superficieCultivada;
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