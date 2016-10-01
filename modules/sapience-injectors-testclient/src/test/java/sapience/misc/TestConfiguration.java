package sapience.misc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.testclient.BaseTest;

public class TestConfiguration extends BaseTest {
	@Autowired ConfigurationFactory configFactory;
	
	 @Test
		public void testSomething() {
			assertTrue(true);
		}
	
//	@Test
//	public void testLocadConfog() throws IOException {
//		String sid = asHEX("http://example.com/wps");
//		InputStream inputStream = getFileStream("wps-describeProcess-0.4.0.xml");
//		
//		Configuration config = configFactory.getConfiguration(inputStream);
//		Extractor e = config.getExtractor();
//		assertNotNull(e);
//
//
//	}

	@Override
	public String getFileName() {
			return "/wps04/wps04-capabilities-testcases/wps-describeProcess-0.4.0.xml";
	}
	
}
