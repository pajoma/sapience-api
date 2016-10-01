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
import sapience.injectors.Lookup;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.injectors.testclient.TestLifeCycle;
import sapience.lookup.LookupImpl;

public class Suite_04 extends TestLifeCycle {



	private String sid;

	private String notAnnotatedFile = "wfs110/corine-capabilities/GetCapabilities-plain.xml";
	private String annotatedFile = "wfs110/corine-capabilities/GetCapabilities.xml";

	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	
	@Override
	public void testExtract() throws IOException {
		sid = asHEX("http://example.com/wfs/Suite_04");
		
		List<Reference> check2 = getConfiguration().getLookup().check(sid);
		assertEquals(0, check2.size()); 
		
		InputStream inputStream = null;
		
		inputStream = getFileStream(annotatedFile);
		Configuration c = getConfiguration().getConfiguration(inputStream);
		List<Reference> extracted = c.getExtractor().extract(sid, inputStream); 
		
		getConfiguration().getLookup().put(sid, extracted);
		List<Reference> check = getConfiguration().getLookup().check(sid);
		assertEquals(8, check.size());	

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;
		
		sid = asHEX("http://example.com/wfs/Suite_04");
		inputStream = getFileStream(notAnnotatedFile);
		
		Configuration c = getConfiguration().getConfiguration(inputStream);
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