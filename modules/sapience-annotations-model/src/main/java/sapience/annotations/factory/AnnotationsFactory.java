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

package sapience.annotations.factory;

import java.io.Serializable;


import sapience.annotations.model.*;
import sapience.annotations.model.contact.*;


public class AnnotationsFactory {

	private boolean isEmpty(Serializable str) {
		return ((str == null) || (str.toString().matches("")));
	}
	
	public Name createName(Serializable f, String value) {
		if(isEmpty(value)) return null;
		else return new Name(f, value);
	}

	
	public Description createDescription(Serializable f, String value) {
		if(isEmpty(value)) return null;
		else return new Description(f, value);
	}
	
	
	public Address createAddress(Serializable f, String value) {
		if(isEmpty(value)) return null;
		else return new Address(f, value);
	}

	public Serializable createUntypedAnnotation(Serializable f, String key, Serializable value) {
		if(isEmpty(key)) return null;
		if(isEmpty(value)) return null;
		
		return new KeyValueProperty(f,key,value);
	}
}
