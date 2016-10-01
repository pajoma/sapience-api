package sapience.injectors.testclient.wsdl.v20;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;

public class Suite_08 extends TestLifeCycle {

	@Autowired ConfigurationFactory config;

	private String sid;

	private String notAnnotatedFile = "WSDL20/wsdl20-sawsdl-testcases/00-all-plain.wsdl";
	private String annotatedFile = "WSDL20/wsdl20-sawsdl-testcases/00-all.wsdl";
	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	

	@Override
	public void testExtract() throws IOException {

		InputStream inputStream = null;
		sid = asHEX("http://example.com/wsdl/Suite_08");
		inputStream = getFileStream(annotatedFile);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
		List<Reference> check = config.getLookup().check(sid);
		assertEquals(5, check.size());	

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;
		
		sid = asHEX("http://example.com/wsdl/Suite_08");
		inputStream = getFileStream(notAnnotatedFile);
		
		Configuration c = config.getConfiguration(inputStream);
		Injector injector = c.getInjector();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		injector.annotate(sid, inputStream, baos);

		String result = new String(baos.toByteArray());	
		Pattern pattern;
		System.out.println(result);
			
		pattern = Pattern.compile("sawsdl:modelReference=\"http://outp.example/ http://2.example\" ", Pattern.DOTALL);
		//Assert.assertTrue(pattern.matcher(result).find());
	
	}

	@Override
	public String getFileName() {
		return "";
	}

}
