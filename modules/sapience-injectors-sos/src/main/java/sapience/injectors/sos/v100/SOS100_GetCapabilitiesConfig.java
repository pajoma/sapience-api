package sapience.injectors.sos.v100;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;


public class SOS100_GetCapabilitiesConfig implements Configuration {

	public String[] getAnnotationXPathExpressions() {

//		String p1 = "//{http://www.opengis.net/ows/1.1}ServiceIdentification/{http://www.opengis.net/ows/1.1}Keywords/{http://www.opengis.net/ows/1.1}Type[@{http://www.opengis.net/ows/1.1}codeSpace]/child()";
		String p1 = "//{http://www.opengis.net/ows/1.1}Metadata[@{http://www.w3.org/1999/xlink}href]/sibling()";
		return new String[] {p1};
	}

	public String getDefaultNamespace() {
		return "http://www.opengis.net/sos/1.0";
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}

	public String[] getHeaderPattern() {
		return new String[] { "<.*Capabilities.*", ".*version=\"1.0.0\".*", ".*\\sxmlns[:\\w]*=\"http://www.opengis.net/sos/1.0\".*" };

	}

	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}


}
