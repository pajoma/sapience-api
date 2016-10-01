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

import org.geospatial.kml.styles.Style;
import org.geospatial.kml.styles.StyleMap;
import org.geospatial.kml.styles.StyleSelector;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A Document is a container for features and styles. 
 * This element is required if your KML file uses shared styles.
 * 
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#document
 *
 */
@XStreamAlias("Document")
public class Document extends KMLContainer {

	@XStreamImplicit
	private List<StyleSelector> styles = null;
	

	/**
	 * Shared Styles for this document.
	 * @return
	 */
	public List<StyleSelector> listStyleSelectors() {
		if (styles == null) {
			styles = new ArrayList<StyleSelector>();
		}
		return styles;
	}


	/**
	 * Adds a new shared style selector to the current document
	 * @param ss
	 */
	public void addStyleSelector(StyleSelector ss) {
		listStyleSelectors().add(ss);
	}


	
	

        
}
