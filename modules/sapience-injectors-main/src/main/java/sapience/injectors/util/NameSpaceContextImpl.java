package sapience.injectors.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Namespace;

import com.ctc.wstx.util.BaseNsContext;

public class NameSpaceContextImpl implements NamespaceContext {
	
	private Set<QName> namespaces;
	
	
	// is merging our own context with the given one
	public NameSpaceContextImpl(BaseNsContext context) {
		Iterator<?> it = context.getNamespaces();
		while(it.hasNext()) {
			Namespace ns = (Namespace) it.next();
			getNamespaces().add(new QName(ns.getNamespaceURI(), ns.getPrefix()));
		}
	}

	
	public NameSpaceContextImpl() {
		// empty
	}
	
	public String getNameStri(String namespaceURI) {
		for(QName q : getNamespaces()) {
			if(q.getNamespaceURI().contentEquals(namespaceURI))
				return q.getLocalPart();
		}
		return null;	
	}
		
		
	
	public String getNamespaceURI(String localPart) {
		for(QName q : getNamespaces()) {
			if(q.getLocalPart().contentEquals(localPart))
				return q.getNamespaceURI();
		}
		return null;	
	}
	
	public String getPrefix(String namespaceURI) {
		for(QName q : getNamespaces()) {
			if(q.getNamespaceURI().contentEquals(namespaceURI))
				return q.getLocalPart();
		}
		return null;	
	}

	public Iterator<String> getPrefixes(String namespaceURI) {
		throw new RuntimeException("Method getPrefixes() not supported");
	}
	
	public void put(String localPart, String namespaceURI) {
		 getNamespaces().add(new QName(namespaceURI, localPart));
	}

	
	
	
	private Set<QName> getNamespaces() {
		if (namespaces == null) {
			namespaces = new HashSet<QName>();
		}

		return namespaces;
	}
	


	/**
	 * Expects a QName with the prefix as local part (the prefix of the QName is "xmlns")
	 * @param ns
	 */
	public void put(QName ns) {
		 getNamespaces().add(ns);		
	}




}
