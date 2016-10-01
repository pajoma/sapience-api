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

import org.geospatial.kml.KMLFeature;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Specifies how the description balloon for placemarks is drawn. The bgColor, if specified, 
 * is used as the background color of the balloon. 
 *  
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#balloonstyle
 *
 */
@XStreamAlias("BalloonStyle")
public class BalloonStyle extends Style {

	@XStreamAlias("bgColor")
	private String bgColor;
	
	@XStreamAlias("text")
	private String text;
	
	@XStreamAlias("textColor")
	private String textColor;

	@XStreamAlias("displayMode") 
	private String displayMode;
	
	/**
	 * Background color of the balloon (optional). 
	 * 
	 * @return
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * Text displayed in the balloon.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * Foreground color for text. The default is black (ff000000). 
	 * @return
	 */
	public String getTextColor() {
		return textColor;
	}
	
	
	/**
	 * If displayMode is default, Google Earth uses the information supplied in text to create a balloon . 
	 * If displayMode is hide, Google Earth does not display the balloon.
	 * @return
	 */
	public String getDisplayMode() {
		return displayMode;
	}
	
}
