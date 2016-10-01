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

package sapience.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;




/**
 * A Feature Collections consists many features (and can itself be 
 * considered as feature)
 * 
 * @author pajoma
 *
 */
public class FeatureCollection extends Feature {
	private static final long serialVersionUID = -2009377003615118988L;
	
	private Collection<Feature> features = null;

	public FeatureCollection() {
		
	}
	
	public FeatureCollection(List<Feature> features) {
		this.addFeatures(features);
	}
	
	public void addFeatures(List<Feature> features) {
		for (Feature feature : features) {
			addFeature(feature);
		}
	}
	
	public void addFeature(Feature f) {
		this.listFeatures().add(f);
		f.setAssociatedFeatureCollection(this);
	}

	public Collection<Feature> listFeatures() {
		if (features == null) {
			features = new HashSet<Feature>();	
		}

		return features;
	}
	
	/**
	 * Returns the feature at the specific index
	 * @param index
	 * @return
	 */
	public Feature getFeature(int index) {
		return new ArrayList<Feature>(features).get(index);
	}
	
	/**
	 * Returns all features in this collection and all sub-collections.
	 * 
	 * @return
	 */
	public Collection<Feature> listFeaturesRecursively() {
		Collection<Feature> res = new ArrayList<Feature>();
		for(Feature f : listFeatures()) {
			if(f instanceof FeatureCollection) res.addAll( ((FeatureCollection) f).listFeaturesRecursively());
			else {
				res.add(f);
			}
		}
		return res;
	}
}
