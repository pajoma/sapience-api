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

package sapience.features.operations;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.operation.distance.DistanceOp;

public class GeodeticDistance {
	Ellipsoid reference = null;
	private GeodeticCalculator calculator;

	public GeodeticDistance() {
		calculator = new GeodeticCalculator();
	}
	
	/**
	 * Calculates the minimum distance between two geometries. The JTS planar
	 * distance functions are used to compute closest points, the ellipsoidal
	 * distance between these two points is then returned.
	 * 
	 * @param g0
	 * @param g1
	 * @throws Exception
	 */
	public double betweenGeometries(Geometry g0, Geometry g1) throws Exception {
		assert g0.getSRID() == g1.getSRID();

		Ellipsoid reference = this.getEllipsoid(g0.getSRID());
		Coordinate[] cs = DistanceOp.closestPoints(g0, g1);

		assert cs.length == 2;
		return this.betweenCoordinates(reference, cs[0], cs[1]);
	}
	
	
	/**
	 * Calculates the distance
	 * 
	 * @param c0
	 * @param c1
	 * @return
	 */
	public double betweenCoordinates(Ellipsoid reference, Coordinate c0, Coordinate c1) {
		GlobalCoordinates gc0 = new GlobalCoordinates(c0.y, c0.x);
		GlobalCoordinates gc1 = new GlobalCoordinates(c1.y, c1.x);
		
		GeodeticCurve geoCurve = 
			calculator.calculateGeodeticCurve(reference, gc0, gc1);
		return geoCurve.getEllipsoidalDistance();
	}
	
	/**
	 * Calculates the length of the given linestring. 
	 * 
	 * @param g0
	 * @param g1
	 * @throws Exception
	 */
	public double length(LineString ls) throws Exception {
		assert ls != null;
		Ellipsoid reference = this.getEllipsoid(ls.getSRID());
		
		Coordinate[] cords = ls.getCoordinates();
		
		assert (cords != null) && (cords.length > 1);
		
		double length = 0;
		Coordinate c0 = cords[0];
		for (int i = 1; i < cords.length; i++) {
			Coordinate c1 = cords[i];
			length += this.betweenCoordinates(reference, c0, c1);
			c0 = c1;
		}
	
		return length;
	}

	private Ellipsoid getEllipsoid(int srid) throws Exception {
		// the parameter is the epsg code
		if (srid == 4326) return Ellipsoid.WGS84;
		else throw new Exception("Given Spatial Reference System with EPSG "+ srid + " not supported");
	}

}
