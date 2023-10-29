//$Id: InterfacesTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.interfaces;

import org.hibernate.test.annotations.TestCase;

/**
 * @author Emmanuel Bernard
 */
public class InterfacesTest extends TestCase {
	public void testInterface() {

	}

	protected Class[] getMappings() {
		return new Class[]{
				ContactImpl.class,
				UserImpl.class
		};
	}
}
