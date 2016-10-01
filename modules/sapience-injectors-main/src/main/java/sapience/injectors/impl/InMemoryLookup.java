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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sapience.injectors.Lookup;
import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;



/**
 * Simple in memory implementation of Lookup, use it only for testing.
 * @author pajoma
 *
 */
public class InMemoryLookup implements Lookup  {
	/**
	 * 
	 */
	List<Reference> allReferences = null;
	
	/* (non-Javadoc)
	 * @see sapience.injectors.Lookup#check(java.io.Serializable)
	 */
	public List<Reference> check(Serializable sid) throws IOException {
		List<Reference> result = new ArrayList<Reference>();
		for(Reference ref : getReferences()) {
			Serializable documentID = ((LocalElement) ref.getSource()).getDocumentID();
			if(sid.toString().contentEquals(documentID.toString())) {
				result.add(ref);
			}
			
		}
		
		return result;
		
	}



	/**
	 * Returns all references
	 * @return a list of all references in memory
	 */
	public List<Reference> getReferences() {
		if (allReferences == null) {
			allReferences = new ArrayList<Reference>();
			
		}

		return allReferences;
		
	}


	public void put(String request, Reference reference) throws IOException {
		getReferences().add(reference);
		
	}


	public void put(String request, List<Reference> refList) throws IOException {
		getReferences().addAll(refList);
		
	}

	
} 
