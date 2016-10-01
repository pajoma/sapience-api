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
public class ConstructXpathDom2 {

	String file = "samples/japan.kml";

	private SAXParser saxParser;
	private DocumentBuilder domParser;
	
	@Before
	public void prepare() throws ParserConfigurationException, SAXException {
		  SAXParserFactory factory = SAXParserFactory.newInstance();
          // Turn on validation, and turn off namespaces
          factory.setValidating(true);
          factory.setNamespaceAware(false);
          saxParser = factory.newSAXParser();
          
          
          // build dom parser
          DocumentBuilderFactory domfactory = DocumentBuilderFactory.newInstance();
          domfactory.setNamespaceAware(true);
          domfactory.setIgnoringComments(true);
          
          domParser = domfactory.newDocumentBuilder();
          

	
	}
	
	/* we need the following test cases
	 * 1. parse a kml file, if you find a model reference, store its unique location, collect
	 *    all information needed for the lookup service
	 * 2. parse a kml file without annotation, inject it (taken for granted we got it from the 
	 *    lookup service)
	 */
	
	@Test
	public void parseElementsSAX() throws IOException, SAXException {
		InputStream is = this.getClass().getResourceAsStream(file);
		
		InputSource source = new InputSource(is);
		saxParser.parse(source, new KMLHandler());
		XMLReader xmlReader = saxParser.getXMLReader();

		
		// read line by line 
		
	}
		
	@Test
	public void parseElementsDOM() throws IOException, SAXException {
		InputStream is = this.getClass().getResourceAsStream(file);
		
		InputSource source = new InputSource(is);
		Document doc = domParser.parse(source);
		NodeList nl =  doc.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			
			NodeList childNodes = n.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node n2 = childNodes.item(j);
				String xpath = ExtractXPath.fromNode(n2);
				System.out.println(xpath);
			}
			
			
		
			
		}
		
		// read line by line 
		
	}
	
	class KMLHandler extends DefaultHandler {
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			
			
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
		}
		
		
		
		
	}
}
