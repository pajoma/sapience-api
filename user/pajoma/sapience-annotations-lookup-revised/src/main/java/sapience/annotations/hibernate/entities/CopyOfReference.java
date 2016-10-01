package sapience.annotations.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import sapience.annotations.model.Model;


	

/**
 * This class represents the entity "ReferenceEntity" and the Model "Reference"  (???)
 * This entity is used to create and administer the database table for the lookup component.
 * 
 * 
 * 
 * The four columns in the database
 * 	
 * 
 * @author Henry Michels
 */

@Entity
@Table(name="Reference")
// @IdClass(ConceptID.class)
public class CopyOfReference extends EntityImpl  {
	

	/**
	 * Empty constructor to use Concept as an entity.
	 */
	public CopyOfReference(){
		
	}
	
	@Id
	private LocalElementID localElement; // the local element, a composite primary key


	
	private String referencedEntity;	// the referenced element (concept or instance) in the external model
	
	private String referenceType; 	// the type of the reference (locality, model, domain)
	
	
	
	/**
	 * The URL of the referenced entity, for example pointing to a concept modeled in OWL and served via a RDF repository. Can also point to individual elements in files. 
	 * 
	 * @return
	 */
	public String getReferencedEntity() {
		return referencedEntity;
	}

	/**
	 * Sets the referenced entity, an URL of a concept or any other element coming from an external source. 
	 * @param referencedEntity
	 */
	public void setReferencedEntity(String referencedEntity) {
		this.referencedEntity = referencedEntity;
	}

	/**
	 * Returns the type of the actual reference. Any type is possible, but it should be well-defined (a URL). Is used for example for XLink expressions. 
	 *  
	 * @return
	 */
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * Sets the type of the actual reference. Possible values for geospatial data are (using the namespace http://purl.net/ifgi/annotations) "modelReference", "domainReference", "placeReference" 
	 * @param referenceType
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public void setLocalElement(LocalElementID localElement) {
		this.localElement = localElement;
	}

	public LocalElementID getLocalElement() {
		return localElement;
	}
}
