// $Id: D_PK.java 14777 2008-06-18 17:47:30Z hardy.ferentschik $
package org.hibernate.test.annotations.fkcircularity;

import java.io.Serializable;

import javax.persistence.ManyToOne;

/**
 * Test entities ANN-722.
 * 
 * @author Hardy Ferentschik
 *
 */
@SuppressWarnings("serial")
public class D_PK implements Serializable{
	private C c;
	
	@ManyToOne
	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}
}
