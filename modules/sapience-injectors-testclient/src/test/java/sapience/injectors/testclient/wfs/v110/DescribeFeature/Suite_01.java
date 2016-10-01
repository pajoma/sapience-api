package sapience.injectors.testclient.wfs.v110.DescribeFeature;

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


public class Suite_01 extends TestLifeCycle {

	@Autowired
	ConfigurationFactory config;

	private String sid;

	private String notAnnotatedFile = "wfs110/wfs110-describeFeature-testcases/DescribeFeatureType-plain.xml";
	private String annotatedFile = "wfs110/wfs110-describeFeature-testcases/01-all-annotation.xml";
	
//	private String notAnnotatedFile = "wfs110/example/wfs-decribeFeatureType-swing.xml";
//	private String annotatedFile = "wfs110/example/wfs-decribeFeatureType-swing_a.xml";

	@Test
	public void test() throws IOException {
		super.testLifeCycle();
	}
	
	@Override
	public void testExtract() throws IOException {

		InputStream inputStream = null;
		sid = asHEX("http://example.com/wfs?DescribeFeature");
		inputStream = getFileStream(annotatedFile);
		Configuration c = config.getConfiguration(inputStream);
		config.getLookup().put("http://example.com/wfs?DescribeFeature",c.getExtractor().extract(sid, inputStream));
		List<Reference> check = config.getLookup().check(sid);
		for (int i = 0; i < check.size(); i++) {
			System.out.println(check.get(i));
		}
		assertEquals(3, check.size());

	}

	@Override
	public void testInject() throws IOException {
		InputStream inputStream = null;

		sid = asHEX("http://example.com/wfs?DescribeFeature");
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
