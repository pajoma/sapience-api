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

import java.util.ArrayList;
import java.util.List;

import org.geospatial.kml.geometries.converters.PolygonConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("Polygon")
@XStreamConverter(value = PolygonConverter.class)
public class Polygon  extends KMLGeometry {

	protected String extrude;
	protected String tesselate;
	protected String altitudeMode;
	protected String gx_altitudeMode;
	
	
	
    protected OuterBoundary outerBoundary; // required
    protected List<InnerBoundary> listInnerBoundary;

    public OuterBoundary getOuterBoundary() {
        return outerBoundary;
    }

    public void setOuterBoundary(OuterBoundary outerBoundaryIs) {
        this.outerBoundary = outerBoundaryIs;
    }

    /**
     * A polygon can one or more holes
     * @return
     */
    public List<InnerBoundary> listInnerBoundaries() {
        if (listInnerBoundary == null) {
			listInnerBoundary = new ArrayList<InnerBoundary>();
		}

		return listInnerBoundary;
    }

    public void addInnerBoundary(InnerBoundary innerBoundary) {
        listInnerBoundaries().add(innerBoundary);
    }
        
}
