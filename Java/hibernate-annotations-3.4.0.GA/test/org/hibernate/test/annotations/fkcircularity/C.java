// $Id: C.java 14777 2008-06-18 17:47:30Z hardy.ferentschik $
package org.hibernate.test.annotations.fkcircularity;

import javax.persistence.Entity;

/**
 * Test entities ANN-722.
 * 
 * @author Hardy Ferentschik
 *
 */
@Entity
public class C extends B {
}
