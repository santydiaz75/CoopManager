// $Id: ClassC.java 14777 2008-06-18 17:47:30Z hardy.ferentschik $
package org.hibernate.test.annotations.fkcircularity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Test entities ANN-730.
 * 
 * @author Hardy Ferentschik
 * 
 */
@Entity
@Table(name = "class_c")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ClassC extends ClassB {
}
