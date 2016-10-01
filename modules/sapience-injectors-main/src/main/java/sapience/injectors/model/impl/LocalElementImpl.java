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

package sapience.injectors.model.impl;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import sapience.injectors.model.LocalElement;


/**
 * The local element identifier is used to uniquely locate an element which has to 
 * be annotated. It consists of a document identifier (e.g. a service request) and 
 * an element identifier (e.g. a XPath statement). The approach is generic, as long
 * as there is a method to infer unique properties of the document and the element, 
 * we can also build the local element identifier. A locally store image has unique 
 * properties (e.g. a histogram, the length in bytes, or its path) and metadata fields.  
 * 
 * @author Henry Michels, Patrick Maué
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LocalElementImpl implements LocalElement {
	
	private static final long serialVersionUID = 7535204895696817340L;
	
	
	@PrimaryKey 
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)  	
	public Long key;

	@Persistent
	private Serializable elementID;	
		
	@Persistent
	private Serializable docID;
	
	// pre-generated string representation
	private String stringVal = null;
	
	/**
	 * 
	 * @param resourceID
	 * @param elementID
	 */
	public LocalElementImpl(Serializable documentName , Serializable elementName){
		this.docID = documentName;
		this.elementID = elementName;
	} 

	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.LocalElement#getDocumentID()
	 */
	public Serializable getDocumentID() {
		return docID;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.LocalElement#getElementID()
	 */
	public Serializable getElementID() {
		return elementID;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return obj.hashCode() == this.hashCode();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	
	
	@Override
	public String toString() {
		
		if (stringVal == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getDocumentID()).append(";").append(getElementID());
			stringVal = sb.toString();	
		}

		return stringVal;
		
	}

	public void setDocumentID(Serializable documentID) {
		docID = documentID;
	}

	public void setElementID(Serializable elementID) {
		this.elementID = elementID;	
	}

}
