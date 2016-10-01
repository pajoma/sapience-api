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

package sapience.features.factories;

import com.vividsolutions.jts.geom.PrecisionModel;

public class GeometryFactory extends com.vividsolutions.jts.geom.GeometryFactory {

	public GeometryFactory(PrecisionModel pm, int srs) {
		super(pm,srs);
	}
	
	public GeometryFactory() {
		// we don't allow no specification of a srs, set default to epsg:4362
		super(new PrecisionModel(PrecisionModel.FLOATING),4326);
	}
	
	
	
	

}
