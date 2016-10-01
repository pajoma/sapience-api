/**
 * Copyright (C) 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package sapience.injectors.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Namespace;

import org.codehaus.stax2.ri.evt.NamespaceEventImpl;

import com.ctc.wstx.util.BaseNsContext;

public class LocalNamespaceContext implements NamespaceContext {
	
	private Set<QName> namespaces;
	private List<Namespace> asEvents;
	
	
	// is merging our own context with the given one
	public LocalNamespaceContext(BaseNsContext context) {
		Iterator<?> it = context.getNamespaces();
		while(it.hasNext()) {
			Namespace ns = (Namespace) it.next();
			nsSet().add(new QName(ns.getNamespaceURI(), ns.getPrefix()));
		}
	}

	
	public LocalNamespaceContext() {
		// empty
	}
	
	public String getNameStri(String namespaceURI) {
		for(QName q : nsSet()) {
			if(q.getNamespaceURI().contentEquals(namespaceURI))
				return q.getLocalPart();
		}
		return null;	
	}
		
		
	
	public String getNamespaceURI(String localPart) {
		for(QName q : nsSet()) {
			if(q.getLocalPart().contentEquals(localPart))
				return q.getNamespaceURI();
		}
		return null;	
	}
	
	public String getPrefix(String namespaceURI) {
		for(QName q : nsSet()) {
			if(q.getNamespaceURI().contentEquals(namespaceURI))
				return q.getLocalPart();
		}
		return null;	
	}

	public Iterator<String> getPrefixes(String namespaceURI) {
		throw new RuntimeException("Method getPrefixes() not supported");
	}
	
	public void put(String localPart, String namespaceURI) {
		 nsSet().add(new QName(namespaceURI, localPart));
	}

	
	
	
	private Set<QName> nsSet() {
		if (namespaces == null) {
			namespaces = new HashSet<QName>();
		}

		return namespaces;
	}
	

	
	public List<Namespace> getNamespaces() {
		if(asEvents == null) {
			asEvents = new ArrayList<Namespace>();
			for(QName name : nsSet()) {
				asEvents.add(NamespaceEventImpl.constructNamespace(null, name.getLocalPart(), name.getNamespaceURI()));
			}
		}
		return asEvents;
	}

	/**
	 * Expects a QName with the prefix as local part (the prefix of the QName is "xmlns")
	 * @param ns
	 */
	public void put(QName ns) {
		 nsSet().add(ns);		
	}




}
