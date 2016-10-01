package org.apache.tika.parser.xml.kml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.DelegatingParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.spatial.GeoToolsXMLParser;
import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.apache.tika.parser.xml.XMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.impl.ParserHandler;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

/**
 * Tika parser for OGC KML files
 * 
 * @author pajoma
 * 
 */
public class KMLParser extends XMLParser {

	
	
	/* (non-Javadoc)
	 * @see org.apache.tika.parser.xml.XMLParser#parse(java.io.InputStream, org.xml.sax.ContentHandler, org.apache.tika.metadata.Metadata)
	 */
	@Override
	@Deprecated
	public void parse(InputStream input, ContentHandler handler,
			Metadata metadata) throws IOException, SAXException, TikaException {
		this.parse(input, handler, metadata, new ParseContext());

	}

	@Override
	public void parse(InputStream input, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {

		metadata.set(Metadata.CONTENT_TYPE, "application/vnd.google-earth.kml+xml");

		/* We ignore any other handler, the Geotools Parser does always 
		 * create its own handler which we can't access. This means
		 * any SAX events are not forwarded 
		 */

		if (handler instanceof GeospatialContentHandler) {
			KMLContentHandler kch = (KMLContentHandler) handler;
			org.geotools.xml.Parser gtParser = new org.geotools.xml.Parser(new KMLConfiguration());
			try {
				if (gtParser == null) {
					throw new ParserConfigurationException();
				}
			
				SimpleFeature f = (SimpleFeature) gtParser.parse(input);
				this.extractMetadata(metadata, f);
				kch.setContainer(f);
				
			} catch (ParserConfigurationException e) {
				throw new TikaException("Failed to parse KML file", e);
			}
			

		}
		
	}

	private void extractMetadata(Metadata metadata, SimpleFeature f) {

		for (Property p : f.getProperties()) {
			Object value = p.getValue();
			if (value != null) {
				if ((value instanceof String) || (value instanceof Boolean)) {
					metadata.add(mapMetadata(p.getName().toString()), p.getValue().toString());
				}
			}
		}
	}
	
	/* maps some kml fields to dc properties */
	private String mapMetadata(String key) {
		System.out.println(key);
		if(key.equalsIgnoreCase("name")) return "dc:title";
		else if(key.equalsIgnoreCase("atom:author")) return "dc:creator";
		else if(key.equalsIgnoreCase("description")) return "dc:description";
		else return key;
	}
	

}
