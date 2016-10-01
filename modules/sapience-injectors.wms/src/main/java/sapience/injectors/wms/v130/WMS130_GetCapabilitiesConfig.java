package sapience.injectors.wms.v130;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WMS130_GetCapabilitiesConfig implements Configuration{

	public String[] getAnnotationXPathExpressions() {
//		String p1 = "//{http://www.opengis.net/wms}Layer/{http://www.opengis.net/wfs}FeatureType/{http://www.opengis.net/ows}Keywords/{http://www.opengis.net/ows}Type[@{http://www.opengis.net/ows}codeSpace]/sibling()";
		String p1 = "//{http://www.opengis.net/wms}Layer/{http://www.opengis.net/wms}Layer/{http://www.opengis.net/wfs}MetadataURL/sibling()";
		return new String[] {p1};
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}

	public String[] getHeaderPattern() {
		return new String[] { "<\\w*:?WMS_Capabilities.*", ".*.*\\sxmlns[:\\w]*=\"http://www.opengis.net/wms\".*", ".*version=\"1.3.0\".*",  };
	}

	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

	public String getDefaultNamespace() {
		return "http://www.opengis.net/wms";
	}

}
