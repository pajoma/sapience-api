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


public class MultiGeometry {
    
    private List<KMLGeometry> geometries = new ArrayList();

    public List<KMLGeometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<KMLGeometry> geometries) {
        this.geometries = geometries;
    }

}
