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

import org.geospatial.kml.metadata.ExtendedData;
import org.geospatial.kml.sugar.LookAt;

import com.thoughtworks.xstream.annotations.XStreamAlias;



/**
 * This is an abstract element and cannot be used directly in a KML file.
 * 
 * Everything in KML is a feature, note that this not a Spatial Feature in our sense
 * 
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#feature
 *
 */
public abstract class KMLFeature {
    
	@XStreamAlias("address")
	private String address = null;
	
	@XStreamAlias("name")
	private String name = null;
	
	@XStreamAlias("description")
	private String description = null;
	
	@XStreamAlias("ExtendedData")
	private ExtendedData extendedData = null;
	
	@XStreamAlias("Snippet")
	private String snippet = null;
	
	@XStreamAlias("LookAt")
	private LookAt lookAt;

	@XStreamAlias("styleUrl")
	private String styleUrl;
	
	@XStreamAlias("open")
	private String open;
	
	@XStreamAlias("visibility")
	private String visibility;
	
	
	
	private KMLContainer associatedContainer;
	


	
	
//	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		if(address != null) this.address = address;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * User-defined text displayed in the 3D viewer as the label for the object 
	 * (for example, for a Placemark, Folder, or NetworkLink).
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name != null) this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if(description != null)  this.description = description;
	}
	
	
	/**
	 * Return the container (folder, document) this KMLFeature has been associated to 
	 * @return
	 */
	public KMLContainer getContainer() {
		return this.associatedContainer;
	}
	
	/**
	 * Sets the collection this feature has been associated to 
	 * @return
	 */
	public void setContainer(KMLContainer c) {
		this.associatedContainer = c;
	}
	public void setExtendedData(ExtendedData ed) {
		this.extendedData = ed;
	}
	

	/**
	 * @return
	 * @see http://code.google.com/apis/kml/documentation/kmlreference.html#extendeddata
	 */
	public ExtendedData getExtendedData() {
		if (extendedData == null) {
			extendedData = new ExtendedData();
			
		}

		return extendedData;
	}
        
	
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public LookAt getLookAt() {
		return lookAt;
	}
	public void setLookAt(LookAt lookAt) {
		this.lookAt = lookAt;
	}

	public String getStyleUrl() {
		return styleUrl;
	}
	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	

	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}


}
