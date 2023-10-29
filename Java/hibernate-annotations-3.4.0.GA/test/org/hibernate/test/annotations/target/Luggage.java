//$Id: Luggage.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.target;

/**
 * @author Emmanuel Bernard
 */
public interface Luggage {
	double getHeight();
	double getWidth();

	void setHeight(double height);
	void setWidth(double width);

	Owner getOwner();

	void setOwner(Owner owner);
}
