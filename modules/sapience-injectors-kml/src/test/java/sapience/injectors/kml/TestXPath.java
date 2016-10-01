/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
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

package sapience.injectors.kml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import sapience.injectors.util.ExtractXPath;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;

/**
 * @author pajoma
 * Using XPATH: http://java.sun.com/j2se/1.6.0/docs/api/javax/xml/xpath/package-summary.html
 * Using Document Locator: http://www.java-tips.org/java-se-tips/org.xml.sax/using-xml-locator-to-indicate-current-parser-pos.html
 */
public class TestXPath {

	String file = "Japan.kml";

	private SAXParser saxParser;
	private DocumentBuilder domParser;
	
	@Before
	public void prepare() throws ParserConfigurationException, SAXException {
		  SAXParserFactory factory = SAXParserFactory.newInstance();
          // Turn on validation, and turn off namespaces
          factory.setValidating(true);
          factory.setNamespaceAware(true);
          saxParser = factory.newSAXParser();


	
	}
	
	/* we need the following test cases
	 * 1. parse a kml file, if you find a model reference, store its unique location, collect
	 *    all information needed for the lookup service
	 * 2. parse a kml file without annotation, inject it (taken for granted we got it from the 
	 *    lookup service)
	 */
	
	@Test
	public void parseElementsSAX() throws IOException, SAXException {
		//System.out.println(this.getClass().getResource("samples/".concat(file)));
		InputStream is = this.getClass().getResourceAsStream("./samples/".concat(file));
		
		
		
		InputSource source = new InputSource(is);
		saxParser.parse(source, new KMLHandler());
		
		
		XMLReader xmlReader = saxParser.getXMLReader();

		
		// read line by line 
		
		
	}
	
	
	class KMLHandler extends DefaultHandler {
		
		Stack<XPathElement> stack = null;
		XPathGenerator generator = null;
		
		@Override
		public void startDocument() throws SAXException {
			stack = new Stack<XPathElement>();
			generator = new XPathGenerator();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(stack.isEmpty()) {
				
				// decide which injector to use
				// if(localName.equalsIgnoreCase("kml")) ...
				stack.push(new XPathElement(uri, localName));
			} else {
				if(localName.equalsIgnoreCase("Document")) {
					stack.push(new XPathElement(uri, localName));
				} else
				
				if(localName.equalsIgnoreCase("Placemark")) {
					stack.push(new XPathElement(uri, localName,"id",attributes.getValue("id")));
					
					System.out.println(generator.render(stack));
					
				}
				
			}
			
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(stack.isEmpty()) return;
			
			if(localName.equalsIgnoreCase("Placemark")) stack.pop();
			else if(localName.equalsIgnoreCase("Document")) stack.pop();
			else if(localName.equalsIgnoreCase("kml")) stack.pop();
			
			
	
		}
		
		
		class XPathElement {
			String uri = null;
			String localname = null;
			String attribute = null;
			public String getUri() {
				return uri;
			}

			public String getLocalname() {
				return localname;
			}

			public String getAttribute() {
				return attribute;
			}

			public String getAttributeValue() {
				return attributeValue;
			}

			String attributeValue = null;
			
			public XPathElement(String uri, String localname) {
				this.uri = uri;
				this.localname = localname;
			}
			
			public XPathElement(String uri, String localname, String attribute, String attributeValue) {
				this.uri = uri;
				this.localname = localname;
				this.attribute = attribute;
				this.attributeValue = attributeValue;
			}
			
			public boolean hasAttribute() {
				return (attribute != null);
			}
		}
		
		class XPathGenerator {
			public String render(Stack<XPathElement> stack) {
				StringBuffer xpath = new StringBuffer();
				XPathElement[] ar = new XPathElement[stack.size()];
				stack.copyInto(ar);
				
				for (int i = ar.length-1; i >= 0; i--) {
					xpath.insert(0, render(ar[i]));
				}
					
				return xpath.toString();
				
			}
			private String render(XPathElement elem) {
				StringBuffer str = new StringBuffer();
				
				str.append("/");
				
				// beware of namespace prefix, read them from the namespace prefix map if necessary
				str.append(elem.getLocalname());
				if(elem.hasAttribute()) {
					str.append("[@");
					str.append(elem.getAttribute());
					str.append("=");
					str.append(elem.getAttributeValue());
					str.append("]");
				}
				
				return str.toString();
				
			}
		}
		
	}
}
