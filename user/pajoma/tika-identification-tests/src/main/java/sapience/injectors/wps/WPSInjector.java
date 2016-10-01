package sapience.injectors.wps;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.xml.sax.InputSource;

import sapience.injectors.impl.AbstractInjector;

public class WPSInjector extends AbstractInjector {

	@Override
	public void annotate(URI uri, InputStream input, OutputStream output)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeAnnotation(URI uri, InputSource is) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMatchingPattern() {
		return ".*xmlns.*http://www.opengis.net/wps/1.0.0.*";
	}

}
