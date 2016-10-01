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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;


import sapience.annotations.model.Name;
import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;

public class TestEasy {

	String in = "samples/easy.kml";
	String out = "samples/easy.out.kml";
	
	private FeatureCollection res;
	private Streams streams;
	
	@Before
	public void parse() throws IOException {
		streams = new KMLStream();
		res = streams.read(this.getClass().getResourceAsStream(in));
	}
	
	@Test
	public void testNumberOfFeatures() throws IOException {
		// number of features
		Assert.assertEquals(6, res.listFeaturesRecursively().size());
	}
	
	@Test
	public void testAnnotation() {
		// name of second folder
		// we don't which one, but one of both has to (order can change)
		Name f0 = res.getFeature(0).getAnnotation(Name.class);
		Name f1 = res.getFeature(1).getAnnotation(Name.class);
		Assert.assertTrue(f0.toString().equalsIgnoreCase("Folder B") || f1.toString().equalsIgnoreCase("Folder B")); 

		
	}
	
	// test removed, will always fail, since the order of always changes (you have to 
	// check the content, not the XML
	public void writeEasy() throws IOException {
		OutputStream os = new OutputStream() {
			StringBuffer sb = new StringBuffer();
			@Override
			public void write(int b) throws IOException {
				sb.append((char) b);
			}
			
			public String toString() {
				return sb.toString();
			}
		};
		
		streams.write(res, os);
		
		String expected = IOUtils.toString(this.getClass().getResourceAsStream(out));
		String actual = os.toString();
		
		System.out.println(expected.trim());
		System.out.println(actual.trim());
		
	}
	
}
