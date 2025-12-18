package entitiesPackage;

import java.util.Date;

/**
 * Categorias entity. @author MyEclipse Persistence Tools
 */

public class Categorias implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CategoriasId id;
	private String nombreCategoria;
	private Float numKilosCaja;
	private Integer idSubcategoria;
	private Short privada;
	private Integer orden;
	private String codCategoriaAgriten;
	private Short activa;
	private String sid;
	private Date lmd;
	private Integer version;
    private Short retorno;
    private Integer idGrupo;


	/** default constructor */
	public Categorias() {
	}

	/** minimal constructor */
	public Categorias(CategoriasId id, Short privada, Short activa, Short retorno) {
		this.id = id;
		this.privada = privada;
		this.activa = activa;
        this.retorno = retorno;
        this.idGrupo = id.getIdCategoria();
	}

	/** full constructor */
	public Categorias(CategoriasId id, String nombreCategoria,
			Float numKilosCaja, Integer idSubcategoria, Short privada,
			Integer orden, String codCategoriaAgriten, Short activa,
			String sid, Date lmd, Integer version, Short retorno, Integer idGrupo) {
		this.id = id;
		this.nombreCategoria = nombreCategoria;
		this.numKilosCaja = numKilosCaja;
		this.idSubcategoria = idSubcategoria;
		this.privada = privada;
		this.orden = orden;
		this.codCategoriaAgriten = codCategoriaAgriten;
		this.activa = activa;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
        this.retorno = retorno;
        this.idGrupo = idGrupo;
	}


	public CategoriasId getId() {
		return this.id;
	}

	public void setId(CategoriasId id) {
		this.id = id;
	}

	public String getNombreCategoria() {
		return this.nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public Float getNumKilosCaja() {
		return this.numKilosCaja;
	}

	public void setNumKilosCaja(Float numKilosCaja) {
		this.numKilosCaja = numKilosCaja;
	}

	public Integer getIdSubcategoria() {
		return this.idSubcategoria;
	}

	public void setIdSubcategoria(Integer idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	public Short getPrivada() {
		return this.privada;
	}

	public void setPrivada(Short privada) {
		this.privada = privada;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getCodCategoriaAgriten() {
		return this.codCategoriaAgriten;
	}
	
	public Integer getIdGrupo() {
		return this.idGrupo;
	}
	
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public void setCodCategoriaAgriten(String codCategoriaAgriten) {
		this.codCategoriaAgriten = codCategoriaAgriten;
	}

	public Short getActiva() {
		return this.activa;
	}

	public void setActiva(Short activa) {
		this.activa = activa;
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
        
        public Short getRetorno() {
		return this.retorno;
	}

	public void setRetorno(Short retorno) {
		this.retorno = retorno;
	}
}
