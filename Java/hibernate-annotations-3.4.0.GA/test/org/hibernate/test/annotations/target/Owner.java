//$Id: Owner.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.target;

import java.util.Map;

/**
 * @author Emmanuel Bernard
 */
public interface Owner {
	String getName();
	void setName(String name);
}
