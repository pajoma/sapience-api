package org.apache.tika.parsers.spatial.kml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.apache.tika.parser.xml.kml.KMLContentHandler;
import org.apache.tika.parser.xml.kml.KMLParser;
import org.xml.sax.SAXException;


public class TestingHarbors extends TestCase{
	
	private Parser parser;
	
	
	public void testEasy() throws IOException, SAXException, TikaException, URISyntaxException {
		URL file_url = this.getClass().getResource("gauges.kml");
		File file = new File(file_url.toURI());
		
		Metadata metadata = new Metadata();
		
		Parser parser = new KMLParser();
		GeospatialContentHandler handler = new KMLContentHandler();
		
	    FileInputStream stream = new FileInputStream(file);
		
        try {
        	parser.parse(stream, handler, metadata, new ParseContext());
        } finally {
            stream.close();
        }
        
        // get Body content
        System.out.println(handler.getTextContent().toString());
	}
}
