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
import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.annotations.model.KeyValueProperty;
import sapience.features.Feature;
import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;

public class TestHarbors {

	//TODO: remove prints, inserts tests for equal checks
	
	String name = "samples/DestinationsAndGauges_v07.kml";
	private FeatureCollection collection;
	private Streams parser;

	
	
//	@Test
//	public void parseGeosoftFile() {
//		KMLParser parser = new KMLParser();
//		KML kml = parser.start(TestParser.class.getResourceAsStream(t1));
//		
//		for(KMLFeature f : kml.getDocuments()) {
//			System.out.println(f);
//		}
//	}
//	
//	
	@Before
	public void parse() throws IOException {
		parser = new KMLStream();
		collection = parser.read(this.getClass().getResourceAsStream(name));
	}
	
	@Test
	public void nrFeatures() {
		System.out.println(collection.listFeaturesRecursively().size());
		Assert.assertEquals(122, collection.listFeaturesRecursively().size());
	}
	
	@Test
	public void printFeatures() {
		for(Feature f : collection.listFeaturesRecursively()){
			for (Serializable name : f.listAnnotations(KeyValueProperty.class)) {
				KeyValueProperty prop = (KeyValueProperty) name;
				if(prop.getKey().equalsIgnoreCase("Type")) {
					if("Gauge".equalsIgnoreCase(prop.getValue().toString())) {
						
					}
				}
			}
		}
	}

	@Test
	public void printLocations() {
		for(Feature f : collection.listFeaturesRecursively()){
			System.out.println(f.getGeometry().toText());
		}
	}
	
	
	@Test
	public void printKML() throws IOException {
		parser.write(collection, System.out);
	}
	
//	@Test
//	public void parseOtherFile() {
//		KMLParser parser = new KMLParser();
//		KML kml = parser.start(TestParser.class.getResourceAsStream(t3));
//		
//		
//	}
}
