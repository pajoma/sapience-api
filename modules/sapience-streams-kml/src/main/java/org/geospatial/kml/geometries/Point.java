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

package org.geospatial.kml.geometries;
/* */
import org.geospatial.kml.geometries.converters.PointConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.vividsolutions.jts.geom.Coordinate;


@XStreamAlias("Point")
@XStreamConverter(value = PointConverter.class)
public class Point extends KMLGeometry {
	
	
	public Point(Coordinate c) {
		if(c != null) super.listCoordinates().add(c);
	}


	public Point() {
		this(null);
	}


	/**
	 * A Point has only one associated coordinate
	 * @return
	 * 		the coordinate associated to this point
	 */
	public Coordinate getCoordinate() {
		return super.listCoordinates().get(0);
	}
	
	
	
	}

