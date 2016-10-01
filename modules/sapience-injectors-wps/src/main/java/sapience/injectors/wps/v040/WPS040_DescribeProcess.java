package sapience.injectors.wps.v040;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

public class WPS040_DescribeProcess implements Configuration {

	
	/* (non-Javadoc)
	 * @see sapience.injectors.Configuration#getExtractor()
	 */
	public Extractor getExtractor() throws IOException {
		return new DefaultExtractor(this);
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.Configuration#getInjector()
	 */
	public Injector getInjector() throws IOException {
		return new DefaultInjector(this);
	}

	/* (non-Javadoc)
	 * @see sapience.injectors.Configuration#getHeaderPattern()
	 */
	public String[] getHeaderPattern() {
		return new String[] {".*ProcessDescriptions.*", ".*http://www.opengeospatial.net/wps.*"};

	}

	
	/* (non-Javadoc)
	 * @see sapience.injectors.Configuration#getAnnotationXPathExpressions()
	 */
	public String[] getAnnotationXPathExpressions() {
		String p1 = "//{http://www.opengeospatial.net/wps}ProcessDescription/{http://www.opengeospatial.net/ows}Metadata[@{http://www.w3.org/1999/xlink}href and @{http://www.w3.org/1999/xlink}arcrole]/sibling()";
		String p2 = "//{http://www.opengeospatial.net/wps}Input/{http://www.opengeospatial.net/ows}Metadata[@{http://www.w3.org/1999/xlink}href and @{http://www.w3.org/1999/xlink}arcrole]/sibling()";
		String p3 = "//{http://www.opengeospatial.net/wps}Output/{http://www.opengeospatial.net/ows}Metadata[@{http://www.w3.org/1999/xlink}href and @{http://www.w3.org/1999/xlink}arcrole]/sibling()";
		return new String[] {p1,p2,p3};
	}


	public String getDefaultNamespace() {
		return "http://www.opengeospatial.net/wps";
	}
}
