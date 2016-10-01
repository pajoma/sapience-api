/**
 * 
 */
package sapience.lookup.hibernate.model;

import java.io.Serializable;
import java.net.URI;
import java.util.zip.Adler32;

import sapience.injectors.factories.ReferenceFactory;

/**
 * @author Henry
 *
 */
public class HibernateModelFactory implements ReferenceFactory {

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.ReferenceFactory#createBinding(java.io.Serializable, java.io.Serializable)
	 */
	public HibernateReference createBinding(Serializable name, Serializable reference) {
		if(! (name instanceof HibernateLocalElement)) throw new IllegalArgumentException("Name has to be a HibernateLocalElement");
		return new HibernateReference((HibernateLocalElement) name, reference.toString());
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.ReferenceFactory#createCompositeName(java.io.Serializable, java.io.Serializable)
	 */
	public HibernateLocalElement createCompositeName(Serializable first,
			Serializable second) {
		return new HibernateLocalElement(createDocumentName(first), createElementName(second));
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.ReferenceFactory#createDocumentName(java.io.Serializable)
	 */
	public URI createDocumentName(Serializable documentName) {
		try {
			return new URI(asString(documentName));
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a valid URI: "+documentName.toString());
		}
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.ReferenceFactory#createElementName(java.io.Serializable)
	 */
	public String createElementName(Serializable elementName) {
		return asString(elementName);
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.ReferenceFactory#createReference(java.io.Serializable)
	 */
	public Serializable createReference(Serializable reference) {
		return asString(reference);
	}
	
	private String asString(Serializable entity) {
		if((entity == null) || !(entity.toString().length() > 0)) {
			throw new IllegalArgumentException("Invalid part for an identifier: "+entity);
		} 
		return entity.toString();
	}
	
	/**
	 * To overcome the problem of single quotes in hibernate, this method transforms
	 * single quotes into <singlequote>
	 * @param ref
	 * @return
	 */
	public static HibernateReference apostrophesToQuotes(HibernateReference ref){
		ref.getId().setElemID(ref.getId().getElemID().replace("'", "<singlequote>"));
		return ref;
	}
	
	/**
	 * This method retransforms <singlequote> to a single quote
	 * @param ref
	 * @return
	 */
	public static HibernateReference quotesToApostrophes(HibernateReference ref){
		ref.getId().setElemID(ref.getId().getElemID().replace("<singlequote>", "'"));
		return ref;
	}
}
