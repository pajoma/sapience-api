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

package sapience.annotations.model;

import java.io.Serializable;


public class KeyValueProperty extends Annotation {
	private static final long serialVersionUID = -7704595416248137926L;
	
	private final String key;
	private Serializable value;

	/**
	 * Creates a new Key-Value-Property. If given key already exists, the old value will be replaced 
	 * with the new value
	 * @param annotatedObject
	 * @param key
	 * @param value
	 */
	public KeyValueProperty(Serializable annotatedObject, String key, Serializable value) {
		super(annotatedObject);
		this.key = key;
		this.setValue(value);
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public Serializable getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(getKey());
		sb.append(",");
		sb.append(getValue());
		sb.append(")");
		
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof KeyValueProperty) {
			KeyValueProperty kvp = (KeyValueProperty) obj;
			if(kvp.getKey().equalsIgnoreCase(this.getKey())) return true;
		}
		return false;
	}

	
	
}
