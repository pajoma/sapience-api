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

package org.geospatial.kml.geometries.converters;

import org.geospatial.kml.geometries.Point;

import sapience.features.streams.exceptions.ParserException;
import sapience.features.streams.kml.converter.CoordinateConverter;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PointConverter extends CoordinateConverter<Point> {

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		super.writeCoordinates((Point) source, writer);
	}

	public Point unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		try {
			return super.readCoordinate(new Point(), reader);
		} catch (ParserException e) {
			throw new ConversionException(e);
		}
	}
	
	public boolean canConvert(Class type) {
		return type.equals(Point.class);
	}
}

