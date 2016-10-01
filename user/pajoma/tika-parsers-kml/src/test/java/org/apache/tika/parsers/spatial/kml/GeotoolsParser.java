package org.apache.tika.parsers.spatial.kml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.geotools.feature.simple.SimpleFeatureImpl;
import org.geotools.kml.KML;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.Parser;
import org.geotools.xml.StreamingParser;
import org.opengis.feature.simple.SimpleFeature;
import org.xml.sax.SAXException;

public class GeotoolsParser extends TestCase {
	String name = "easy.kml";

	 public void testParse() throws Exception {
	        Parser parser = new Parser(new KMLConfiguration());
	        SimpleFeature f = (SimpleFeature) parser.parse(getClass().getResourceAsStream(name));
	        Assert.assertNotNull(f);

	        Assert.assertEquals("Easy", f.getAttribute("name"));

	        Collection<SimpleFeature> folders = (Collection<SimpleFeature>) f.getAttribute("Feature");
	        
	        // first folder name
	        SimpleFeature next = folders.iterator().next();
	        Assert.assertEquals("Folder A", next.getAttribute("name"));
	        
	        Assert.assertEquals("folder", next.getFeatureType().getTypeName());
	        
	        Collection<SimpleFeature> placemarks = (Collection<SimpleFeature>) next.getAttribute("Feature");
	        Assert.assertEquals(2, placemarks.size());
	        
	        
	    }
	 

	 
	  public void testStream() throws Exception {
	        StreamingParser parser = new StreamingParser(new KMLConfiguration(),
	                getClass().getResourceAsStream(name), KML.Placemark);
	        int count = 0;
	        SimpleFeature f = null;

	        while ((f = (SimpleFeature) parser.parse()) != null) {
	            count++;
	        }

	        Assert.assertEquals(6, count);
	    }

	public void testKMLParsing() throws IOException, SAXException, ParserConfigurationException {
		
		
		KMLConfiguration config = new KMLConfiguration();
		Parser parser = new Parser(new KMLConfiguration());
		InputStream res = this.getClass().getResourceAsStream(name);
		Object parsedObject = parser.parse(res);

		
		
		Assert.assertEquals(0, parser.getValidationErrors().size());
		Assert.assertTrue(parsedObject instanceof SimpleFeature);
		
		SimpleFeatureImpl kml = (SimpleFeatureImpl) parsedObject;
		recursiveDisplay(kml);
		
		
//		System.out.println(kml.getName());
//		for(Property prop : kml.getProperties()) {
//			
//			System.out.println(prop.getValue().getClass().toString());
//			
//			//System.out.println(prop.toString());
//		}
//		
//		System.out.println();

	}
	
	private void recursiveDisplay(SimpleFeatureImpl fc) {
		
		System.out.println(fc.getBounds());
		
        for (Object item : fc.getAttributes()) {
            if (item != null) {
                if (item instanceof Iterable) {
                    Iterator i = ((Iterable) item).iterator();
                    while (i.hasNext()) {
                        Object child = i.next();
                        if (child instanceof SimpleFeature) {
                           recursiveDisplay((SimpleFeatureImpl) child);
                        } else {
                        	
                        	System.out.println("" + child.getClass() + " " +child);
                        }
                    }
                } else {
                	System.out.println("" + item.getClass() + " " + item);
                }
            }
        }
    } 
}
