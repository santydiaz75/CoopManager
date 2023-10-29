//$Id: TypeTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.type;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.test.annotations.TestCase;

/**
 * @author Emmanuel Bernard
 */
public class TypeTest extends TestCase {
	public void testIdWithMulticolumns() throws Exception {
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		Dvd lesOiseaux = new Dvd();
		lesOiseaux.setTitle( "Les oiseaux" );
		s.persist( lesOiseaux );
		s.flush();
		assertNotNull( lesOiseaux.getId() );
		tx.rollback();
		s.close();
	}

	protected Class[] getMappings() {
		return new Class[]{
				Dvd.class
		};
	}
}
