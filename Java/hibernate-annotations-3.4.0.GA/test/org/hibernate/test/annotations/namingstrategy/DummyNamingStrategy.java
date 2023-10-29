// $Id: DummyNamingStrategy.java 14741 2008-06-05 11:25:56Z hardy.ferentschik $
package org.hibernate.test.annotations.namingstrategy;

import org.hibernate.cfg.EJB3NamingStrategy;

@SuppressWarnings("serial")
public class DummyNamingStrategy extends EJB3NamingStrategy {
	
	public String tableName(String tableName) {
		return "T" + tableName;
	}

}
