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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;

import sapience.injectors.exceptions.UnconfiguredMetadataTypeException;
import sapience.injectors.impl.AbstractInjector;


/**
 * The injector interface
 * 
 * @author pajoma
 *
 */
public interface Injector {
	
	/**
	 * Injects/Extract annotations into/from a String 
	 * 
	 * @see AbstractInjector#annotate(byte[])
	 * @param The String containing the metadata to be annotated
	 * @return The updated String
	 * @throws UnconfiguredMetadataTypeException
	 * @throws IOException
	 */
	public String annotate(Serializable docID, String str) throws IOException;
	
	/**
	 * Injects/Extract annotations into/from an InputStream
	 * 
	 * @param is
	 * @return the processed and modified InputStream
	 * @throws IOException 
	 */

	public void annotate(Serializable docID, InputStream input, OutputStream output) throws IOException;
	
	/**
	 * Injects/Extract annotations into/from a Reader
	 * 
	 * @param read
	 * @return
	 * @throws UnconfiguredMetadataTypeException
	 * @throws IOException
	 */
	public Reader annotate(Serializable docID, Reader reader) throws IOException;
	
	/**
	 * Injects/Extract annotations into/from a Byte Array. 
	 * 
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public byte[] annotate(Serializable docID, byte[] in) throws IOException;
	
	




	
	
}
