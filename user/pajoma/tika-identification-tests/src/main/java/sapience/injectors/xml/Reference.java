package sapience.injectors.xml;

import java.io.Serializable;
import java.net.URI;




/**
 * The Reference is a pair of LocalElementIdentifier and the Annotation which is to be injected. This can be, for example, 
 * an XPath element such as //wsdl/message[name="myname"] and an attribute such as [sawsdl:modelReference=".."]. 
 * 
 * @author Henry Michels
 *
 */
public class Reference implements Serializable {
	
	
	
	private static final long serialVersionUID = 1l;
	
	private final Serializable target;	
	private final LocalElementIdentifier source;	
	
	
	public Reference(LocalElementIdentifier source, Serializable target)   {
		this.target = target;
		
		this.source = source;

	}
	
	public Serializable getTarget() {
		return target;
	}
	
	public LocalElementIdentifier getSource() {
		return source;
	}
	
	
	private String string = null;
	
	@Override
	public String toString() {
		if(string == null) {
			string = new StringBuilder().append(source).append(";").append(target).toString();
		}
		return string;
	}
}
