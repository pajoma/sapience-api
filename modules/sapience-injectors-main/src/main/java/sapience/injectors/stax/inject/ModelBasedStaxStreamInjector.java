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

package sapience.injectors.stax.inject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPathExpressionException;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.ri.Stax2EventFactoryImpl;
import org.codehaus.stax2.ri.evt.StartElementEventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import com.ctc.wstx.sr.CompactNsContext;
import com.ctc.wstx.util.BaseNsContext;

import sapience.injectors.Configuration;
import sapience.injectors.factories.ReferenceFactory;
import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;
import sapience.injectors.model.xpath.XPathElement;
import sapience.injectors.model.xpath.XPathGenerator;
import sapience.injectors.model.xpath.XPathMatcher;
import sapience.injectors.model.xpath.XPathPattern;
import sapience.injectors.model.xpath.XPathStack;
import sapience.injectors.stax.StaxBase;
import sapience.injectors.util.LocalNamespaceContext;

/**
 * Based on Stax (the Woodstox implementation), this default injector is able to add a set of references to an existing XML document. 
 * The input stream is copied into an output stream (which means, nearly no processing is applied, and the parsing is limited to 
 * the Stax-part). 
 * 
 * @author pajoma
 * @see http://woodstox.codehaus.org/
 *
 */
@SuppressWarnings("unused")
public class ModelBasedStaxStreamInjector extends StaxBase {

	// we always store the current column
	private int col = 0;
	
	public Map<XPathPattern, Reference> references;	// the references from the database

	private List<Reference> interceptingElements = null;
	 
	private static final Logger logger = Logger.getLogger(ModelBasedStaxStreamInjector.class.getName());
	
	private NamespaceHelper nshelper; 
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	public ModelBasedStaxStreamInjector(Configuration config) throws IOException {
		super(config);
		nshelper = new NamespaceHelper(config);
		
	}

	
	
	/**
	 * The actual injection procedure
	 * @param in the input stream where the XML is coming from (will be closed in the end) 
	 * @param out the output stream where we write the annotated XML into (remains open)
	 * @param refs a list of references
	 * @throws IOException
	 */
	public void inject(InputStream in, OutputStream out, List<Reference> refs) throws IOException {
		StringBuilder pb;
		XPathElement characters = null;
		int marked; 
		interceptingElements = new ArrayList<Reference>(); 
		
		try {
			XMLEventReader r = getXMLInputFactory().createXMLEventReader(in);
			XMLEventWriter w = getXMLOutputFactory().createXMLEventWriter(out);

			
			while (r.hasNext()) {

				
				
				XMLEvent e = r.nextEvent();
				switch (e.getEventType()) {
				
				case XMLEvent.START_ELEMENT:
					StartElement se = (StartElement) e;
					if(super.getNamespaceContext() == null) {
						super.updateNamespaceContext(se.getNamespaceContext());
					}
					
					if(references == null) {
						// process the namespaces in the references
						references = this.prepareReferences(refs);
					}
					// store location
					col = e.getLocation().getColumnNumber();

					// add to current xpath				
					updateCurrentPath(getGenerator().asXPathElement((StartElement) e));

					System.out.println(getCurrentPath());
					// check if the current xpath is in our list of attribute references
					for(XPathPattern p : references.keySet()) {
						if(references.get(p) == null) continue; 
						
						if(p.isAttribute()) {
							if(getMatcher().matches(p, getCurrentPath())) {
								se = this.handleAttribute(w, references.get(p), se);
								references.put(p, null); 	// removing it would lead to concurrentmodificationexcpetion
							}
						}
					}
					
					w.add(se);
					break;
				case XMLEvent.END_ELEMENT:
					updateCurrentPath(characters);
					
					
					// before removing from stack, we check if the current path with added characters is a match (which means we have to add a new element now)
					XPathStack currentPath = getCurrentPath();
		
						
					
					for(XPathPattern p : references.keySet()) {
						
						if(references.get(p) == null) continue; 
						
	
						if(getMatcher().matches(p, currentPath)) {
							if(p.isSiblingElement()) {
								// injection happens below
								this.interceptingElements.add(references.get(p)); 
								
							} else if (p.isChildElement()) {
								// we can add it directly
								w.add(getXMLEventFactory().createSpace("\n"));
								writeElementIntoStream(w, references.get(p));
							} else {
								throw new IOException("Invalid Reference: "+p);
							}
							references.put(p, null); 	// removing it would lead to concurrentmodificationexcpetion
							//references.remove(p);
							//break; //otherwise ConcurrentModificationException
							
						}
					
					}
					
					// clean up
					if(characters != null) {				
						getCurrentPath().pop();
						characters = null;
					}
					
				
					getCurrentPath().pop();
					w.add(e);	// do not change position
					
					// if the intercepting is not null, the preceding element was a match, hence we inject some xml before writing a new element
					if(this.interceptingElements.size() > 0) {
						// write all references into stream
						for (Reference ref : this.interceptingElements) {
							w.add(getXMLEventFactory().createSpace("\n"));
							writeElementIntoStream(w, ref);
						}
		
						// reset list
						this.interceptingElements.clear(); 
					} 
					
					break;	
				case XMLEvent.CHARACTERS: 					
					characters = getGenerator().asXPathElement((Characters) e);
					w.add(e);
					break;
					
				default:
					w.add(e);
					break;
				}
			}
		} catch (XPathExpressionException e) {
			if (logger.isLoggable(Level.SEVERE)) {
				logger.log(Level.SEVERE, "Not a valid XPath", e);
			}
			throw new IOException(e);
			
		} catch (XMLStreamException e) {
			if (logger.isLoggable(Level.SEVERE)) {
				logger.log(Level.SEVERE, "Failed to inject. Reason: "+e.getLocalizedMessage(), e);
			}
			throw new IOException(e);
		} finally {
			in.close();
		}
		
	}
	
	









