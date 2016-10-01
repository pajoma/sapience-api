package sapience.injectors.wsdl.configuration;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WSDL20Configuration  implements Configuration {

	public String[] getHeaderPattern() {
		return new String[] {".*xmlns.*http://www.w3.org/ns/wsdl.*"};
	}



	public String[] getAnnotationXPathExpressions() {
		// see http://www.w3.org/TR/sawsdl/#annotateWSDLInterfaces
		String p1 = "//{http://www.w3.org/ns/wsdl}interface/{http://www.w3.org/ns/wsdl}operation[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p2 = "//{http://www.w3.org/ns/wsdl}interface[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p3 = "//{http://www.w3.org/ns/wsdl}interface/{http://www.w3.org/ns/wsdl}fault[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p8 = "//{http://www.w3.org/ns/wsdl}interface/{http://www.w3.org/ns/wsdl}operation/{http://www.w3.org/ns/wsdl}input[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p9 = "//{http://www.w3.org/ns/wsdl}interface/{http://www.w3.org/ns/wsdl}operation/{http://www.w3.org/ns/wsdl}output[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		
		
		// see http://www.w3.org/TR/sawsdl/#annotateXSD
		String p4 = "//{http://www.w3.org/ns/wsdl}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}simpleType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p5 = "//{http://www.w3.org/ns/wsdl}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}element[@{http://www.w3.org/ns/sawsdl}modelReference and @{http://www.w3.org/ns/sawsdl}liftingSchemaMapping]/attribute()";
		String p6 = "//{http://www.w3.org/ns/wsdl}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}complexType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p7 = "//{http://www.w3.org/ns/wsdl}types/{http://www.w3.org/2001/XMLSchema}schema/{http://www.w3.org/2001/XMLSchema}complexType/{http://www.w3.org/2001/XMLSchema}attribute[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		
		return new String[] {p1, p2, p3, p4, p5, p6, p7, p8, p9};
	}

	public String getDefaultNamespace() {
		return "http://www.w3.org/ns/wsdl";
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}


	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

}
