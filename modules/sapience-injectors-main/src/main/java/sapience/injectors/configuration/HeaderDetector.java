package sapience.injectors.configuration;

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
			if(res.charAt(1) == '?') {
				res = this.readNextElement(stream);
			}
			
			stream.reset();
			return res.toString();
		
		}
		
		private StringBuilder readNextElement(InputStream stream) throws IOException {
			
			
			char c;
			boolean in = false;
			StringBuilder res = new StringBuilder();
			
	        while ((c = (char) stream.read()) != -1) {
	        	switch (c) {
				case '<':	// <
					in = true; 
					res.append(c);
					break;
				case '>':
					res.append(c);
					return res;
				default:
					if(in == true) {
						res.append(c);
					}
				}
	         }
	        
	        throw new IOException("Invalid XML");
		}
}

