package sapience.proxy.persistence.model;

import java.io.Serializable;
import java.net.URI;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

import sapience.injectors.model.LocalElement;


/**
 * Basically default implementation with JDO-safe fields (no serializable, that apparrently doesn't work)
 * @author pajoma
 *
 */
@PersistenceCapable
public class JDOLocalElement implements LocalElement {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;


	@Persistent
	private String elementName;	
		
	@Persistent
	private Link documentName;
	
	// pre-generated string representation
	@NotPersistent
	private String stringVal = null;
	
	/**
	 * 
	 * @param resourceID
	 * @param elementID
	 */
	public JDOLocalElement(Link documentName, String elementName ){
		this.documentName = documentName;
		this.elementName = elementName;
	} 

	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.LocalElement#getDocumentID()
	 */
	public Serializable getDocumentID() {
		return documentName;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.LocalElement#getElementID()
	 */
	public Serializable getElementID() {
		return elementName;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return obj.hashCode() == this.hashCode();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	
	
	@Override
	public String toString() {
		
		if (stringVal == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getDocumentID()).append(";").append(getElementID());
			stringVal = sb.toString();	
		}

		return stringVal;
		
	}

	public void setDocumentID(Serializable documentID) {
		if(! (documentID instanceof Link)) throw new IllegalArgumentException("Parameter Document ID has to be of type Link");
		this.documentName = (Link) documentID;
		
	}

	public void setElementID(Serializable elementID) {
		this.elementName = elementID.toString();
		
	}
	
	
	

}
