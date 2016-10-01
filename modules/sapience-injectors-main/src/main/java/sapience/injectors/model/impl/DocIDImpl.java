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

/**
 * 
 */
package sapience.injectors.model.impl;

import java.io.Serializable;
import java.net.URI;

import sapience.injectors.model.DocID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Encapsulates a URI
 * @author Henry Michels
 *
 * AKA Service Request (consistent naming introduced)
 */
public class DocIDImpl implements DocID {
	

	private static final long serialVersionUID = 1L;

	@PrimaryKey 
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)  	
	public Long key;
	
	
	@Persistent
	private final Serializable identifier;
	
	

	/**
	 * Builds a URI from one or more string parameters
	 * @param string
	 * @param query
	 */
	public DocIDImpl(String ... string) {
		if(string.length > 0) {
			StringBuilder id =  new StringBuilder();
			for(String str : string) {
				// we just concatenate
				id.append(str);
				id.append(" ");
			}
			identifier = id.toString();
		} else {
			identifier = string;
			 
		}

	}
	
	/**
	 * 
	 * @param string
	 * @param query
	 */
	public DocIDImpl(URI uri) {
		identifier = uri;
	}
	
	public DocIDImpl(Serializable ser) {
		this.identifier = ser;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.model.DocID#getIdentifier()
	 */
	public Serializable getIdentifier() {
		return identifier;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.model.DocID#asURI()
	 */
	public URI asURI() {
		return URI.create(toString());
	}


	@Override
	public String toString() {
		return identifier.toString();
	}

}
