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

package org.geospatial.kml.metadata;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Data inside the ExtendedData Element, encoded as simple key-value-pair
 * 
 * @author pajoma
 *
 */
@XStreamAlias("Data")
public class Data extends ExtendedData {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamAlias("value")
	private String value;

	public Data(String key, String value) {
		this.value = value;
		this.name = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Serializable getValue() {
		return value;
	} 
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(this.getName());
		sb.append("] ");
		sb.append(this.getValue());
		return sb.toString();
		
	}
	
}
