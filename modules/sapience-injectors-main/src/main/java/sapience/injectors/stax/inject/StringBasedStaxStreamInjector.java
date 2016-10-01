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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.factories.ReferenceFactory;
import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;
import sapience.injectors.model.xpath.XPathGenerator;
import sapience.injectors.model.xpath.XPathMatcher;
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
public class StringBasedStaxStreamInjector {

	
	private int col = 0;
	
	public Stack<String> current_path;			// the current path, updated during parsing
	public Map<Reference, Stack<String>> elementReferences;	// the element references from the database
	public Map<Reference, Stack<String>> attributeReferences;	// the attribute references from the database
	
	@Autowired ReferenceFactory refFac;
	
	private XMLInputFactory inFac;
	private XMLOutputFactory outFac;
	private XMLEventFactory eventFac;
	
	// we always store the current column
	
	
	
	
//	private NameSpaceContextImpl nsContext;
	
	private Reference interceptingElement = null;

	private XPathGenerator generator;

	private XPathMatcher matcher;
	 
	private static final Logger logger = Logger
			.getLogger(StringBasedStaxStreamInjector.class.getName());
	
	public StringBasedStaxStreamInjector(Configuration config) {

		setupFactories();
		
		generator = new XPathGenerator();
		generator.setDefaultNamespace(config.getDefaultNamespace());
		
		matcher = new XPathMatcher();
	}
	
	

	
	private void setupFactories() {
		inFac = XMLInputFactory2.newInstance();
		inFac.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
		inFac.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		inFac.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);

		outFac = XMLOutputFactory2.newInstance();

