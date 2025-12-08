package entitiesPackage;

import java.util.Date;

/**
 * Liquidacioneslineas entity. @author MyEclipse Persistence Tools
 */

public class Liquidacioneslineas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private LiquidacioneslineasId id;
	private Float pinasSemana1;
	private Float pinasSemana2;
	private Float pinasSemana3;
	private Float pinasSemana4;
	private Float pinasSemana5;
	private Float precioSemana1;
	private Float precioSemana2;
	private Float precioSemana3;
	private Float precioSemana4;
	private Float precioSemana5;
	private Float kilosSemana1;
	private Float kilosSemana2;
	private Float kilosSemana3;
	private Float kilosSemana4;
	private Float kilosSemana5;
	private Float kilosInutSemana1;
	private Float kilosInutSemana2;
	private Float kilosInutSemana3;
	private Float kilosInutSemana4;
	private Float kilosInutSemana5;
	private String sid;
	private Date lmd;
	private Integer version;

	// Constructors

	/** default constructor */
	public Liquidacioneslineas() {
	}

	/** minimal constructor */
	public Liquidacioneslineas(LiquidacioneslineasId id, Float pinasSemana1,
			Float pinasSemana2, Float pinasSemana3, Float pinasSemana4,
			Float pinasSemana5, Float precioSemana1, Float precioSemana2,
			Float precioSemana3, Float precioSemana4, Float precioSemana5,
			Float kilosSemana1, Float kilosSemana2, Float kilosSemana3,
			Float kilosSemana4, Float kilosSemana5, Float kilosInutSemana1,
			Float kilosInutSemana2, Float kilosInutSemana3,
			Float kilosInutSemana4, Float kilosInutSemana5) {
		this.id = id;
		this.pinasSemana1 = pinasSemana1;
		this.pinasSemana2 = pinasSemana2;
		this.pinasSemana3 = pinasSemana3;
		this.pinasSemana4 = pinasSemana4;
		this.pinasSemana5 = pinasSemana5;
		this.precioSemana1 = precioSemana1;
		this.precioSemana2 = precioSemana2;
		this.precioSemana3 = precioSemana3;
		this.precioSemana4 = precioSemana4;
		this.precioSemana5 = precioSemana5;
		this.kilosSemana1 = kilosSemana1;
		this.kilosSemana2 = kilosSemana2;
		this.kilosSemana3 = kilosSemana3;
		this.kilosSemana4 = kilosSemana4;
		this.kilosSemana5 = kilosSemana5;
		this.kilosInutSemana1 = kilosInutSemana1;
		this.kilosInutSemana2 = kilosInutSemana2;
		this.kilosInutSemana3 = kilosInutSemana3;
		this.kilosInutSemana4 = kilosInutSemana4;
		this.kilosInutSemana5 = kilosInutSemana5;
	}

	/** full constructor */
	public Liquidacioneslineas(LiquidacioneslineasId id, Float pinasSemana1,
			Float pinasSemana2, Float pinasSemana3, Float pinasSemana4,
			Float pinasSemana5, Float precioSemana1, Float precioSemana2,
			Float precioSemana3, Float precioSemana4, Float precioSemana5,
			Float kilosSemana1, Float kilosSemana2, Float kilosSemana3,
			Float kilosSemana4, Float kilosSemana5, Float kilosInutSemana1,
			Float kilosInutSemana2, Float kilosInutSemana3,
			Float kilosInutSemana4, Float kilosInutSemana5, String sid,
			Date lmd, Integer version) {
		this.id = id;
		this.pinasSemana1 = pinasSemana1;
		this.pinasSemana2 = pinasSemana2;
		this.pinasSemana3 = pinasSemana3;
		this.pinasSemana4 = pinasSemana4;
		this.pinasSemana5 = pinasSemana5;
		this.precioSemana1 = precioSemana1;
		this.precioSemana2 = precioSemana2;
		this.precioSemana3 = precioSemana3;
		this.precioSemana4 = precioSemana4;
		this.precioSemana5 = precioSemana5;
		this.kilosSemana1 = kilosSemana1;
		this.kilosSemana2 = kilosSemana2;
		this.kilosSemana3 = kilosSemana3;
		this.kilosSemana4 = kilosSemana4;
		this.kilosSemana5 = kilosSemana5;
		this.kilosInutSemana1 = kilosInutSemana1;
		this.kilosInutSemana2 = kilosInutSemana2;
		this.kilosInutSemana3 = kilosInutSemana3;
		this.kilosInutSemana4 = kilosInutSemana4;
		this.kilosInutSemana5 = kilosInutSemana5;
		this.sid = sid;
		this.lmd = lmd;
		this.version = version;
	}

	// Property accessors

	public LiquidacioneslineasId getId() {
		return this.id;
	}

	public void setId(LiquidacioneslineasId id) {
		this.id = id;
	}

	public Float getPinasSemana1() {
		return this.pinasSemana1;
	}

	public void setPinasSemana1(Float pinasSemana1) {
		this.pinasSemana1 = pinasSemana1;
	}

	public Float getPinasSemana2() {
		return this.pinasSemana2;
	}

	public void setPinasSemana2(Float pinasSemana2) {
		this.pinasSemana2 = pinasSemana2;
	}

	public Float getPinasSemana3() {
		return this.pinasSemana3;
	}

	public void setPinasSemana3(Float pinasSemana3) {
		this.pinasSemana3 = pinasSemana3;
	}

	public Float getPinasSemana4() {
		return this.pinasSemana4;
	}

	public void setPinasSemana4(Float pinasSemana4) {
		this.pinasSemana4 = pinasSemana4;
	}

	public Float getPinasSemana5() {
		return this.pinasSemana5;
	}

	public void setPinasSemana5(Float pinasSemana5) {
		this.pinasSemana5 = pinasSemana5;
	}

	public Float getPrecioSemana1() {
		return this.precioSemana1;
	}

	public void setPrecioSemana1(Float precioSemana1) {
		this.precioSemana1 = precioSemana1;
	}

	public Float getPrecioSemana2() {
		return this.precioSemana2;
	}

	public void setPrecioSemana2(Float precioSemana2) {
		this.precioSemana2 = precioSemana2;
	}

	public Float getPrecioSemana3() {
		return this.precioSemana3;
	}

	public void setPrecioSemana3(Float precioSemana3) {
		this.precioSemana3 = precioSemana3;
	}

	public Float getPrecioSemana4() {
		return this.precioSemana4;
	}

	public void setPrecioSemana4(Float precioSemana4) {
		this.precioSemana4 = precioSemana4;
	}

	public Float getPrecioSemana5() {
		return this.precioSemana5;
	}

	public void setPrecioSemana5(Float precioSemana5) {
		this.precioSemana5 = precioSemana5;
	}

	public Float getKilosSemana1() {
		return this.kilosSemana1;
	}

	public void setKilosSemana1(Float kilosSemana1) {
		this.kilosSemana1 = kilosSemana1;
	}

	public Float getKilosSemana2() {
		return this.kilosSemana2;
	}

	public void setKilosSemana2(Float kilosSemana2) {
		this.kilosSemana2 = kilosSemana2;
	}

	public Float getKilosSemana3() {
		return this.kilosSemana3;
	}

	public void setKilosSemana3(Float kilosSemana3) {
		this.kilosSemana3 = kilosSemana3;
	}

	public Float getKilosSemana4() {
		return this.kilosSemana4;
	}

	public void setKilosSemana4(Float kilosSemana4) {
		this.kilosSemana4 = kilosSemana4;
	}

	public Float getKilosSemana5() {
		return this.kilosSemana5;
	}

	public void setKilosSemana5(Float kilosSemana5) {
		this.kilosSemana5 = kilosSemana5;
	}

	public Float getKilosInutSemana1() {
		return this.kilosInutSemana1;
	}

	public void setKilosInutSemana1(Float kilosInutSemana1) {
		this.kilosInutSemana1 = kilosInutSemana1;
	}

	public Float getKilosInutSemana2() {
		return this.kilosInutSemana2;
	}

	public void setKilosInutSemana2(Float kilosInutSemana2) {
		this.kilosInutSemana2 = kilosInutSemana2;
	}

	public Float getKilosInutSemana3() {
		return this.kilosInutSemana3;
	}

	public void setKilosInutSemana3(Float kilosInutSemana3) {
		this.kilosInutSemana3 = kilosInutSemana3;
	}

	public Float getKilosInutSemana4() {
		return this.kilosInutSemana4;
	}

	public void setKilosInutSemana4(Float kilosInutSemana4) {
		this.kilosInutSemana4 = kilosInutSemana4;
	}

	public Float getKilosInutSemana5() {
		return this.kilosInutSemana5;
	}

	public void setKilosInutSemana5(Float kilosInutSemana5) {
		this.kilosInutSemana5 = kilosInutSemana5;
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