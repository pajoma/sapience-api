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

/**
 * The same as an outer boundary (may be merged in the future)
 * @author pajoma
 *
 */
public class InnerBoundary  {    
 
    
    private final LinearRing ring;

    public InnerBoundary(LinearRing ring) {
    	this.ring = ring;
    }
    
    public LinearRing getLinearRing() {
        return ring;
    }


 
}
