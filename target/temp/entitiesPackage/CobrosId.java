package entitiesPackage;



/**
 * CobrosId entity. @author MyEclipse Persistence Tools
 */

public class CobrosId  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empresas empresas;
     private Ejercicios ejercicios;
     private Integer idCliente;
     private Integer cobro;


    // Constructors

    /** default constructor */
    public CobrosId() {
    }

    
    /** full constructor */
    public CobrosId(Empresas empresas, Ejercicios ejercicios, Integer idCliente, Integer cobro) {
        this.empresas = empresas;
        this.ejercicios = ejercicios;
        this.idCliente = idCliente;
        this.cobro = cobro;
    }

   
    // Property accessors

    public Empresas getEmpresas() {
        return this.empresas;
    }
    
    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public Ejercicios getEjercicios() {
        return this.ejercicios;
    }
    
    public void setEjercicios(Ejercicios ejercicios) {
        this.ejercicios = ejercicios;
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getCobro() {
        return this.cobro;
    }
    
    public void setCobro(Integer cobro) {
        this.cobro = cobro;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CobrosId) ) return false;
		 CobrosId castOther = ( CobrosId ) other; 
         
		 return ( (this.getEmpresas()==castOther.getEmpresas()) || ( this.getEmpresas()!=null && castOther.getEmpresas()!=null && this.getEmpresas().equals(castOther.getEmpresas()) ) )
 && ( (this.getEjercicios()==castOther.getEjercicios()) || ( this.getEjercicios()!=null && castOther.getEjercicios()!=null && this.getEjercicios().equals(castOther.getEjercicios()) ) )
 && ( (this.getIdCliente()==castOther.getIdCliente()) || ( this.getIdCliente()!=null && castOther.getIdCliente()!=null && this.getIdCliente().equals(castOther.getIdCliente()) ) )
 && ( (this.getCobro()==castOther.getCobro()) || ( this.getCobro()!=null && castOther.getCobro()!=null && this.getCobro().equals(castOther.getCobro()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getEmpresas() == null ? 0 : this.getEmpresas().hashCode() );
         result = 37 * result + ( getEjercicios() == null ? 0 : this.getEjercicios().hashCode() );
         result = 37 * result + ( getIdCliente() == null ? 0 : this.getIdCliente().hashCode() );
         result = 37 * result + ( getCobro() == null ? 0 : this.getCobro().hashCode() );
         return result;
   }   





}