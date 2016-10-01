/**
 * 
 */
package sapience.annotations.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 * @author Henry
 *
 */

@Embeddable
public class LocalElementID implements Serializable {
	
	@Id
	private String resourceID;	// a unique id of the document the reference is part of
	
	@Id
	private String elementID;	// a unique id of the element within the document (modeled as XPath)
	
	/**
	 * The unique id of the document the reference is part of. Can be either the URL of the Web service or the URL of the XML file.
	 * @return 
	 * 		an unique identifier of the resource 
	 */
	public String getResourceID() {
		return resourceID;
	}

	/**
	 * Setting the unique identifier of the resource 
	 * @param resourceID
	 * 		an unique identifier of the resource 
	 */
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	
	

	/**
	 * The unique id of the element within the document identified with the ResourceID. The ElementID is preferably modeled as XPath expression (if the 
	 * the references are added to XML documents). The combination of ResourceID and ElementID are used to uniquely identify the reference. 
	 * @return
	 * 
	 */
	public String getElementID() {
		return elementID;
	}

	/**
	 * Setting the unique element ID (XPath expression). 
	 * @param elementID
	 */
	public void setElementID(String elementID) {
		this.elementID = elementID;
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
		StringBuffer sb = new StringBuffer();
		sb.append(getResourceID()).append(";").append(getElementID());
		return sb.toString();
	}
}
