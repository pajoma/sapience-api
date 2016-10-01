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

import java.io.Serializable;

import sapience.injectors.factories.ReferenceFactory;
import sapience.injectors.model.*;
import sapience.injectors.model.impl.*;
/**
 * Factory providing helper methods to build a new Reference
 * @author pajoma
 *
 */
public class DefaultReferenceFactory implements ReferenceFactory {

	/* (non-Javadoc)
	 * @see sapience.injectors.factories.IReferenceFactory#createDocumentName(java.io.Serializable)
	 */
	public Serializable createDocumentName(Serializable documentName) {
		return new DocIDImpl(documentName);
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.factories.IReferenceFactory#createElementName(java.io.Serializable)
	 */
	public Serializable createElementName(Serializable elementName) {
		return elementName;
	}
	
	
	/* (non-Javadoc)
	 * @see sapience.injectors.factories.IReferenceFactory#createCompositeName(java.io.Serializable, java.io.Serializable)
	 */
	public Serializable createCompositeName(Serializable documentName, Serializable elementName) {
		return new LocalElementImpl(documentName, elementName);
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.factories.IReferenceFactory#createReference(java.io.Serializable)
	 */
	public Serializable createReference(Serializable reference) {
		return reference;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.factories.IReferenceFactory#createBinding(java.io.Serializable, java.io.Serializable)
	 */
	public Reference createBinding(Serializable name, Serializable reference) {
		return new ReferenceImpl(name, reference);
		
	}
	
	
}
