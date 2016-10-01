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
package sapience.injectors.model;

import java.io.Serializable;

/**
 * @author Henry Michels
 *
 */
public class ServiceRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String host;
	
	private String query;

	/**
	 * 
	 * @param string
	 * @param query
	 */
	public ServiceRequest(String string, String query) {
		super();
		this.host = string;
		this.query = query;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	
	@Override
	public String toString() {
		String uri = this.host + this.query;
		return uri;
	}

}
