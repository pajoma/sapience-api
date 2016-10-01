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

package org.geospatial.kml;

import java.util.ArrayList;
import java.util.List;

import org.geospatial.kml.geometries.KMLGeometry;
import org.geospatial.kml.geometries.Point;
import org.geospatial.kml.geometries.converters.PointConverter;



import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Placemark")
public class Placemark extends KMLFeature {
	
	// if annotated, he doesn't invoke the converter marshaller
	
	// if not annotated he create KMLGeometry, invokes marshaller, asks for points in placemarks
	
	
	@XStreamConverter(value = PointConverter.class)
	@XStreamImplicit
	private List<KMLGeometry> geometries;

	
//    
    public List<KMLGeometry> listGeometries() {
        if(geometries == null) {
        	geometries = new ArrayList<KMLGeometry>();
    	}
        return geometries;
    
    }

    public void setGeometries(List<KMLGeometry> geometries) {
        listGeometries().addAll(geometries);
    }
    
    public void addGeometry(KMLGeometry geometry) {
    	listGeometries().add(geometry);
    }

    
    
}

