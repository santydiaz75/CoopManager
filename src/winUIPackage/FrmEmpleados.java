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
public class FrmEmpleados extends FrmEntityView  {

	private static final long serialVersionUID = 1L;
    
    public FrmEmpleados(java.awt.Frame parent, MySession session) {
    	super(EntityType.EntitiesType.Empleados, parent, session, true, false);
    	try {
    		showEntity();
    	} catch (RuntimeException he) {
    		Message.ShowRuntimeError(parent,
    				"FrmEmpleados()", he);
    	}
    }
   
}
