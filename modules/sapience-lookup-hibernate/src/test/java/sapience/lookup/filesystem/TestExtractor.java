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

/**
 * 
 */
package sapience.lookup.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import sapience.injectors.Extractor;
import sapience.injectors.model.Reference;

/**
 * @author Henry
 *
 */
public class TestExtractor implements Extractor {
	
	private boolean store = false;
	
	public boolean getStore(){
		return store;
	}

	public void storeAnnotation(Serializable docID, InputStream is){
		store = true;
	}

	public List<Reference> extract(Serializable docID, InputStream is)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}



}
