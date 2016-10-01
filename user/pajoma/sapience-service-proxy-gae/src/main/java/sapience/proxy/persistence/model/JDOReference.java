package sapience.proxy.persistence.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Link;

import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;

@PersistenceCapable
public class JDOReference implements Reference {
	
	private static final long serialVersionUID = 3646052842396727461L;

	@PrimaryKey 
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)  	
	private Long key;
	
	@Persistent
	private String target;
	
	@Persistent
	private JDOLocalElement source;	
	
	// lazy-generated string representations
	@NotPersistent
	private String stringVal = null;
	
	
	
	
	public JDOReference(JDOLocalElement name, String reference)   {
		this.target = reference;
		this.source = name;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getTarget()
	 */
	public Serializable getTarget() {
		return target;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getSource()
	 */
	public LocalElement getSource() {
		return source;
	}
	
	
	
	
	@Override
	public String toString() {
		if(stringVal == null) {
			stringVal = new StringBuilder().append(source).append(";").append(target).toString();
		}
		return stringVal;
	}

	public void setSource(Serializable source) {
		if(! (source instanceof JDOLocalElement)) throw new IllegalArgumentException("Parameter has to be of type JDOLocalElement");
		this.source = (JDOLocalElement) source;
	}

	public void setTarget(Serializable target) {
		this.target = target.toString();
		
	}

}
