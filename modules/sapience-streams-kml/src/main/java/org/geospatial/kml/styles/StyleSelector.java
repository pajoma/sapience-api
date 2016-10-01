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

package org.geospatial.kml.styles;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is an abstract element and cannot be used directly in a KML file. 
 * It is the base type for the Style and StyleMap elements. The StyleMap 
 * element selects a style based on the current mode of the Placemark. An element 
 * derived from StyleSelector is uniquely identified by its id and its url.
 * 
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#styleselector
 * @see	
 *
 */
public abstract class StyleSelector {
	
	@XStreamAlias("id")
   	@XStreamAsAttribute
	private String id;
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
}
