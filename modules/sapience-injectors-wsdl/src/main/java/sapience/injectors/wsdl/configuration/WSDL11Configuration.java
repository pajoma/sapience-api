package sapience.injectors.wsdl.configuration;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WSDL11Configuration  implements Configuration {

	public String[] getHeaderPattern() {
		return new String[] {".*xmlns.*http://schemas.xmlsoap.org/wsdl.*"};
	}



	public String[] getAnnotationXPathExpressions() {
		// see http://www.w3.org/TR/sawsdl/#S5
		String p1 = "//{http://www.w3.org/ns/sawsdl}attrExtensions[@{http://www.w3.org/ns/sawsdl}modelReference]/child()";
		String p2 = "//{http://schemas.xmlsoap.org/wsdl/}message/{http://schemas.xmlsoap.org/wsdl/}part[@name and @type and @{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p3 = "//{http://schemas.xmlsoap.org/wsdl/}portType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p8 = "//{http://schemas.xmlsoap.org/wsdl/}portType/{http://schemas.xmlsoap.org/wsdl/}fault[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		
		// see http://www.w3.org/TR/sawsdl/#annotateXSD
		
		String p4 = "//{http://schemas.xmlsoap.org/wsdl/}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}simpleType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p5 = "//{http://www.w3.org/2001/XMLSchema}element[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p6 = "//{http://schemas.xmlsoap.org/wsdl/}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}complexType[@name and @{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p7 = "//{http://www.w3.org/2001/XMLSchema}attribute[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		
		
		return new String[] {p1, p2, p3, p4, p5, p6, p7, p8};
	}

	public String getDefaultNamespace() {
		return "http://schemas.xmlsoap.org/wsdl";
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}


	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

}
