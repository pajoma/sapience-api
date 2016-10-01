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

/**
 * 
 */
package sapience.features.streams.kml;


import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.features.Feature;
import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;
import sapience.features.streams.kml.KMLStream;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

/**
 * @author Henry
 * 
 * This class tests only whether the geodetic coordinates of Germany a readed in a right way.
 *
 */
public class TestCoordinates {
	
	InputStream fileInputStream;
	String name = "InlandWaterways_v08.kml";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fileInputStream = this.getClass().getResourceAsStream("samples/"+name);
	}
	
	@Test
	public void testCoordinateValues() throws Exception {
		Geometry geom = readCoordinates();
		Coordinate[] coordinate = geom.getCoordinates();
		Assert.assertTrue((coordinate[0].x<20));
		Assert.assertTrue((coordinate[0].y>20));
	}

	
	public Geometry readCoordinates() throws IOException{
		Streams parser = new KMLStream();
		FeatureCollection collection = parser.read(fileInputStream);
		for(Feature f : collection.listFeaturesRecursively()){
			Geometry geom = f.getGeometry();
			return geom;
		}
		return null;
	}
}