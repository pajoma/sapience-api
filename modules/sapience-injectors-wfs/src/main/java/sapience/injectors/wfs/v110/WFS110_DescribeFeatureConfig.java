package sapience.injectors.wfs.v110;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WFS110_DescribeFeatureConfig implements Configuration{
	
	public String[] getHeaderPattern() {

		return new String[] {"<\\w*:?schema.*",".*xmlns.*http://www.w3.org/2001/XMLSchema.*", ".*xmlns.*http://www.opengis.net/gml.*" };
	}

	public String[] getAnnotationXPathExpressions() {
		
		// see http://www.w3.org/TR/sawsdl/#annotateXSD
		String p1 = "//{http://www.w3.org/2001/XMLSchema}simpleType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p2 = "//{http://www.w3.org/2001/XMLSchema}element[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p3 = "//{http://www.w3.org/2001/XMLSchema}complexType[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";
		String p4 = "//{http://www.w3.org/2001/XMLSchema}complexType/{http://www.w3.org/2001/XMLSchema}attribute[@{http://www.w3.org/ns/sawsdl}modelReference]/attribute()";

		return new String[] {p1, p2, p3 ,p4};
	}

	public String getDefaultNamespace() {
		return "http://www.w3.org/2001/XMLSchema";
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}


	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

}
