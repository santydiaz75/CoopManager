//$Id: GenericsInheritanceTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.genericsinheritance;

import org.hibernate.test.annotations.TestCase;
import org.hibernate.Session;

/**
 * @author Emmanuel Bernard
 */
public class GenericsInheritanceTest extends TestCase {
	public void testMapping() throws Exception {
		Session s = openSession();
		s.close();
		//mapping is tested
	}
	protected Class[] getMappings() {
		return new Class[] {
				ChildHierarchy1.class,
				ParentHierarchy1.class,
				ChildHierarchy22.class,
				ParentHierarchy22.class
		};
	}
}
