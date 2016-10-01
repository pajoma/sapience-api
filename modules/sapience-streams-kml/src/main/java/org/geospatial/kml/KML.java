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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * The root element of a KML file. This element is required. It follows 
 * the xml declaration at the beginning of the file. 
 * 
 * @author pajoma
 * @see http://code.google.com/apis/kml/documentation/kmlreference.html#kml
 *
 */
@XStreamAlias("kml")
public class KML {
	
	/* A basic <kml> element contains 0 or 1 Feature 
	 * and 0 or 1 NetworkLinkControl:
	 */
	
	@XStreamImplicit
	private List<KMLFeature> documents;

	/**
	 * Returns the first feature
	 * @return
	 */
	public List<KMLFeature> listFeatures() {
		if (documents == null) {
			documents = new ArrayList<KMLFeature>();	
		}
		return documents;
	}
	
	/**
	 * Returns the feature as Document. A KML file can only contain one feature 
	 * (or a network link), we assume this feature is a document (we throw an exception
	 * otherwise)
	 * 
	 * @return
	 * @throws MultipleFeaturesException 
	 */
	public Document getDocument() throws IOException {
		if(listFeatures().size()==1) {
			KMLFeature f = this.getFeature();
			if(f instanceof Document) return (Document) f;
			
			// TODO: throw an exception which states that this is a network link
			
			else throw new IOException("Not a Document");
		}
		else throw new IOException("Multiple Documents in this KML");
	}
	
	/**
	 * Returns the feature A KML file can only contain one feature 
	 * (usually a Document or a network link), this method returns it.
	 * 
	 * 
	 * @return
	 * @throws MultipleFeaturesException 
	 */
	public KMLFeature getFeature() throws IOException {
		if(listFeatures().size()==0) throw new IOException("No Feature in this KML");
		if(listFeatures().size()>1) throw new IOException("Multiple Features in this KML");
			
		return listFeatures().get(0);

	}
	
	/**
	 * Sets the feature of this KML object. Note: only one feature per KML is allowed, 
	 * which means adding a second feature will overwrite the first. 
	 * @param doc
	 */
	public void setFeature(KMLFeature doc) {
		this.documents = null;
		this.listFeatures().add(doc);
	}

	
}
