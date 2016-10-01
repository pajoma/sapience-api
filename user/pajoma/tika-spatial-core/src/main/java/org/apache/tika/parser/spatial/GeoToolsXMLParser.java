package org.apache.tika.parser.spatial;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.parsers.SAXParser;
import org.geotools.xml.Configuration;
import org.xml.sax.SAXException;

public class GeoToolsXMLParser extends org.geotools.xml.Parser {

	public GeoToolsXMLParser(Configuration configuration) {
		super(configuration);
		
	}
	
	@Override
	protected SAXParser parser() throws ParserConfigurationException,
			SAXException {
		return super.parser();
	}

	@Override
	protected SAXParser parser(boolean validate)
			throws ParserConfigurationException, SAXException {
		return super.parser(validate);
	}
}
