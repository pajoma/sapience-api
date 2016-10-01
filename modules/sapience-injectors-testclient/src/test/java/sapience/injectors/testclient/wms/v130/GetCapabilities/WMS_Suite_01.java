package sapience.injectors.testclient.wms.v130.GetCapabilities;

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
public class WMS_Suite_01 extends TestLifeCycle {

	
	private String sid;
	private String notAnnotatedFile = "wms130/wms130-capabilities-testcases/GetCapabilities.xml";
	private String annotatedFile = "wms130/wms130-capabilities-testcases/01-Layer-annotation.xml";

	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	
	public void testExtract() throws IOException {

		
			InputStream inputStream = null;
			sid = asHEX("http://example.com/wms/Suite_01");
			inputStream = getFileStream(annotatedFile);
			Configuration c = super.getConfiguration().getConfiguration(inputStream);
			List<Reference> extract = c.getExtractor().extract(sid, inputStream);
			super.getConfiguration().getLookup().put(sid,extract);
			List<Reference> check = super.getConfiguration().getLookup().check(sid);
			System.out.println(c.getClass().getName());
			for (int i = 0; i < check.size(); i++) {
				System.out.println(check.get(i));
			}
			assertEquals(1, check.size());		
	}
	
	
	public void testInject() throws IOException {
		InputStream inputStream = null;
		try {
			
			
			sid = asHEX("http://example.com/wms/Suite_01");
			inputStream = getFileStream(notAnnotatedFile);
			
			Configuration c = super.getConfiguration().getConfiguration(inputStream);
			Injector injector = c.getInjector();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			injector.annotate(sid, inputStream, baos);

			String result = new String(baos.toByteArray());		
			
			System.out.println(result);
					
			Pattern pattern1 = Pattern.compile(".*xlink:href=\"http://giv-wfs.uni-muenster.de:8080/jcr/repository/y2review/WCS11_Mapsrefrecbrgmfr/SRTM3.rdf\".*", Pattern.DOTALL);
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
