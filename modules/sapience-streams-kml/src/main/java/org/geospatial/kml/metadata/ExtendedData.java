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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @see http://code.google.com/apis/kml/documentation/kmlreference.html#extendeddata
 * @author pajoma
 *
 */
@XStreamAlias("ExtendedData")
public class ExtendedData implements Serializable {
	private static final long serialVersionUID = -8780134583271134727L;
	
	@XStreamImplicit
	private
	List<ExtendedData> extendedData;

	public void set(List<ExtendedData> extendedData) {
		this.extendedData = extendedData;
	}
	
	public void add(ExtendedData extendedData) {
		this.list().add(extendedData);
	}
	
	

	public List<ExtendedData> list() {
		if (extendedData == null) {
			extendedData = new ArrayList<ExtendedData>();
			
		}
		return extendedData;
	} 
	
	
	
	
}
