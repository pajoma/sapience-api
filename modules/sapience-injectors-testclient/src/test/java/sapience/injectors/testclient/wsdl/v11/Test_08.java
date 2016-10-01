package sapience.injectors.testclient.wsdl.v11;

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

public class Test_08 extends TestLifeCycle {

	@Autowired ConfigurationFactory config;

	private String sid;

	private String notAnnotatedFile = "WSDL11/currency/plain.xml";
	private String annotatedFile = "WSDL11/currency/annotated.xml";
	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	

	@Override
	public void testExtract() throws IOException {

		InputStream inputStream = null;
		sid = asHEX("http://example.com/wsdl11/Suite_07");
		inputStream = getFileStream(annotatedFile);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
		List<Reference> check = config.getLookup().check(sid);
		assertEquals(6, check.size());	

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;
		
		sid = asHEX("http://example.com/wsdl11/Suite_07");
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
