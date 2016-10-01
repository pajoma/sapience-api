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

package sapience.injectors.factories;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.Lookup;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.util.HeaderDetector;
import sapience.injectors.util.LocalNamespaceContext;





public class ConfigurationFactory  {

	Logger log = Logger.getLogger("Injector.Config");

	private List<ExtendedConfigurations> configurations; 
	private HeaderDetector headerDetector;
	private NamespaceContext nscontext = null;

	private Lookup lookup;
	private ReferenceFactory reFac;
	
	private static ConfigurationFactory instance;






	private ConfigurationFactory() {
		headerDetector = new HeaderDetector();
		configurations = new ArrayList<ExtendedConfigurations>();
	}

	public static ConfigurationFactory get() {
		if(instance == null) {
			instance = new ConfigurationFactory();
		}
		return instance;
	}

	
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#registerInjector(sapience.injectors.Configuration)
	 */
	public void registerInjector(Configuration config) {
		try {
			ExtendedConfigurations ec = new ExtendedConfigurations(config);
			for(String patternString : config.getHeaderPattern()) {
				ec.addHeaderPattern(patternString);
			}
			configurations.add(ec);
			
		} catch (Exception e) {
			log.severe("Failed to compile pattern for config: "+config.getClass());
			throw new IllegalArgumentException("Illegal Pattern specified",e);
		}
	
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#loadConfiguration(java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	public void loadConfiguration(InputStream stream) throws IOException {
		log.info("Loading Injectors Configuration");
		
		if(stream == null) {
			throw new IOException("No valid input stream given. Path to config file correct?");
		}
		
		String attribute = "";
		try {
			DocumentBuilderFactory parserFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = parserFactory.newDocumentBuilder();
			Document doc = parser.parse(stream);
			
			// load configurations
			NodeList nodes = doc.getElementsByTagName("configuration");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element config = (Element) nodes.item(i);
				attribute = config.getAttribute("class");
				Class<? extends Configuration> cls = (Class<? extends Configuration>) Class.forName(attribute);
				Configuration newInstance = cls.newInstance();
				this.registerInjector(newInstance);
			}
		} catch (ClassNotFoundException e) {
			log.severe("Class not in class path: "+attribute);
			throw new IOException(e);
		} catch (ParserConfigurationException e) {
			log.severe("Failed to parse configuration file: "+e.getMessage());
			throw new IOException(e);
		} catch (SAXException e) {
			log.severe("Failed to parse configuration file: "+e.getMessage());
			throw new IOException(e);
		} catch (InstantiationException e) {
			log.severe("Failed to create instance. Does it implement the Injector-Interface? ");
			throw new IOException(e);
		} catch (IllegalAccessException e) {
			throw new IOException(e);
		} catch (Exception e) {
			log.severe("Unknown Error: "+e.getMessage());
			throw new IOException(e);
		} finally {
			//stream.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#setLookup(sapience.injectors.Lookup)
	 */
	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}


	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#setReferenceFactory(sapience.injectors.factories.ReferenceFactory)
	 */
	public void setReferenceFactory(ReferenceFactory reFac) {
		this.reFac = reFac;		
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getLookup()
	 */
	public Lookup getLookup() {
		return lookup;
	}




	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getReferenceFactory()
	 */
	public ReferenceFactory getReferenceFactory() {
		return reFac;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getInjector(java.net.URL)
	 */
	public Injector getInjector(URL fileURL) throws IOException {
		return this.getInjector(fileURL.openStream());
		
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getInjector(java.io.InputStream)
	 */
	public Injector getInjector(InputStream s) throws IOException {
		return getConfiguration(s).getInjector();
		
	}
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getExtractor(java.io.InputStream)
	 */
	public Extractor getExtractor(InputStream s) throws IOException {
		return getConfiguration(s).getExtractor();
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getNamespaceContext()
	 */
	public NamespaceContext getNamespaceContext() {
		if(nscontext == null) {
			nscontext = new LocalNamespaceContext();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.impl.ConfigurationFactory#getConfiguration(java.io.InputStream)
	 */
	public Configuration getConfiguration(InputStream stream) throws IOException {
		   if (stream == null) {
	            throw new IllegalArgumentException("InputStream is missing");
	        }
		
		if(! stream.markSupported()) {
			throw new IOException("mark/reset not supported. Try BufferedInputStream.");
		}
		
     
       
        String header = headerDetector.readXMLHeader(stream);
        for(ExtendedConfigurations ec : configurations) {
        	if(ec.headerMatches(header)) return ec.getConfiguration();
        }

        
        throw new IOException("No matching injector found");
	}
	

	private class ExtendedConfigurations {
		private List<Pattern> headerPatterns = null;
		private final Configuration config;
		
		void addHeaderPattern(String patternString) {
			Pattern compiled = Pattern.compile(patternString,Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
			headerPatterns.add(compiled);
		}
		
		Configuration getConfiguration() {
			return config;
		}

		ExtendedConfigurations(Configuration config) {
			this.config = config;
			headerPatterns = new ArrayList<Pattern>();
		}
		
		boolean headerMatches(String text) {
			// it has to match every pattern
			boolean res = true;
			for (Pattern p : headerPatterns) {
				if (!p.matcher(text).find())
					res = false;
			}
			//for (Pattern p : headerPatterns) {
			//	res = p.matcher(text).matches();
			
			return res;
		}
	}

	
	


}
