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

package sapience.injectors.stax.extract;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPathExpressionException;

import sapience.injectors.Configuration;
import sapience.injectors.model.xpath.XPathAttribute;
import sapience.injectors.model.xpath.XPathElement;
import sapience.injectors.model.xpath.XPathMatcher;
import sapience.injectors.model.xpath.XPathPattern;
import sapience.injectors.model.xpath.XPathStack;
import sapience.injectors.stax.StaxBase;

import com.ctc.wstx.sr.CompactNsContext;

public class StaxBasedExtractor extends StaxBase {

	private static final Logger logger = Logger.getLogger(StaxBasedExtractor.class.getName());
	private NamespaceHelper nshelper; 
	
	
	public StaxBasedExtractor(Configuration config) {
		super(config);
		nshelper = new NamespaceHelper(config);
	}



	/**
	 * Parses the input stream and, if any of the lines matches the given XPath-statements from the configuration, adds to the 
	 * resultingmap
	 * @param is the input stream with the annotated XML file
	 * @return a map, with the identifying XPath as key and the XML representing the reference as value
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public Map<String,String> collectReferences(InputStream is) throws IOException {
		Map<String,String> result = new HashMap<String, String>();

		// we always have to keep of the track one step before (since later during the injection we have to store the location
		// AFTER where we have to inject (if it is a element)
		XPathStack preceding_path = null; 
		
	
		List<XPathPattern> annotation_paths = null;

		int marked = -1;
		
		/* here we temporarily store information if there has been a match */
		QName matchingElementName = null;
		XPathPattern matchingPattern = null;
		
		// the current context
		// CompactNsContext context = null;
		 

