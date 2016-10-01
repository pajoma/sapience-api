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

import org.geospatial.kml.Document;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * A Style defines an addressable style group that can be referenced by 
 * StyleMaps and Features. Styles affect how Geometry is presented in 
 * the 3D viewer and how Features appear in the Places panel of the List view. 
 * Shared styles are collected in a {@link Document} and must have an id defined for them 
 * so that they can be referenced by the individual Features that use them.
 *  
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#style
 */
@XStreamAlias("Style")
public class Style extends StyleSelector {
	
	@XStreamAlias("IconStyle")
	private IconStyle iconStyle;
	
	
	@XStreamAlias("LineStyle")
	private LineStyle lineStyle;
	
	@XStreamAlias("LabelStyle")
	private LabelStyle labelStyle;
	
	@XStreamAlias("PolyStyle")
	private PolyStyle polyStyle;
	
	@XStreamAlias("BalloonStyle")
	private BalloonStyle balloonStyle;
	
	public IconStyle getIconStyle() {
		return iconStyle;
	}

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public LabelStyle getLabelStyle() {
		return labelStyle;
	}

	public PolyStyle getPolyStyle() {
		return polyStyle;
	}

	public BalloonStyle getBalloonStyle() {
		return balloonStyle;
	}


}
