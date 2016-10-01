package sapience.injectors.testclient.wfs.v110.GetCapabilities;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;

public class Suite_02 extends TestLifeCycle {

	@Autowired ConfigurationFactory config;

	private String sid;

	private String notAnnotatedFile = "wfs110/wfs110-capabilities-testcases/GetCapabilities-plain.xml";
	private String annotatedFile = "wfs110/wfs110-capabilities-testcases/02-featureType-annotation.xml";

	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	
	@Override
	public void testExtract() throws IOException {

		InputStream inputStream = null;
		sid = asHEX("http://example.com/wfs/Suite_02");
		inputStream = getFileStream(annotatedFile);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
		List<Reference> check = config.getLookup().check(sid);
		assertEquals(2, check.size());	

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;
		
		sid = asHEX("http://example.com/wfs/Suite_02");
		inputStream = getFileStream(notAnnotatedFile);
		
		Configuration c = config.getConfiguration(inputStream);
		Injector injector = c.getInjector();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		injector.annotate(sid, inputStream, baos);

		String result = new String(baos.toByteArray());		
		
		System.out.println(result);

	}

	@Override
	public String getFileName() {
		return "";
	}

}