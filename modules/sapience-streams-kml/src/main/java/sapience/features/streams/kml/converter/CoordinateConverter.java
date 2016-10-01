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

package sapience.features.streams.kml.converter;

import org.geospatial.kml.geometries.KMLGeometry;

import sapience.features.streams.exceptions.ParserException;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.util.Assert;


public abstract class CoordinateConverter<T extends KMLGeometry> implements Converter {
	String nan = "NaN";
	String current; // member for output in exception
	
	protected void writeCoordinates(T p, HierarchicalStreamWriter writer) {
		try {
			writer.startNode("coordinates");
			StringBuffer sb = new StringBuffer();
			for(Coordinate c : p.listCoordinates()) {
				double x = c.x;
				double y = c.y;

				// stupid, but has to be done
				// TODO: check how to it without a string comparison
				double z = new Double(c.z).doubleValue();
				if(nan.equalsIgnoreCase(""+z)) z = 0;
			
				
				

				
				
				sb.append(x).append(",")
				  .append(y).append(",")
				  .append(z).append(" ");
			}
			writer.setValue(sb.toString());
			
		} catch (Exception e) {
			System.out.println("CoordinateConverter.writeCoordinates()");
			e.printStackTrace();
			
		} finally {
			writer.endNode();
		}

	}

	protected T readCoordinate(T geom, HierarchicalStreamReader reader) throws ParserException {
		return this.addCoordinate(geom, getCoordinatesValue(reader));
	}
	
	protected T readCoordinates(T geom, HierarchicalStreamReader reader) throws ParserException {
		return this.addCoordinates(geom, getCoordinatesValue(reader));
	}
	
	private String getCoordinatesValue(HierarchicalStreamReader reader) {
		if(! reader.hasMoreChildren()) return null;
		
		try {
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				if(reader.getNodeName().matches("coordinates")) {
					String value = reader.getValue();
					reader.moveUp();
					return value;	
				}
				else reader.moveUp();
			}
			
			// TODO: throw exception, MalformedKML("Expected coordinates here"); + location
			throw new ConversionException("Expected Coordinates here");
		} finally {
			//reader.moveUp();
		}		
	}
	
	/**
	 * Store one coordinate to the given geometry object
	 * @param geom
	 * @param line
	 * @throws ParserException 
	 */
	protected T addCoordinate(T geom, String line) throws ParserException {
		geom.addCoordinate(extractCoordinate(line));
		return geom;
	}
	
	/**
	 * Store a set of coordinates to the given geometry object
	 * @param geom
	 * @param line
	 * @throws ParserException 
	 */
	protected T addCoordinates(T geom, String line) throws ParserException  {
		try {
			/* replace line breaks  "\n" with space " "
			 * this may break the stream in very rare cases (line breaks within one coordinate definition)
			 * note that space is standard according to kml reference
			 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#linestring
			 * 
			 * 
			 */
			line = line.replace("\n", " ");
			
			
			
			String[] points = line.split(" ");
			

			
			/* and then we add each point definition again and create new points */
			for(String str : points) {
				// skip empty strings (potential side effect of splitting)
				if((str==null) || (str.compareToIgnoreCase("") == 0) || (! str.contains(","))) continue;
				
				current = str; 
				this.addCoordinate(geom, str);
			}
			return geom;
		} catch (Exception e) {
			throw new ParserException("Failed to parse coordinate: "+current);
		}

	
	}
	
	private Coordinate extractCoordinate(String line) throws ParserException {
		
		
		Coordinate c = new Coordinate();
		
		
		try {
			
			
			String[] split = line.split(",");
			
			
			// lat = y
			// lon = x 
			// lat / lon / height
			c.x = Double.parseDouble(split[0]);
			c.y = Double.parseDouble(split[1]);
			
			// height is not always given
			if(split.length==3) {
				c.z = Double.parseDouble(split[2]);
			} else {
				c.z = 0;
			}
			
		} catch (Exception e) {
			// don't throw an exception, return an empty coordinate
			throw new ParserException("Failed to parse coordinate: "+line);
		}
		return c;
	}

}
