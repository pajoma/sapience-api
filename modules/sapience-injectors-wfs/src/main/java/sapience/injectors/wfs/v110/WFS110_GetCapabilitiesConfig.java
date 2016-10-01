package sapience.injectors.wfs.v110;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WFS110_GetCapabilitiesConfig implements Configuration{

	public String[] getAnnotationXPathExpressions() {
//		String p1 = "//{http://www.opengis.net/ows}ServiceIdentification/{http://www.opengis.net/ows}Keywords/{http://www.opengis.net/ows}Type[@{http://www.opengis.net/ows}codeSpace]/child()";
		String p1 = "//{http://www.opengis.net/ows}Metadata[@{http://www.w3.org/1999/xlink}href]/sibling()";
		String p2 = "//{http://www.opengis.net/wfs}FeatureTypeList/{http://www.opengis.net/wfs}FeatureType/{http://www.opengis.net/ows}Keywords/{http://www.opengis.net/ows}Type[@{http://www.opengis.net/ows}codeSpace]/sibling()";
		String p3 = "//{http://www.opengis.net/wfs}FeatureTypeList/{http://www.opengis.net/wfs}FeatureType/{http://www.opengis.net/wfs}MetadataURL/sibling()";
		return new String[] {p1,p2,p3};
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}

	public String[] getHeaderPattern() {
		return new String[] { "<.*Capabilities.*", ".*xmlns.*http://www.opengis.net/wfs.*", ".*version=\"1.1.0\".*",  };
	}

	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

	public String getDefaultNamespace() {
		return "http://www.opengis.net/wfs";
	}

}
