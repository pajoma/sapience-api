package sapience.injectors;

import static junit.framework.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.identify.TestingIdentification;
import org.apache.tika.identify.wsdl11.WSDL11Identification;
import org.junit.Before;
import org.junit.Test;

import sapience.injectors.wsdl.v11.WSDLInjector1_1;

public class TestWSDLInjector extends TestingIdentification{

	
	@Before
	public void setupp() throws IOException {
		InputStream resourceAsStream = TestWSDLInjector.class.getClassLoader().getResourceAsStream("InjectorConfig.xml");
		assertNotNull(resourceAsStream);
		
		SelectorFactory.getInstance().loadConfig(resourceAsStream);
	}
	
	@Test
	public void testWSDLstuff() throws IOException {
		
		Injector injector = SelectorFactory.getInstance().getInjector(getFileURL().openStream());
		assertTrue(injector instanceof WSDLInjector1_1);
	}
	

	@Override
	public String getFileName() {
		return "TestWSDL_1_1(noRefs).wsdl";
	}


}
