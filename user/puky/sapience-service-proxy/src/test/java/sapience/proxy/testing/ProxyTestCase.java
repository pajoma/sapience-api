package sapience.proxy.testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Check: http://code.google.com/appengine/docs/java/howto/unittesting.html
 * @author pajoma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class ProxyTestCase {

	@Autowired()
	GoogleHelpersFactory fac;
	

	
	protected InputStream getFileStream(String filename) throws FileNotFoundException {
		//URL res = ProxyTestCase.class.getClassLoader().getResource("testing/samples/".concat(filename));
		InputStream stream = ProxyTestCase.class.getClassLoader().getResourceAsStream("samples/".concat(filename));
		return stream;
	}

}
