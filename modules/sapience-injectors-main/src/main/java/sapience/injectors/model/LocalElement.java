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

public interface LocalElement extends Serializable {

	/**
	 * Return the Document Identifier, which is a unique ID for the document the references 
	 * have been extracted from. 
	 * 
	 * @return the resourceID
	 */
	public abstract Serializable getDocumentID();

	/**
	 * Returns the unique identifier of the element within the document. 
	 * @return the elementID
	 */
	public abstract Serializable getElementID();
	
	

	public abstract void setDocumentID(Serializable documentID);

	public abstract void setElementID(Serializable elementID);

}