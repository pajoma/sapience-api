package sapience.injectors.testclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.Adler32;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


/**
 * Testing mimetype and schema detection
 * @author pajoma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-tests.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class BaseTest {
	
	
	private URL file;

	
	@Before
	public void setup () {		
		// load file
		String name = this.getFileName();
		if(name != null) {
			String path = "samples/".concat(this.getFileName());
			file = BaseTest.class.getClassLoader().getResource(path);
		}
		
		
	}
	
	
	
	public abstract String getFileName();
	
	public URL getFileURL() {
		return file;
	}
	
	public InputStream getFileStream() throws IOException {
		return file.openStream();
	}
	
	public InputStream getFileStream(String filename) throws IOException {
		String path = "samples/".concat(filename);
		InputStream stream = BaseTest.class.getClassLoader().getResourceAsStream(path);
		
		if(stream == null) {
			System.err.println("Failed to load file: "+filename);
		}
		Assert.assertNotNull(stream);
		return stream;
	}
	
	
	protected String asHEX(String serviceRequest) {
		Adler32 adler32 = new Adler32();
		adler32.update(serviceRequest.getBytes());
		return Long.toHexString(adler32.getValue());
	}


}
