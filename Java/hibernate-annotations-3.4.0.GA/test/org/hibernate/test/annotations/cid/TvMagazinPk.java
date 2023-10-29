//$Id: TvMagazinPk.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.cid;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author Emmanuel Bernard
 */
@Embeddable
public class TvMagazinPk implements Serializable {
	@ManyToOne
	public Channel channel;
	//public String name;
	@ManyToOne
	public Presenter presenter;
}
