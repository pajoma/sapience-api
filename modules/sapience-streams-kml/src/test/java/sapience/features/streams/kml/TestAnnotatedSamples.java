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

import junit.framework.Assert;

import org.junit.Test;

import sapience.annotations.model.Name;
import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;



/**
 * Tests for Ticket Re #4
 * @author pajoma
 *
 */
public class TestAnnotatedSamples {
	InputStream fileInputStream;
	Streams parser;
	FeatureCollection collection;
	
	@Test
	public void testSampleOne() throws IOException {
		String fileName = "annotatedSamples/Gievenbeck.kml";
		parser = new KMLStream();
		collection = parser.read(this.getClass().getResourceAsStream(fileName));
		
		// is MS-Gievenbeck
		Name name = (Name) collection.getAnnotation(Name.class);		
		Assert.assertEquals("MS-Gievenbeck", name.getValue());

	}
	
	// should actually be our own conversion exception
	@Test(expected=IOException.class)
	public void testSampleTwo() throws IOException {
		// this is KML file with an invalid Geometry, should throw a conversion exception
		String fileName = "annotatedSamples/Japan.kml";
		parser = new KMLStream();
		collection = parser.read(this.getClass().getResourceAsStream(fileName));
		
		// TODO: update exceptions, this test still fails
		// Assert.fail()

	}
	
}
