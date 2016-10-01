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

package sapience.features.streams.kml;


import org.geospatial.kml.*;
import org.geospatial.kml.geometries.*;
import org.geospatial.kml.geometries.converters.*;
import org.geospatial.kml.metadata.*;
import org.geospatial.kml.styles.*;

import com.thoughtworks.xstream.XStream;


public class XStreamsConfiguration {

	public void configure(XStream xs) {

//		this.configureManually(xs);
		this.registerAnnotations(xs);
		

	}
	
	private void registerAnnotations(XStream xstream) {
		xstream.processAnnotations(KML.class);

		xstream.processAnnotations(Document.class);
		xstream.processAnnotations(Placemark.class);
		xstream.processAnnotations(Folder.class);
		xstream.processAnnotations(KMLGeometry.class);
		
		xstream.processAnnotations(Data.class);

		xstream.processAnnotations(LineString.class);
		xstream.processAnnotations(Point.class);
		xstream.processAnnotations(Polygon.class);	
		
		xstream.processAnnotations(Style.class);
		xstream.processAnnotations(StyleMap.class);

		
	}



	private void configureManually(XStream xs) {
		// kml
		xs.alias("kml", KML.class);
		xs.addImplicitCollection(KML.class, "documents", Document.class);

		// folder
		xs.alias("Document", Document.class);

		xs.alias("Folder", Folder.class);
		xs.alias("Placemark", Placemark.class);
		xs.addImplicitCollection(Document.class, "containedFeatures",
				Folder.class);
		xs.addImplicitCollection(Document.class, "containedFeatures",
				Placemark.class);

		// geometries
		xs.alias("MultiGeometry", MultiGeometry.class);
		xs.alias("Point", Point.class);
			xs.alias("LineString", LineString.class);
		xs.alias("LinearRing", LinearRing.class);
		xs.alias("Polygon", Polygon.class);
		xs.alias("innerBoundaryIs", InnerBoundary.class);
		xs.alias("outerBoundaryIs", OuterBoundary.class);

		handleGeometryCollections(xs, Placemark.class);
		handleGeometryCollections(xs, MultiGeometry.class);

		ignoreFields(xs);
		
		registerConverters(xs);
		
	}

	private void registerConverters(XStream xs) {
		xs.registerConverter(new PointConverter());
		xs.registerConverter(new LineStringConverter());
		xs.registerConverter(new LinearRingConverter());

	}
	
	private void handleGeometryCollections(XStream xs, Class cls) {
		xs.addImplicitCollection(cls, "geometries", MultiGeometry.class);
		xs.addImplicitCollection(cls, "geometries", Point.class);
		xs.addImplicitCollection(cls, "geometries", LineString.class);
		xs.addImplicitCollection(cls, "geometries", LinearRing.class);
		xs.addImplicitCollection(cls, "geometries", Polygon.class);
	}

	private void ignoreFields(XStream xstream) {
		xstream.alias("NetworkLinkControl", String.class);
		xstream.alias("NetworkLink", String.class);
		xstream.alias("GroundOverlay", String.class);
		xstream.alias("ScreenOverlay", String.class);
		xstream.alias("visibility", String.class);
		xstream.alias("open", String.class);
		xstream.alias("phoneNumber", String.class);
		xstream.alias("Snippet", String.class);
		xstream.alias("LookAt", String.class);
		xstream.alias("TimePrimitive", String.class);
		xstream.alias("styleUrl", String.class);
		xstream.alias("StyleSelector", String.class);
		xstream.alias("Style", String.class);
		xstream.alias("Region", String.class);
		xstream.alias("Metadata", String.class);
		xstream.alias("tessellate", String.class);
		xstream.alias("altitudeMode", String.class);
		xstream.alias("extrude", String.class);
		xstream.alias("StyleMap", String.class);
		xstream.addImplicitCollection(KMLContainer.class, "styleMaps", String.class);
	}
}
