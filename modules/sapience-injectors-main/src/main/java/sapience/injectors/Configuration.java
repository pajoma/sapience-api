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

public interface Configuration {
	
	/**
	 * Returns the pattern which we match the header of the XML document against. In many cases multiple
	 * patterns have to match (e.g. the name of the element and a namespace definition).
	 *  
	 * @return
	 * 	a list with Regular Expressions
	 */
	public String[] getHeaderPattern();
	
	
	/**
	 * The patterns we use to match existing XML elements against. See documentation for more details.
	 * @return
	 */
	public String[] getAnnotationXPathExpressions();
	
	
	/**
	 * Returns the Injector suitable for the document type. In most case, the BaseInjector should be sufficient
	 * @return
	 * @throws IOException
	 */
	public Injector getInjector() throws IOException;
	
	/**
	 * Returns the Extractor
	 * @return
	 * @throws IOException
	 */
	public Extractor getExtractor() throws IOException;
	
	public String getDefaultNamespace();
}
