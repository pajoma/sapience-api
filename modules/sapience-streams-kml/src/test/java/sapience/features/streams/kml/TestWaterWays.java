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

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import sapience.features.Feature;
import sapience.features.FeatureCollection;
import sapience.features.operations.GeodeticDistance;
import sapience.features.streams.Streams;
import sapience.features.streams.kml.KMLStream;

import com.vividsolutions.jts.geom.LineString;

public class TestWaterWays {

	String name = "samples/InlandWaterways_v08.kml";
	String out = "samples/InlandWaterways_v07.out.kml";
	private FeatureCollection kml;
	private Streams parser;

	// Spree, 11.733 km
	// Fulda, 136.566 km
	@Before
	public void parseRivers() throws Exception {
		parser = new KMLStream();
		kml = parser.read(this.getClass().getResourceAsStream(name));
	}
	
	@Test
	public void writeLength() throws Exception {
		for(Feature f : kml.listFeaturesRecursively()) {
			double length = new GeodeticDistance().length((LineString) f.getGeometry());
		}
	}
	
	@Test
	public void writeRivers() throws Exception {
		URL outURL = this.getClass().getResource(out);
		File f = new File(outURL.toURI());
//		parser.write(kml, new FileOutputStream(f));
		// parser.write(kml, System.out);
		
//		FeatureCollection kml2 = parser.read(new FileInputStream(f));
//		System.out.println(kml2.getAnnotation(Name.class));
	}
	


}
