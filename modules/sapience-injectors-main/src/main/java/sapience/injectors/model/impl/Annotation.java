package sapience.injectors.model.impl;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Henry Michels, pajoma
 * The Annotation is the content pointing to an external entity. This can be either a simple attribute (e.g. 
 * sawsdl:modelreference=".."), a set of attributes ("xlink:href="..." xlink:arcrole="...") or a new child element
 * (e.g. <extendedData> ...</extendedData>. The annotation itself does not now in which document it is used
 */
@Deprecated
public class Annotation implements Serializable {
	


}
