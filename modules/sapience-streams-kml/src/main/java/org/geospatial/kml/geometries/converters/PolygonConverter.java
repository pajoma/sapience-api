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

import org.geospatial.kml.geometries.InnerBoundary;
import org.geospatial.kml.geometries.LinearRing;
import org.geospatial.kml.geometries.OuterBoundary;
import org.geospatial.kml.geometries.Polygon;

import sapience.features.streams.kml.converter.CoordinateConverter;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author pajoma
 * 
 */
public class PolygonConverter extends CoordinateConverter<LinearRing> {

	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
			Polygon p = (Polygon) source;
			
			writer.startNode("outerBoundaryIs");
			writer.startNode("LinearRing");
			super.writeCoordinates(p.getOuterBoundary().getLinearRing(), writer);
			writer.endNode();
			writer.endNode();
			
			for(InnerBoundary ib : p.listInnerBoundaries()) {
				writer.startNode("innerBoundaryIs");
				writer.startNode("LinearRing");
				super.writeCoordinates(ib.getLinearRing(), writer);
				writer.endNode();
				writer.endNode();
			}
			
		// super.writeCoordinates((LinearRing) source, writer);
	}

	public Polygon unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			Polygon polygon = new Polygon(); 
			LinearRing lr = null;
			
			// get Outer Boundary
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				if(reader.getNodeName().matches("outerBoundaryIs")) {
					reader.moveDown();
					lr = super.readCoordinates(new LinearRing(), reader);
					polygon.setOuterBoundary(new OuterBoundary(lr));
					reader.moveUp();
				} else if(reader.getNodeName().matches("innerBoundaryIs")) {
					reader.moveDown();
					lr = super.readCoordinates(new LinearRing(), reader);
					polygon.addInnerBoundary(new InnerBoundary(lr));
					reader.moveUp();
				}
				else reader.moveUp();
			}
			
			// get Inner Boundaries
			
			// TODO: also check closeness for each inner Boundary
			if(! polygon.getOuterBoundary().getLinearRing().isClosed()) throw new ConversionException("Invalid Geometry");
			
			return polygon;
			
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	

		

	}

	public boolean canConvert(Class type) {
		return type.equals(Polygon.class);
		// also valid for innerBoundary, outBoundary
	}
}