		try {
			
			
			XMLEventReader eventReader = getXMLInputFactory().createXMLEventReader(is);
			// we temporarily always store that, we may use it as top of the identifiying path
			XPathElement characters = null;

			StringWriter resultingXMLString = new StringWriter();

			while (eventReader.hasNext()) {
				XMLEvent e = eventReader.nextEvent();
				
				switch (e.getEventType()) {				
				case XMLEvent.START_ELEMENT:
					StartElement start_element = (StartElement) e;
					
					// update namespace
					super.updateNamespaceContext(start_element.getNamespaceContext());
					
					getCurrentPath().add(getGenerator().asXPathElement(start_element));
				
					/* if we have a match, we set the markedElementName to the local name of the current start element. 
					 * Once we encounter the according end element, we store the complete string occurred in between 
					 * (stored in sw, the string writer)
					 * 
					 */
					if(marked == -1) {
						if(annotation_paths == null) {
							// get annotations XPaths from configuration and convert them to Stacks
							annotation_paths = getGenerator().asXPathPatterns(Arrays.asList(getConfiguration().getAnnotationXPathExpressions()), getNamespaceContext());
						}
						
						
//						System.out.println(getCurrentPath().toString()); 
						
						if ((marked = getMatcher().matches(getCurrentPath(), annotation_paths)) > -1) {
							matchingPattern = annotation_paths.get(marked);
							e.writeAsEncodedUnicode(resultingXMLString);
							
							if(matchingPattern.isAttribute()) {
								/* the discovered annotation is an attribute of an existing element, we directly add it as result */	
								// remove the attribute from the path
								XPathElement matched = removeMatchingAttribute(matchingPattern.peek(), getCurrentPath().pop());
								getCurrentPath().push(matched);
								NamespaceContext namespaceContext = getNamespaceContext();
								
								// build element id
								String elementID = this.generateElementID(getCurrentPath(), "attribute()", resultingXMLString);								
								String reference = nshelper.injectNamespace(new StringBuilder(resultingXMLString.toString()), elementID, getNamespaceContext()).toString();
								 
								result.put(elementID, reference);
								
								// reset
								marked = -1;
								matchingElementName = null;
								resultingXMLString = new StringWriter();

								
								
							} else {
								/* the discovered annotation is a new element, which means we can only add it once the current element is closed again (and
								 * everything which is inside is stored as reference in the resultingXMLString)
								 *
								 */
								matchingElementName = start_element.getName();
							
							}

							// reset
		
						
						}
					}



					break;	
				case XMLEvent.END_ELEMENT:
			
					if((matchingElementName != null) && (matchingPattern != null) && (matchingElementName.toString().contentEquals(((EndElement) e).getName().toString()))) {
						e.writeAsEncodedUnicode(resultingXMLString);
						if(matchingPattern.isChildElement()) {
							
							/* the discovered annotation is a new element which is a child of the current father */
							
							// we pop the top of the preceding path
							XPathStack popper = (XPathStack) getCurrentPath().clone();
							popper.pop();
							
							// build result
							String elementID = this.generateElementID(popper, "child()", resultingXMLString); 
							String reference = nshelper.injectNamespace(new StringBuilder(resultingXMLString.toString()), elementID, getNamespaceContext()).toString();
							result.put(elementID, reference);
							
							// reset
							marked = -1;
							matchingElementName = null;
							resultingXMLString = new StringWriter();
							
						} else if(matchingPattern.isSiblingElement()) {
							String elementID = this.generateElementID(preceding_path, "sibling()", resultingXMLString); 
							// declare the prefixes used in the xpath and reference to the reference as namespaces
							String reference = nshelper.injectNamespace(new StringBuilder(resultingXMLString.toString()), elementID, getNamespaceContext()).toString();
							// add as result and reset
							result.put(elementID, reference);
							marked = -1;
							matchingElementName = null;
							resultingXMLString = new StringWriter();
						}

						
					
						
					} else {
						// we only set the new preceding path if the current wasn't a matching pattern (otherwise we will have the problem that 
						// the key of the next line will be a reference (only the case if we have two references in a row) 
						preceding_path = (XPathStack) getCurrentPath().clone();
						if(characters != null) {
							preceding_path.push(characters);
						}
					}

					getCurrentPath().pop();
					
					break;
				case XMLEvent.CHARACTERS:
					// we temporarily store the characters, and add them later to the preceding element	
					characters = getGenerator().asXPathElement((Characters) e);
					
					
					if(matchingElementName != null) {
						e.writeAsEncodedUnicode(resultingXMLString);
					}
					break;
				default:
					if(matchingElementName != null) {
						e.writeAsEncodedUnicode(resultingXMLString);
					}
					break;
				}
			}
		} catch (XPathExpressionException e) {
			if (logger.isLoggable(Level.SEVERE)) {
				logger.log(Level.SEVERE, "Not a valid XPath. Message: "+e.getLocalizedMessage(), e);
			}
			throw new IOException(e);

		} catch (XMLStreamException e) {
			if (logger.isLoggable(Level.SEVERE)) {
				logger.log(Level.SEVERE, "Failed to parse XML Stream. Message: "+e.getLocalizedMessage(), e);
			}
			throw new IOException(e);
		} finally {
			is.close();
			
			// unbind local namespace context from global context
//			context.release();
		}

		return result;

	}



	
	private String generateElementID(XPathStack currentPath, String function, StringWriter reference) {
		StringBuilder sb = currentPath.serialize(new StringBuilder(), getNamespaceContext());
		sb.append("/").append(function);
		sb.append("~").append(reference.hashCode()); 		
		return sb.toString(); 
	}



	/**
	 * For every attribute in the pattern, we remove the match in the second element (we have to do this to be able to match against it
	 * during injection (where the annotation attributes are obviously not part of the parsed XML)
	 * 
	 * @param matchingPattern
	 * @param peek
	 */
	private XPathElement removeMatchingAttribute(XPathElement pattern, XPathElement matched) {
		for(XPathAttribute attr : pattern.getAttributes()) {
			matched.getAttributeMap().remove(attr.getKey());
		}
		return matched;
	}


	


	
	





}
