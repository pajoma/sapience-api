package sapience.proxy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Adler32;

import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.factories.ReferenceFactory;
import sapience.proxy.persistence.model.JDOModelFactory;

public class Examples {
	private @Autowired ReferenceFactory fac;

	private @Autowired ConfigurationFactory config;
	
	public Examples(ConfigurationFactory cfac) {
	    config = cfac;
	    fac = config.getReferenceFactory();
	}
	
	void example_WSDL20Interface() throws MalformedURLException, IOException {
		String plain = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl2.0/00-plain.wsdl";
		String annotated = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl2.0/01-interface-annotation.wsdl";
		InputStream inputStream = null;
		String sid = generateServiceID(annotated);
		inputStream = new URL(annotated).openStream();
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(plain, c.getExtractor().extract(sid, inputStream));
	}
	
	void example_WSDL11Operation() throws MalformedURLException, IOException {
		String plain = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl1.1/00-plain.wsdl";
		String annotated = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl1.1/03-operation-annotation.wsdl";
		InputStream inputStream = null;
		String sid = generateServiceID(annotated);
		inputStream = new URL(annotated).openStream();
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(plain, c.getExtractor().extract(sid, inputStream));
	}
	
	void example_WSDL20XMLSchema() throws MalformedURLException, IOException {
		String plain = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl2.0/00-plain.wsdl";
		String annotated = "http://www.w3.org/2002/ws/sawsdl/CR/wsdl2.0/06-complextype-annotation.wsdl";
		InputStream inputStream = null;
		String sid = generateServiceID(annotated);
		inputStream = new URL(annotated).openStream();
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(plain, c.getExtractor().extract(sid, inputStream));
	}

	
	void example_WPS10Process() throws MalformedURLException, IOException {
		String plain = "http://schemas.opengis.net/wps/1.0.0/examples/40_wpsDescribeProcess_response.xml";
		String annotated = "Examples/40_wpsDescribeProcess_response.xml";
		InputStream inputStream = null;
		String sid = generateServiceID(plain);
		
		inputStream = this.getClass().getClassLoader().getResourceAsStream(annotated);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(plain, c.getExtractor().extract(sid, inputStream));
	}
	
	
	void example_WPS10GetCapabilities() throws MalformedURLException, IOException {
		String plain = "http://schemas.opengis.net/wps/1.0.0/examples/20_wpsGetCapabilities_response.xml";
		String annotated = "Examples/20_wpsGetCapabilities_response.xml";
		InputStream inputStream = null;
		String sid = generateServiceID(plain);
		
		
		inputStream = this.getClass().getClassLoader().getResourceAsStream(annotated);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(plain, c.getExtractor().extract(sid, inputStream));
	}
	
	
	
	private String generateServiceID(String serviceRequest) {
		Adler32 adler32 = new Adler32();
		adler32.update(serviceRequest.getBytes());
		return Long.toHexString(adler32.getValue());
	}
}
