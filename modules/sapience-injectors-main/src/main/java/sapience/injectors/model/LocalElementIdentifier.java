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

package sapience.injectors.model;

import java.io.Serializable;

/**
 * @author Henry Michels
 *
 */
@Deprecated
public class LocalElementIdentifier implements Serializable {
	
	private static final long serialVersionUID = 7535204895696817340L;

	private Serializable serviceRequest;
	
	private String elementID;
	
	/**
	 * 
	 * @param resourceID
	 * @param elementID
	 */
	public LocalElementIdentifier(Serializable string , String elementID){
		this.serviceRequest = string;
		this.elementID = elementID;
	}

	/**
	 * @return the resourceID
	 */
	public Serializable getDocumentIdentifier() {
		return serviceRequest;
	}

	/**
	 * @return the elementID
	 */
	public String getElementID() {
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
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentIdentifier()).append(";").append(getElementID());
		return sb.toString();
	}

}
