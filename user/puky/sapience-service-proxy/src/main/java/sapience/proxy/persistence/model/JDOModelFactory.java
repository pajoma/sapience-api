package sapience.proxy.persistence.model;

import java.io.Serializable;

import sapience.injectors.factories.ReferenceFactory;
import sapience.injectors.model.Reference;

import com.google.appengine.api.datastore.Link;

public class JDOModelFactory implements ReferenceFactory {

	public JDOReference createBinding(Serializable name, Serializable reference) {
		if(! (name instanceof JDOLocalElement)) throw new IllegalArgumentException("Name has to be a JDOLocalElement");
		return new JDOReference((JDOLocalElement) name, reference.toString());
	}

	public JDOLocalElement createCompositeName(Serializable first,
			Serializable second) {
		return new JDOLocalElement(createDocumentName(first), createElementName(second));
	}

	public Link createDocumentName(Serializable documentName) {
		try {
			return new Link(asString(documentName));
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a valid URI: "+documentName.toString());
		}
	}

	public String createElementName(Serializable elementName) {
		return asString(elementName);
	}

	public Serializable createReference(Serializable reference) {
		return asString(reference);
	}
	
	private String asString(Serializable entity) {
		if((entity == null) || !(entity.toString().length() > 0)) {
			throw new IllegalArgumentException("Invalid part for an identifier: "+entity);
		} 
		return entity.toString();
	}

}
