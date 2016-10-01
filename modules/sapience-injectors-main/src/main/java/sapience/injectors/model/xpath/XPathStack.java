package sapience.injectors.model.xpath;

import java.util.Iterator;
import java.util.Stack;

import javax.xml.namespace.NamespaceContext;

import org.codehaus.stax2.ri.EmptyNamespaceContext;

public class XPathStack extends Stack<XPathElement> {

	private NamespaceContext context = null;
	
	public XPathStack() {
		context = EmptyNamespaceContext.getInstance();
	}
	
	private static final long serialVersionUID = -1751380253997591857L;
	
	
	public StringBuilder serialize(StringBuilder sb, NamespaceContext context) {
		sb.append("/");
		Iterator<XPathElement> each = this.iterator();
		while (each.hasNext()) {
			XPathElement next = each.next();
			StringBuilder elem = next.serialize(new StringBuilder(), context);
			if(elem.charAt(0) != '/')  {
				// TODO: changed from next to elem
				sb.append('/').append(elem);
			}
		}
		

		return sb;
	}
	
	
	
}