	private void writeElementIntoStream(XMLEventWriter w, Reference ref) throws XMLStreamException {
		// write the active reference as XMLEvent into stream
		createEventsForElement(w, ref);
		
	}


	



	
	private Pattern findAttributeInReference = Pattern.compile("(\\w|:)*=\\\"[^\\s]*\\\"", Pattern.DOTALL);
	
	//private Pattern findAttributeInReference = Pattern.compile("(\\w|:)*='[^\\s]*'", Pattern.DOTALL);

	/**
	 * If the reference is a attribute (e.g. sawsdl:modelreference), we add it here (by creating 
	 * the according XMLEvent). The ref
	 * @param w
	 * @param ref
	 * @param se 
	 * @throws XMLStreamException
	 */
	private StartElement handleAttribute(XMLEventWriter w, Reference ref, StartElement se) throws XMLStreamException {
		/* we are having attributes which are in both, the reference and the current element. We only add 
		 * a new Attribute event, if it is not already contained in the Start Element
		 * 
		 * Example: 
		 * 	reference <element ns:attr1="value" reference="http://example.com">
		 *  element   <element ns:attr1="value">
		 */
		StringBuilder referenceString = new StringBuilder(ref.getTarget().toString());
		Matcher matcher = findAttributeInReference.matcher(referenceString);
		List<Attribute> attributeList = new ArrayList<Attribute>();
		
		// copy namespaces
		LocalNamespaceContext lnc = new LocalNamespaceContext((BaseNsContext) se.getNamespaceContext());
		
		while(matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			
			String key = null; 
			String prefix = null;
			String value = null;
			
			// [ns:attr1, "value"]		
			String[] l = referenceString.substring(start, end).split("=");
			if(l.length>0) {
				// [ns, attr1]
				String[] n = l[0].split(":");
				if(n.length==2) {
					key = n[1];
					prefix = n[0];
				} else {
					key = n[0];
				}
				if(l.length == 2) {
					value = l[1].substring(1, l[1].length()-1); // remove ""
					
				}
			} 
			
			// check if this is a namespace definition
			if(( prefix != null) && ("xmlns".contentEquals(prefix)) ){
				lnc.put(key, value);
			} else {
				QName name = null;
				// create QName
				if(prefix != null) {
					name = new QName(null, key, prefix);
				} else {
					String namespaceURI = se.getNamespaceContext().getNamespaceURI(XMLConstants.DEFAULT_NS_PREFIX);
					name = new QName(namespaceURI, key);
				}	
				if(name != null) {
					Attribute created = getXMLEventFactory().createAttribute(name, value);
					attributeList.add(created);
				}
			}
		}
		
		// remove redundant attribute from reference list
		Iterator<?> it = se.getAttributes();
		while(it.hasNext()) {
			Attribute ae = (Attribute) it.next();
			for(Attribute ar : attributeList) {
				if( (ar.getName().getLocalPart().contentEquals(ae.getName().getLocalPart())) &&
					(ar.getValue().contentEquals(ae.getValue()))) {
					//System.out.println("Attribute removed! -> " + ar.getName() + "= " + ar.getValue());
					attributeList.remove(ar);
					break;
					
				}
			}
		}
		
		// merge everything again
		Iterator<? extends Attribute> it2 = se.getAttributes();
		while(it2.hasNext()) {
			attributeList.add(it2.next());
		}
		
		// create a new element with the attribute set and return it
		return StartElementEventImpl.construct(se.getLocation(), se.getName(), attributeList.iterator(), lnc.getNamespaces().iterator(), lnc);
	
	}

	
	
	
	
	
	/**
	 * If the reference is more then a simple attribute, we have to add new XML (subtree) to the stream. We transform
	 * the reference into an InputStream and invoke another SAX parsing process for it. But the parsed events are added
	 * to the main XMLEventWriter. 
	 *
	 * @param w
	 * @param string
	 * @throws XMLStreamException 
	 * @throws XMLStreamException
	 */
	private void createEventsForElement(XMLEventWriter w, Reference ref) throws XMLStreamException {
		XMLEventReader r = null;
		try {
			StringBuilder target = new StringBuilder(ref.getTarget().toString());
			
			NamespaceContext c = w.getNamespaceContext();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(target.toString().getBytes());
			getXMLInputFactory().setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			r = getXMLInputFactory().createXMLEventReader(bais);

			while (r.hasNext()) {
				XMLEvent e = r.nextEvent();
				switch (e.getEventType()) {
				case XMLEvent.START_DOCUMENT:
					break;
				case XMLEvent.END_DOCUMENT:
					break;
				default:
					w.add(e);
					break;
				}		
			}
		}  finally {;
			

			if(r!=null) 
				r.close();
		}
		
		
		
	}

	
	/**
	 * Converts the XPath-Expressions in the list of references into XPath-Stacks. We distinguish between references which are injected as 
	 * attributes, and references which are injected as new elements (we keep two separated lists for them, to make things a bit easier 
	 * to understand)
	 * @param refs the list of references
	 * @throws IOException 
	 * @throws XPathExpressionException 
	 * 
	 */
	private Map<XPathPattern, Reference> prepareReferences(List<Reference> refs) throws IOException, XPathExpressionException {
		Map<XPathPattern, Reference> result = new HashMap<XPathPattern, Reference>();
		for(Reference reference : refs) {
			LocalElement localElement = (LocalElement) reference.getSource();
			StringBuilder path = new StringBuilder(localElement.getElementID().toString());
			StringBuilder target = new StringBuilder(reference.getTarget().toString());
			LocalNamespaceContext local = new LocalNamespaceContext();
			
 
			nshelper.processNamespace(target, getNamespaceContext(), local);
			nshelper.processNamespace(path, getNamespaceContext(), local);

			// we replace the element id (formerly known as XPath) with the processed (with shiny new namespaces) xpath
			localElement.setElementID(path);
			
			
			// update the reference
			reference.setTarget(target);
			reference.setSource(localElement);
			
			// we add the namespaces in the reference to the NamespaceContext (otherwise we can't compile the XPath statements)
			List<QName> ns = nshelper.extractNamespaces(reference.getTarget().toString());
			
			XPathPattern stack = getGenerator().asXPathPattern(path.toString(), getNamespaceContext());
			
			result.put(stack, reference);
     	}	
		return result;
	}
	

}
