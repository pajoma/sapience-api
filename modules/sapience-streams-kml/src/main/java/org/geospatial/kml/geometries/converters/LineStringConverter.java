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

import org.geospatial.kml.geometries.LineString;
import org.geospatial.kml.geometries.Point;

import sapience.features.streams.exceptions.ParserException;
import sapience.features.streams.kml.converter.CoordinateConverter;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.vividsolutions.jts.util.Assert;

public class LineStringConverter extends CoordinateConverter<LineString> {
	/*
	 * <LineString>
					<coordinates>
						-0.714496,44.837557,0 -0.71376,44.83807,0
						-0.71293,44.83746900000001,0
						-0.713662,44.836958,0
						-0.714496,44.837557,0 </coordinates>
				</LineString>
	*/


		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			super.writeCoordinates((LineString) source, writer);
		}

		public LineString unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			
			try {
				Assert.equals("LineString", reader.getNodeName());
				return super.readCoordinates(new LineString(), reader);
			} catch (ParserException e) {
				throw new ConversionException(e);
			}
		}
		
		public boolean canConvert(Class type) {
			return type.equals(LineString.class);
		}
	}



