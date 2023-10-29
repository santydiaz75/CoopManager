// $Id: Address.java 14741 2008-06-05 11:25:56Z hardy.ferentschik $
package org.hibernate.test.annotations.namingstrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Address {

	@Id
	private long id;

	@ManyToOne
	@JoinTable(name = "person_address")
	private Person person;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
