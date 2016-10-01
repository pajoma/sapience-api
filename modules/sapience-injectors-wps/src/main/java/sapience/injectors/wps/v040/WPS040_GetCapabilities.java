package sapience.injectors.wps.v040;

import java.io.IOException;

import sapience.injectors.Configuration;
import sapience.injectors.Extractor;
import sapience.injectors.Injector;
import sapience.injectors.impl.DefaultExtractor;
import sapience.injectors.impl.DefaultInjector;

/**
 * Configuration for WPS GetCapabilities in version 0.4.0
 * @author pajoma
 *
 */
public class WPS040_GetCapabilities implements Configuration {

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
		return new String[] {".*Capabilities.*", ".*http://www.opengeospatial.net/wps.*",".*version=\"0.4.0\".*"};
	}


	/* (non-Javadoc)
	 * @see sapience.injectors.Configuration#getAnnotationXPathExpressions()
	 */
	public String[] getAnnotationXPathExpressions() {

		String p1 = "//{http://www.opengeospatial.net/ows}Metadata[@{http://www.w3.org/1999/xlink}href and @{http://www.w3.org/1999/xlink}arcrole]/sibling()";
		String p2 = "//{http://www.opengeospatial.net/ows}ServiceIdentification/{http://www.opengeospatial.net/ows}Keywords/{http://www.opengeospatial.net/ows}Type[@{http://www.opengeospatial.net/ows}codeSpace]/child()";
		return new String[] {p1,p2};
	
	}
	

		
		public String getDefaultNamespace() {
			return "http://www.opengeospatial.net/wps";
		}
}
