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

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.features.Feature;
import sapience.features.FeatureCollection;
import sapience.features.factories.GeometryFactory;
import sapience.features.operations.GeodeticDistance;
import sapience.features.streams.Streams;
import sapience.features.streams.kml.KMLStream;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.PrecisionModel;


/**
 * @author Henry
 *
 */
public class TestNearestPoint {
	
	InputStream fileInputStream;
	String name = "samples/InlandWaterways_v08.kml";
	Coordinate givenCoordinate;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fileInputStream = this.getClass().getResourceAsStream(name);
		givenCoordinate = new Coordinate(16.43996218124558, 48.20263106281101);
	}
	
	@Test
	public void testNearestPoint() throws Exception {
		Coordinate coordinate = calculateNearestPoint();
		Assert.assertEquals(coordinate.x, 16.41879065425164);
		Assert.assertEquals(coordinate.y, 48.21791303870728);
	}

	
	public Coordinate calculateNearestPoint() throws Exception{
		Streams parser = new KMLStream();
		FeatureCollection features = parser.read(fileInputStream);
		GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
		double shortestDistance = Double.MAX_VALUE;
		Coordinate nearestCoordinate = null;
		for(Feature f : features.listFeaturesRecursively()){
			Geometry geom = f.getGeometry();
			Coordinate[] coordinates = geom.getCoordinates();
			for (int i = 0; i < coordinates.length; i++) {
				Coordinate[] actualLine = {givenCoordinate, coordinates[i]};
				if (new GeodeticDistance().length(geomFactory.createLineString(actualLine))<shortestDistance) {
					shortestDistance = new GeodeticDistance().length(geomFactory.createLineString(actualLine));
					nearestCoordinate = coordinates[i];
				}
			}
		}
		return nearestCoordinate;
	}

}
