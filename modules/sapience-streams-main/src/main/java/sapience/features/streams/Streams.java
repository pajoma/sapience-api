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

package sapience.features.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.opengis.feature.type.FeatureType;

import sapience.features.streams.util.Cache;

/**
 * The abstract Stream class, which has to be implemented by the concrete Parsers
 * 
 * @author pajoma
 *
 */
public abstract class Streams {
	
	/**
	 * Takes an InputStream, e.g. coming from a File, and creates the Feature object model
	 * 
	 * @param in
	 * @return
	 */
	public abstract FeatureSource read(InputStream in) throws IOException;
	
	/**
	 * Fetches resource from url (with cache) and create the Feature object model
	 * 
	 * @param in
	 * @return
	 */
	public List<FeatureType>  read(URL url) throws IOException {
		Cache c = new Cache(); 
		File f = c.fetch(url);  
		return this.read(new FileInputStream(f));		
	}

	
	/**
	 * Using file for creating Feature object model.
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public List<FeatureType>  read(File f) throws IOException {
		return this.read(f.toURI().toURL());
	}
	
	/**
	 * Takes a model and writes it into the given output stream
	 * 
	 * @param features
	 * @return
	 */
	public abstract void write(List<FeatureType>  types, OutputStream out) throws IOException;

}
