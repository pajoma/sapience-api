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

package sapience.features.streams.kml.ticket29;

import java.io.IOException;

import javax.annotation.PostConstruct;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;
import sapience.features.streams.kml.KMLStream;

public class TestLastLog {

	String in = "lastlog.kml";
	
	private FeatureCollection res;
	private Streams streams;
	
	
	@Before
	public void setUp() throws IOException {
		streams = new KMLStream();
		res = streams.read(this.getClass().getResourceAsStream(in));
	}
	
	@Test
	public void testNumberOfFeatures() throws IOException {
		// number of features
		Assert.assertEquals(2, res.listFeaturesRecursively().size());
	}
		
}
