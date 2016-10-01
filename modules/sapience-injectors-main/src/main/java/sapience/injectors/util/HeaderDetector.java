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

package sapience.injectors.util;

import java.io.IOException;
import java.io.InputStream;

public class HeaderDetector {
		
	/**
		 * Goes through the input stream, and returns the root element including all namespaces. We always assume it is the first element (after <xml>)
		 * @param root
		 * @param stream
		 * @return
		 * @throws IOException 
		 */
		public String readXMLHeader(InputStream stream) throws IOException {		
			if(! stream.markSupported()) {
				 throw new IllegalArgumentException("Stream does not support resetting, try BufferedInputStream");
			}
			stream.mark(512);
			
			// read header 
			StringBuilder res = this.readNextElement(stream);
			
			stream.reset();
			return res.toString();
		
		}
		
		
		
		
		private StringBuilder readNextElement(InputStream stream) throws IOException {
			
			char c;
			boolean in = false;
			StringBuilder res = new StringBuilder();
			StringBuilder test = new StringBuilder();
			
	        while ((c = (char) stream.read()) != -1) {
	        	switch (c) {
				case '<':	// <
					in = true; 
					res.append(c);
					break;
				case '>':
					res.append(c);
					// xml declarations and comments  are evil 
					if((res.charAt(1) == '?') || (res.charAt(1) == '!')) {		
						return this.readNextElement(stream);
					}
					return res;
				default:
					if(in == true) {
						res.append(c);
					} else {
						test.append(c);
					}
				}
	         }
	        
	        throw new IOException("Invalid XML");
		}
}

