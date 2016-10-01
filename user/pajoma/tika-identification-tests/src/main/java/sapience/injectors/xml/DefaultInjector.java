package sapience.injectors.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import sapience.injectors.Lookup;
import sapience.injectors.impl.AbstractInjector;
import sapience.injectors.wsdl.LookupMockUp;

public class DefaultInjector extends AbstractInjector {
	
	
	@Override
	public void annotate(URI uri, InputStream input, OutputStream output)
			throws Exception {
		Lookup l = new LookupMockUp();
		
	}

}
