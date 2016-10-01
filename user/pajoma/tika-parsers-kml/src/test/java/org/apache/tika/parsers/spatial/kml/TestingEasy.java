package org.apache.tika.parsers.spatial.kml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;


import org.apache.tika.TikaConfigKML;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.config.TikaConfigCollection;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.apache.tika.parser.xml.kml.KMLContentHandler;
import org.apache.tika.parser.xml.kml.KMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.impl.ParserHandler;
import org.opengis.geometry.BoundingBox;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class TestingEasy extends TestCase{
	
	private Parser parser;
	
	
	public void testEasy() throws IOException, SAXException, TikaException, URISyntaxException {
		URL file_url = this.getClass().getResource("easy.kml");
		File file = new File(file_url.toURI());
		
		TikaConfigCollection tcc = new TikaConfigCollection();
		tcc.addConfiguration(TikaConfigKML.getConfig());
		Assert.assertEquals(2, tcc.getParsers().size());
		
		String mimeType = tcc.getMimeRepository().getMimeType(file_url).getName();	
		Parser parser = tcc.getParser(mimeType);

		
		Metadata metadata = new Metadata();
		
		
	    FileInputStream stream = new FileInputStream(file);
		
	    GeospatialContentHandler gch = new KMLContentHandler();
        try {
        	parser.parse(stream, gch, metadata, new ParseContext());
        } finally {
            stream.close();
        }
        
        Assert.assertEquals( "Easy", metadata.get("dc:title"));
        // Assert.assertEquals( "John Doe", metadata.get("dc:creator"));
        BoundingBox boundingBox = gch.getBoundingBox();
        Assert.assertTrue(boundingBox.contains(7, 49));
        Assert.assertTrue(boundingBox.contains(11, 55));
        Assert.assertFalse(boundingBox.contains(7, 56));
        
        
        
        // get Body content
        System.out.println(gch.getTextContent().toString());
	}
}
