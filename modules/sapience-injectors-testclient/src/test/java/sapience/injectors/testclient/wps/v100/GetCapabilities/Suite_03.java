package sapience.injectors.testclient.wps.v100.GetCapabilities;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.Assert;

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
	private String notAnnotatedFile = "wps100/wps100-capabilities-testcases/GetCapabilities-plain.xml";
	private String annotatedFile = "wps100/wps100-capabilities-testcases/00-all-annotation.xml";
	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}

	public void testExtract() throws IOException {

		
			InputStream inputStream = null;
			sid = asHEX("http://example.com/wps/getCapabilities/Suite_03");
			inputStream = getFileStream(annotatedFile);
			Configuration c = config.getConfiguration(inputStream);
			config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
			List<Reference> check = config.getLookup().check(sid);
			for (int i = 0; i < check.size(); i++) {
				System.out.println(check.get(i));
			}
			assertEquals(2, check.size());		
	}
	
	
	public void testInject() throws IOException {
		InputStream inputStream = null;
		try {
			
			
			sid = asHEX("http://example.com/wps/getCapabilities/Suite_03");
			inputStream = getFileStream(notAnnotatedFile);
			
			Configuration c = config.getConfiguration(inputStream);
			Injector injector = c.getInjector();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			injector.annotate(sid, inputStream, baos);

			String result = new String(baos.toByteArray());		
			
			System.out.println(result);
			Pattern pattern1 = Pattern.compile(".*ows:codeSpace=\"http://core/WebServices/52N/WPS\".*", Pattern.DOTALL);
			Assert.assertTrue(pattern1.matcher(result).matches());
			
			Pattern pattern2 = Pattern.compile(".*xlink:href=\"http://www.example.com/interpolation\" xlink:arcrole=\"http://processReference\"/>.*", Pattern.DOTALL);
			Assert.assertTrue(pattern2.matcher(result).matches());
		
								
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