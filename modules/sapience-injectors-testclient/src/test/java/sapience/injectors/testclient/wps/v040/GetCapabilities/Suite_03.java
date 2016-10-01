package sapience.injectors.testclient.wps.v040.GetCapabilities;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;

public class Suite_03 extends TestLifeCycle {

	@Autowired ConfigurationFactory config;
	private String sid;
	private String notAnnotatedFile = "wps04/wps04-capabilities-testcases/GetCapabilities-plain.xml";
	private String annotatedFile = "wps04/wps04-capabilities-testcases/00-all-annotation.xml";
	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}

	public void testExtract() throws IOException {

		
			InputStream inputStream = null;
			sid = asHEX("http://example.com/wps04/getCapabilities/Suite_03");
			inputStream = getFileStream(annotatedFile);
			Configuration c = config.getConfiguration(inputStream);
			config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
			List<Reference> check = config.getLookup().check(sid);
			assertEquals(8, check.size());		
	}
	
	
	public void testInject() throws IOException {
		InputStream inputStream = null;
		try {
			
			
			sid = asHEX("http://example.com/wps04/getCapabilities/Suite_03");
			inputStream = getFileStream(notAnnotatedFile);
			
			Configuration c = config.getConfiguration(inputStream);
			Injector injector = c.getInjector();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			injector.annotate(sid, inputStream, baos);

			String result = new String(baos.toByteArray());		
			
			System.out.println(result);
								
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		
	}

	@Override
	public String getFileName() {
		return "";
	}
}