		eventFac = Stax2EventFactoryImpl.newInstance();
	}

	
	
	/**
	 * Converts the XPath-Expressions in the list of references into XPath-Stacks. We distinguish between references which are injected as 
	 * attributes, and references which are injected as new elements (we keep two separated lists for them, to make things a bit easier 
	 * to understand)
	 * @param refs the list of references
	 * @throws IOException 
	 * 
	 */
	private void prepareReferences(List<Reference> refs, NamespaceContext context) throws IOException {
		elementReferences = new HashMap<Reference, Stack<String>>();
		attributeReferences = new HashMap<Reference, Stack<String>>();
		
		
		
		
		for(Reference reference : refs) {
			LocalElement localElement = (LocalElement) reference.getSource();
			StringBuilder path = new StringBuilder(localElement.getElementID().toString());
			StringBuilder target = new StringBuilder(reference.getTarget().toString());
			LocalNamespaceContext local = new LocalNamespaceContext();
			this.processNamespace(target, context, local);
			this.processNamespace(path, context, local);

			// we replace the element id (formerly known as XPath) with the processed (with shiny new namespaces) xpath
			localElement.setElementID(path);
			
			// update the reference
			reference.setTarget(target);
			reference.setSource(localElement);
			
			// we add the namespaces in the reference to the NamespaceContext (otherwise we can't compile the XPath statements)
			List<QName> ns = extractNamespaces(reference.getTarget().toString());
			Stack<String> stack = generator.asXPathStringStack(path.toString(), ns, context);
			
			if(target.charAt(0)== '<') {
     			elementReferences.put(reference,stack);
     		} else {
     			attributeReferences.put(reference, stack);
     		}
     	}	
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
		String characters = null;
		NamespaceContext context = null;
		int marked; 
		
		
		current_path = new Stack<String>();
		current_path.push("//");
		
		try {
			XMLEventReader r = inFac.createXMLEventReader(in);
			XMLEventWriter w = outFac.createXMLEventWriter(out);
			XMLStreamWriter ws = outFac.createXMLStreamWriter(System.out);
					
			
			
			
			while (r.hasNext()) {
				
				XMLEvent e = r.nextEvent();
				switch (e.getEventType()) {
				
				case XMLEvent.START_ELEMENT:
					StartElement se = (StartElement) e;
					context = se.getNamespaceContext();
					if(elementReferences == null) {
						// process the namespaces in the references
						this.prepareReferences(refs, context);
					}
					
					
					
					// store location
					col = e.getLocation().getColumnNumber();


					// add to current xpath
				
					current_path.add(generator.asXPathString((StartElement) e));
					
					//XPathHelper.addCurrentElementToStack(current_path, se);
					
		
					
					// check if the current xpath is in our list of attribute references
					if(attributeReferences.size() > 0) {
						for (int i = 0; i < refs.size(); i++) {
							Stack<String> stack = attributeReferences.get(refs.get(i));
							if (matcher.matches(current_path, stack, true, context)) {
								// yes, let's inject the reference (only valid for attributes here)
								this.handleAttribute(w, refs.get(i));
								attributeReferences.remove(refs.get(i));
								refs.remove(i);
							}
						}
					}
					

					w.add(e);
					break;
				case XMLEvent.END_ELEMENT:

		
					
					// before removing from stack, we check if the current path with added characters is a match (which means we have to add a new element now)
					if(characters != null) this.current_path.push(characters);
					
					if(elementReferences.size() > 0) {
						for (int i = 0; i < refs.size(); i++) {
						
							
							Stack<String> stack = elementReferences.get(refs.get(i));
							
							if (matcher.matches(current_path, stack, true, context)) {
								// yes, let's inject the reference (only valid for attributes here)
								this.interceptingElement = refs.get(i);
								elementReferences.remove(refs.get(i));
								refs.remove(i);
							}
						}
					}
					
					
					if(characters != null) {				
						// clean up
						this.current_path.pop();
						characters = null;
					}
					
				
					this.current_path.pop();
					
					w.add(e);
					
					
					
					// if the intercepting is not null, the preceding element was a match, hence we inject some xml before writing a new element
					if(this.interceptingElement != null) {
						w.add(eventFac.createSpace("\n"));
						writeElementIntoStream(w, interceptingElement);
					} 
					break;	
				case XMLEvent.CHARACTERS: 					
					characters = generator.asXPathString((Characters) e);
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
		
		// written into stream, reset interception
		this.interceptingElement = null;
		
	}

	/**
	 * Helper method: writes the given reference into the output stream. We distinguish between injecting elements and attributes. For the former, we add the reference AFTER the element 
	 * represented by the XPath expression. In the case of the attribute, we inject the reference into an existing element in the XML.  
	 * 
	 * @param w XMLEventWriter
	 * @param ref Reference
	 * @throws IOException
	 * @throws XMLStreamException
	 * @see StringBasedStaxStreamInjector#handleNewElement(XMLEventWriter, Reference)
	 * @see StringBasedStaxStreamInjector#handleAttribute(XMLEventWriter, Reference)
	 */
	private void inject(XMLEventWriter w, Reference ref) throws IOException, XMLStreamException {
		String annot = ref.getTarget().toString();
		// identify event type of ref
		if(annot.getBytes()[0] == '<') {
			this.interceptingElement = ref;
		} else {
			handleAttribute(w,ref);	
		}
	}
	





	/**
	 * If the reference is a attribute (e.g. sawsdl:modelreference), we add it here (by creating 
	 * the according XMLEvent)
	 * @param w
	 * @param ref
	 * @throws XMLStreamException
	 */
	private void handleAttribute(XMLEventWriter w, Reference ref) throws XMLStreamException {
		String target = ref.getTarget().toString();
		// TODO: should also make user of the event writer
		String[] attributes = target.split(" ");
		for(String attribute : attributes) {
			w.add(createAttribute(attribute));
		}
		
		
	}

	/**
	 * Helper methods extracting the attribute fields from the reference string
	 * @param target
	 * @return
	 */
	private Attribute createAttribute(String target) {
		String[] split = target.split("=");	// split between qname and value
		// Strategy: when storing annotations, we always store the full namespace, if we detect it here
		// we create a new qname with an arbitrary prefix (or having some predefined ones such as sawsdl: or xlink:
		int pos = split[0].indexOf(':');	 
		if(pos >= 0) {
			split[0] = split[0].substring(pos);
		}
		QName name = new QName(split[0]);
		return eventFac.createAttribute(new QName(split[0]), split[1]);
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
			
			// process namespaces
			//processNamespace(target, w.getNamespaceContext());

		
			
			ByteArrayInputStream bais = new ByteArrayInputStream(target.toString().getBytes());
			this.inFac.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			r = this.inFac.createXMLEventReader(bais);
			// start a new line
	
			
		

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

	
	private Pattern prefixPattern =  Pattern.compile("(\\s|<|/|@)\\w+:");
	private Pattern nsPattern =  Pattern.compile("xmlns:\\w+=\"(\\w|:|/|\\.)*\"");

	/**
	 * Helper method, taking a XML string like <ows:Metadata xmlns:ows=\"http://ogc.org/ows\" xmlns:xlink=\"http://wrc.org/xlink\" 
	 * xlink:href=\"http://dude.com\"></ows:Metadata> from the reference 
	 * and checks if 
	 * a  the used prefixes match the globally used ones and
	 * b) any of the declared namespaces are redundant 
	 * 
	 * The same is true for the XPath definition
	 * 
	 * @param resultingXMLString
	 * @param context
	 */
	private void processNamespace(StringBuilder sb, NamespaceContext global, LocalNamespaceContext local) {
	

		
		Matcher prefixMatcher = prefixPattern.matcher(sb);
		Matcher nsMatcher = nsPattern.matcher(sb);
		String prefix; 
		String uri;
		
		
		/* process the local namespaces */
		while (nsMatcher.find()) {
			int start = nsMatcher.start();
			int end = nsMatcher.end();
			StringBuilder sbu = new StringBuilder(sb.substring(start,end));
			String thisPrefix = sbu.substring(sbu.indexOf(":")+1, sbu.lastIndexOf("="));
			String thisUri = sbu.substring(sbu.indexOf("\"")+1, sbu.lastIndexOf("\""));
			// add to local namespace context
			local.put(thisPrefix, thisUri);
			
			if((prefix = global.getPrefix(thisUri)) != null) {
				// namespace is registered, let's remove it
				sb.delete(start-1, end);
				
				// we have to reset, since we changed the state of the matched string with the deletion
				nsMatcher.reset(); 
			}
			
		}
		
		
		/* change the prefixes */
		try {
			while (prefixMatcher.find()) {
				int start = prefixMatcher.start();
				int end = prefixMatcher.end();
				
				String localprefix = sb.substring(start+1,end-1);
				if((global.getNamespaceURI(localprefix) == null) && (uri = local.getNamespaceURI(localprefix)) != null) {
					// get the other prefix
					prefix = global.getPrefix(uri);
						
					if((prefix != null)&&(! (localprefix.contentEquals(prefix)))) {
						sb.replace(start+1, end-1, prefix);
						prefixMatcher.reset();
					}
				}			
			}
		} catch (StringIndexOutOfBoundsException  e) {
			// we do nothing here
		}

	}

	
	/**
	 * Helper method, takes an XML reference with local namespace definitions, and returns the namespaces as QNames
	 * @param xml
	 * @return
	 */
	private List<QName> extractNamespaces(String xml) {
		List<QName> res = new ArrayList<QName>();
		
		Matcher nsMatcher = nsPattern.matcher(xml);
		while (nsMatcher.find()) {
			int start = nsMatcher.start();
			int end = nsMatcher.end();
			StringBuilder sbu = new StringBuilder(xml.substring(start,end));
			String prefix = sbu.substring(sbu.indexOf(":")+1, sbu.lastIndexOf("="));
			String uri = sbu.substring(sbu.indexOf("\"")+1, sbu.lastIndexOf("\""));
			res.add(new QName(uri,prefix));
		}
		return res;
	}
	

}
