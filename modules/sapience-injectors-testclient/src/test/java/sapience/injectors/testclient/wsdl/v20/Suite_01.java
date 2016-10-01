package sapience.injectors.testclient.wsdl.v20;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;

public class Suite_01 extends TestLifeCycle {

	@Autowired ConfigurationFactory config;

	private String sid;

	private String notAnnotatedFile = "WSDL20/wsdl20-sawsdl-testcases/00-plain.wsdl";
	private String annotatedFile = "WSDL20/wsdl20-sawsdl-testcases/01-interface-annotation.wsdl";
	
	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	

	@Override
	public void testExtract() throws IOException {

		InputStream inputStream = null;
		sid = asHEX("http://example.com/wsdl/Suite_01");
		inputStream = getFileStream(annotatedFile);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put(sid, c.getExtractor().extract(sid, inputStream));
		List<Reference> check = config.getLookup().check(sid);
		assertEquals(1, check.size());	

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;
		
		sid = asHEX("http://example.com/wsdl/Suite_01");
		inputStream = getFileStream(notAnnotatedFile);
		
		Configuration c = config.getConfiguration(inputStream);
		Injector injector = c.getInjector();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		injector.annotate(sid, inputStream, baos);

		String result = new String(baos.toByteArray());	
		System.out.println(result);

		Pattern pattern;
			
		pattern = Pattern.compile("<wsdl:interface.*sawsdl:modelReference=\"http://example.org/categorization/products/electronics\".*", Pattern.DOTALL);
		Assert.assertTrue(pattern.matcher(result).find());
		
		pattern = Pattern.compile(".*wsdl:interface.*name=\"Order\".*", Pattern.DOTALL);
		Assert.assertTrue(pattern.matcher(result).find());		
	}

	@Override
	public String getFileName() {
		return "";
	}

}
