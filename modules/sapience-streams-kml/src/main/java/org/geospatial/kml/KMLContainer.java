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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;




/**
 * This is an abstract element and cannot be used directly in a KML file. A Container 
 * element holds one or more Features and allows the creation of nested hierarchies.
 * 
 * @author pajoma
 * @see http://code.google.com/intl/de-DE/apis/kml/documentation/kmlreference.html#container
 *
 */
public abstract class KMLContainer extends KMLFeature {
    
	@XStreamImplicit
	private List<KMLFeature> containedFeatures;
    

	
	
	
	/**
	 * Add a feature to the container
	 * @param feature
	 */
	public void addFeature(KMLFeature feature) {
		listFeatures().add(feature);
	}
	
	
	/**
	 * Lists all features in this container which are instance of the given class
	 * @param filter, the class, e.g. Placemark.class
	 * @return the list of features
	 */
	public List<KMLFeature> listFeatures(Class... filter) {
		if(containedFeatures == null) {
			containedFeatures = new ArrayList<KMLFeature>();
		}
		
		if(filter == null) return containedFeatures;
		List<KMLFeature> res = new ArrayList<KMLFeature>();
		for(KMLFeature f : this.containedFeatures) {
			
			for(Class cls : filter) {
				if(f.getClass().equals(cls)) { 
					res.add(f);	
					break;
				}
			}
			
		}
		return res;
		
	}
	
	/**
	 * Lists all features in this container
	 * 
	 * @return all features 
	 */
	public List<KMLFeature> listFeatures() {
		return listFeatures(null);
	}
	
	/**
	 * Lists all features in this container recursively
	 * 
	 * @return all features 
	 */
	public List<KMLFeature> listFeaturesRecursive() {
		return listFeaturesRecursive(null);
	}
	
	/**
	 * Lists all features in this container recursively, using a filter
	 * 
	 * @return all features 
	 */
	public List<KMLFeature> listFeaturesRecursive(Class... filters) {
		// recursive (not iterative), may have better performance
		List<KMLFeature> res = new ArrayList<KMLFeature>();
		
		for(KMLFeature f : this.containedFeatures) {
			if(f instanceof KMLContainer) {
				// if current feature is an container itself, we add all contained features (recursively)
				res.addAll(((KMLContainer)f).listFeaturesRecursive(filters));
			} else {
				// no container, then let's add it to the list (we have to care about the filter though)
				if(filters == null) res.add(f);
				else {
					for(Class cls : filters) {
						if(f.getClass().equals(cls)) { 
							res.add(f);	
							break;
						}
					}
						
				}
				
			}
		}
			
		return res;
	}

	
}
