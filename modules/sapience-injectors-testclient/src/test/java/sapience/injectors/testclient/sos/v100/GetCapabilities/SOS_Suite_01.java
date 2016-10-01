package sapience.injectors.testclient.sos.v100.GetCapabilities;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-tests.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SOS_Suite_01 extends TestLifeCycle {

	
	private String sid;
	private String notAnnotatedFile = "sos100/sos100-capabilities-testcases2/GetCapabilities-plain.xml";
	private String annotatedFile = "sos100/sos100-capabilities-testcases2/01-ServiceIdentification-annotation.xml";

	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	
	public void testExtract() throws IOException {

		
			InputStream inputStream = null;
			sid = asHEX("http://example.com/sos/Suite_01");
			inputStream = getFileStream(annotatedFile);
			Configuration c = super.getConfiguration().getConfiguration(inputStream);
			super.getConfiguration().getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
			List<Reference> check = super.getConfiguration().getLookup().check(sid);
			System.out.println(c.getClass().getName());
			for (int i = 0; i < check.size(); i++) {
				System.out.println(check.get(i));
			}
			assertEquals(3, check.size());		
	}
	
	
	public void testInject() throws IOException {
		InputStream inputStream = null;
		try {
			
			
			sid = asHEX("http://example.com/sos/Suite_01");
			inputStream = getFileStream(notAnnotatedFile);
			
			Configuration c = super.getConfiguration().getConfiguration(inputStream);
			Injector injector = c.getInjector();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			injector.annotate(sid, inputStream, baos);

			String result = new String(baos.toByteArray());		
			
			System.out.println(result);
					
			Pattern pattern1 = Pattern.compile(".*xlink:arcrole=\"http://role.org/posm\" xlink:href=\"http://giv-wfs.uni-muenster.de/jcr/repository/testing/SOS10_BrgmSos/Depth.rdf\".*", Pattern.DOTALL);
			Assert.assertTrue(pattern1.matcher(result).matches());
		
								
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
