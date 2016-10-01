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
import java.io.Serializable;
import java.util.List;

import sapience.injectors.Configuration;
import sapience.injectors.model.Reference;
import sapience.injectors.stax.inject.ModelBasedStaxStreamInjector;

/**
 * The base injector is a generic solution for injections, and should be sufficient for most cases
 * @author pajoma
 *
 */
public class DefaultInjector extends AbstractInjector {

	
	public DefaultInjector(Configuration config) {
		super(config);
	}


	/* (non-Javadoc)
	 * @see sapience.injectors.impl.AbstractInjector#annotate(java.net.URI, java.io.InputStream, java.io.OutputStream)
	 */
	public void annotate(Serializable uri, InputStream input, OutputStream output)
			throws IOException {
		super.getDocumentID(ensureURI(uri));	
		 
		/* Helper (doing all the Stax stuff with woodstox) */
		ModelBasedStaxStreamInjector helper = new ModelBasedStaxStreamInjector(getConfiguration());
		List<Reference> refs = super.retrieveReferencesFromDatabase();
		
		helper.inject(input, output, refs);
		
	}



	
	/** private stuff */

}
