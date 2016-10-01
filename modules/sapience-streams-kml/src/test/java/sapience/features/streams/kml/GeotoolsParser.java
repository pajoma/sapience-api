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

package sapience.features.streams.kml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.geotools.kml.KML;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.Parser;
import org.junit.Test;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.xml.sax.SAXException;

public class GeotoolsParser {
	String name = "samples/DestinationsAndGauges_v07.kml";
	
	@Test
	public void testKMLParsing() throws IOException, SAXException, ParserConfigurationException {
		
		
		KMLConfiguration config = new KMLConfiguration();
		Parser parser = new Parser(config);
		InputStream res = this.getClass().getResourceAsStream(name);
		Object parsedObject = parser.parse(res);

		
		
		Assert.assertEquals(0, parser.getValidationErrors().size());
		Assert.assertTrue(parsedObject instanceof SimpleFeature);
		
		SimpleFeature kml = (SimpleFeature) parsedObject;
		recursiveDisplay(kml);
		
		
		System.out.println(kml.getName());
		for(Property prop : kml.getProperties()) {
			
			System.out.println(prop.getValue().getClass().toString());
			
			//System.out.println(prop.toString());
		}
		
		System.out.println();

	}
	
	private void recursiveDisplay(SimpleFeature fc) {
        for (Object item : fc.getAttributes()) {
            if (item != null) {
                if (item instanceof Iterable) {
                    Iterator i = ((Iterable) item).iterator();
                    while (i.hasNext()) {
                        Object child = i.next();
                        if (child instanceof SimpleFeature) {
                            recursiveDisplay((SimpleFeature) child);
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
