/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DVDStoreAdmin.java
 *
 * Created on Jun 17, 2009, 11:43:48 AM
 */
package winUIPackage;


import sessionPackage.MySession;
import entitiesPackage.EntityType;
import entitiesPackage.Message;

/**
 *
 * @author SANTI DIAZ
 */
public class FrmVentas extends FrmEntityView  {

	private static final long serialVersionUID = 1L;
       
    public FrmVentas(java.awt.Frame parent, MySession session) {
    	super(EntityType.EntitiesType.Ventas, parent, session, true, false);
    	try {
    		showEntity();
    	} catch (RuntimeException he) {
    		Message.ShowRuntimeError(parent,
    				"FrmVentas()", he);
    	}
    }
   
}
