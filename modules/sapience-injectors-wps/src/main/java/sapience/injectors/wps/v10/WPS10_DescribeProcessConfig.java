package sapience.injectors.wps.v10;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WPS10_DescribeProcessConfig implements Configuration {

	public String[] getAnnotationXPathExpressions() {
		String p1 = "//{http://www.opengis.net/ows/1.1}Metadata[@{http://www.w3.org/1999/xlink}href and @{http://www.w3.org/1999/xlink}arcrole]/sibling()";
		
		return new String[] {p1};
	}

	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}

	public String[] getHeaderPattern() {
		return new String[] { "<.*ProcessDescriptions .*", ".*service=\"WPS\".*", ".*version=\"1.0.0\".*",  };
	}

	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}
	
	public String getDefaultNamespace() {
		return "http://www.opengis.net/wps/1.0.0";
	}

}
