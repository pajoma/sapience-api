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

package sapience.injectors;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import sapience.injectors.model.Reference;




/**
 * The interface of the lookup component
 * So every Injector is able to use a check and a put method.
 * 
 * @author Henry Michels
 */
public interface Lookup {
	
	/**
	 * This method returns all References, which contain the given URI.
	 * @param uri
	 * @return list of References
	 */
	public List<Reference> check(Serializable request)throws IOException;
	
	/**
	 * This method adds or updates the given reference in the database.
	 * @param reference
	 */
	public void put(String request, Reference reference)throws IOException;

	
	
	/**
	 * Adds or updates a list of references in the database
	 * @param refList
	 * @throws IOException 
	 */
	public void put(String request, List<Reference> refList) throws IOException;
	
	

	
}
