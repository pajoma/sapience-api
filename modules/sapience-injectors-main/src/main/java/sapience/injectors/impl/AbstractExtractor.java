/**
 * Copyright (C) 2010 Institute for Geoinformatics (ifgi)
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

package sapience.injectors.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;

 

public abstract class AbstractExtractor implements Extractor {

	private final Configuration config;
	private final ConfigurationFactory cfac = ConfigurationFactory.get();

	public AbstractExtractor(Configuration config) {
		this.config = config;
	}
	
	
	public abstract List<Reference> extract(Serializable docID, InputStream is) 
			throws IOException ;

	/**
	 * Takes a map and builds accordingly the references.
	 * @param result a map, with serialized XPaths as key, and the reference (e.g. an XML element) as value 
	 * @param docID the unique identifier of the document where the XPath queries are valid for
	 * @return a list of reference which can be stored in the database
	 */
	public List<Reference> buildReferencesFromResultMap(Map<String, String> result, Serializable docID) {
		List<Reference> res = new ArrayList<Reference>();
		
		Serializable documentName = cfac.getReferenceFactory().createDocumentName(docID);
		for(Entry<String, String> entry : result.entrySet()) {
			Serializable elementName = cfac.getReferenceFactory().createElementName(entry.getKey());
			Serializable localElement = cfac.getReferenceFactory().createCompositeName(documentName, elementName);
			
			Serializable target = cfac.getReferenceFactory().createReference(entry.getValue());
			Reference reference = cfac.getReferenceFactory().createBinding(localElement, target);
			res.add(reference);
		}
		
		return res;
		
	}


	public Configuration getConfiguration() {
		return config;
	}
	

}