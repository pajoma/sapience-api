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

package sapience.injectors.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;


public abstract class AbstractInjector implements Injector {
	
	/**
	 * TODO: we may replace this with a more generic approach, to make it possible for the coupled library to register their own loggers
	 * (if this is even possible)
	 * 
	 */
	protected static Logger LOG = Logger.getLogger(AbstractInjector.class.getName());
	
	private final ConfigurationFactory cfac = ConfigurationFactory.get();
	
	
	// the ID of the document to be annotated
	protected Serializable serviceRequest = null;


	private Configuration config;
	
	public AbstractInjector(Configuration config) {
		this.config = config;
	}
	
	public Configuration getConfiguration() {
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sapience.injectors.Injector#annotate(java.net.URI, byte[])
	 */
	public byte[] annotate(Serializable docID, byte[] in) throws IOException {

		// copy the byte array, will be returned if exception is thrown
		byte[] copy = in.clone();

		// the stream the result is written to. With original length as initial
		// length
		ByteArrayOutputStream baos = new ByteArrayOutputStream(in.length);

		/*
		 * procedure
		 * 
		 * We have - unique document id (UDI) - access to a lookup component to
		 * get references - a list of definitions telling us where
		 * modelreferences can exist in the current document type
		 * 
		 * 1. we call the check method with the UDI, which returns a list of
		 * references for the UDI (LookUpList) 2. we delegate it to the sax
		 * handler of the extending packages a. for each line, we first check if
		 * it is valid location (comparing the first bytes) a.+: check if
		 * current line (constructed Xpath) is in LookUpList a.+.+ check if
		 * current line contains a model reference a.+.+.+ call put of lookup
		 * component a.+.+.- inject annotation a.+.- continue a.-: continue
		 * 
		 * 3. return the bytearray with the injected annotations
		 */

		// if doc type is not configured, we throw an exception and return the
		// input stream unchanged
		try {

			InputSource is = new InputSource(new String(copy));

			// we let the extending classes handle the actual content
			this.annotate(docID, is.getByteStream(), baos);

		} catch (Exception e) {
			return copy;
		} finally {
			baos.flush();
		}

		byte[] res = baos.toByteArray();
		baos.close();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sapience.injectors.Injector#annotate(java.net.URI,
	 * java.io.InputStream)
	 */
	public abstract void annotate(Serializable docID, InputStream input,
			OutputStream output) throws IOException;

	public Reader annotate(Serializable docID, Reader reader) throws IOException {
		byte[] processed = this.annotate(docID, IOUtils.toByteArray(reader));
		return new StringReader(new String(processed));
	}

	public String annotate(Serializable docID, String str)
			throws IOException {
		Reader rd = this.annotate(docID, new StringReader(str));
		return IOUtils.toString(rd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sapience.injectors.Injector#annotate(java.net.URI, java.io.Reader)
	 */

	public URI ensureURI(Serializable ser) throws IOException{
		try {
			URI uri = new URI(ser.toString());
			return uri;
		} catch (URISyntaxException e) {
			
			throw new IOException(e);
		}
		
	}

	
	public Serializable getServiceRequest() {
		return serviceRequest;
	}
	
	public void setServiceRequest(Serializable uri){
		this.serviceRequest = uri;
	}
	


	protected List<Reference> retrieveReferencesFromDatabase() throws IOException {
		// search for references in the database
		
		if(this.serviceRequest == null) {
			throw new IOException("Failed to check for references: service request not assigned");
		}

		if(cfac.getLookup() == null) {
			throw new IOException("Failed to check for references: Lookup Implementation not set");
		}

		try {
			return cfac.getLookup().check(this.serviceRequest);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "Failed to check for references");
			throw new IOException(e);
		}


	}
	
	

	


	public void returnUnchanged(Serializable docID, InputStream input, OutputStream output) throws IOException {
		
		// write InputStream to OutputStream
		try {
			IOUtils.write(IOUtils.toByteArray(input), output);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "Failed to write InputStream to OutputStream");
			throw new IOException(e);
		}
			
			
		
	}
	

	public void getDocumentID(URI uri) {
		this.serviceRequest = cfac.getReferenceFactory().createDocumentName(uri);
	}
}
