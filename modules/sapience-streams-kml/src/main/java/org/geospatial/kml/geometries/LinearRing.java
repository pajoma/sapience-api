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

import java.util.List;

import sapience.features.streams.kml.converter.CoordinateConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.vividsolutions.jts.geom.Coordinate;


/**
 * The Linear Ring is a Line String where the first and last points are the same 
 * @author pajoma
 *
 */
@XStreamAlias("LinearRing")
public class LinearRing extends LineString {
    
	public LinearRing() {
		super();
	}
	
	public LinearRing(Coordinate[] coordinates) {
		super(coordinates);
	}

		
	
	/**
	 * Checks whether the first coordinate in this ring equals the last (otherwise we a gap)
	 * @return
	 */
	public boolean isClosed() {
		List<Coordinate> all = listCoordinates();
		Coordinate first = all.get(0);
		Coordinate last = all.get(all.size()-1);
		return last.equals3D(first);
	}
}
