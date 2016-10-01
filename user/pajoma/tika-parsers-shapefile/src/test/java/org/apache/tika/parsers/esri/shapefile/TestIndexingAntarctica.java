package org.apache.tika.parsers.esri.shapefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.apache.tika.parser.spatial.GeospatialMetadata;
import org.apache.tika.parser.spatial.esri.shapefile.ESRIShapefileContentHandler;
import org.apache.tika.parser.spatial.esri.shapefile.ESRIShapefileParser;
import org.apache.tika.parser.spatial.esri.shapefile.ShapefileConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.utils.ParseUtils;
import org.opengis.geometry.BoundingBox;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;



/**
 * Antarctica OSM files, coming from http://downloads.cloudmade.com/antarctica#downloads_breadcrumbs
 * @author pajoma
 *
 */
public class TestIndexingAntarctica extends TestCase {
	

	public void testLoadConfig() {
		TikaConfig conf = TikaConfig.getDefaultConfig(); 
		Assert.assertEquals(0, conf.getParsers().size());
	}
	 
	
	
	
	public void testTraditionalParsing() throws URISyntaxException, TikaException, IOException, SAXException {
		URL file_url = TestIndexingAntarctica.class.getResource("antarctica.shapefiles/antarctica_coastline.shp");
		File file = new File(file_url.toURI());
		
		Metadata metadata = new Metadata();
		
		Parser parser = new ESRIShapefileParser();
		ParseContext context = new ParseContext();
		context.set(URL.class, file_url);
		
		GeospatialContentHandler handler = new ESRIShapefileContentHandler();
		
	    
		/* why not using ParserUtils, e.g.
		 *  Parser parser = ParseUtils.getParser(file, conf);
		 * Requires a custom tika config, which interferes with default config. And
		 * shapefiles have no mimetype. 
		 * 
		 */


		
		
        FileInputStream stream = new FileInputStream(file);
	
        try {
        	parser.parse(stream, handler, metadata, context);
        } finally {
            stream.close();
        }
        
        // assertions
        Assert.assertEquals("antarctica_coastline",metadata.get(DublinCore.TITLE));
        BoundingBox boundingBox = handler.getBoundingBox();
        
        Assert.assertEquals(boundingBox.getMaxX(), 167.0599832,0);
        

        
        
       
        
	}
	


	
}
