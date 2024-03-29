/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.hibernate.engine.query.sql;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;

/**
 * Represents the base information for a non-scalar return defined as part of
 * a native sql query.
 *
 * @author Steve Ebersole
 */
public abstract class NativeSQLQueryNonScalarReturn implements NativeSQLQueryReturn, Serializable {
	private final String alias;
	private final LockMode lockMode;
	private final Map propertyResults = new HashMap();

	/**
	 * Constructs some form of non-scalar return descriptor
	 *
	 * @param alias The result alias
	 * @param propertyResults Any user-supplied column->property mappings
	 * @param lockMode The lock mode to apply to the return.
	 */
	protected NativeSQLQueryNonScalarReturn(String alias, Map propertyResults, LockMode lockMode) {
		this.alias = alias;
		if ( alias == null ) {
			throw new HibernateException("alias must be specified");
		}
		this.lockMode = lockMode;
		if ( propertyResults != null ) {
			this.propertyResults.putAll( propertyResults );
		}
	}

	/**
	 * Retrieve the defined result alias
	 *
	 * @return The result alias.
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Retrieve the lock-mode to apply to this return
	 *
	 * @return The lock mode
	 */
	public LockMode getLockMode() {
		return lockMode;
	}

	/**
	 * Retrieve the user-supplied column->property mappings.
	 *
	 * @return The property mappings.
	 */
	public Map getPropertyResultsMap() {
		return Collections.unmodifiableMap( propertyResults );
	}
}
