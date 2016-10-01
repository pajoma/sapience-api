package sapience.injectors.wps.identify;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.identify.TestingIdentification;
import org.apache.tika.mime.MimeType;
import org.junit.Before;
import org.junit.Test;

import sapience.injectors.Injector;
import sapience.injectors.ConfigurationFactory;
import sapience.injectors.TestWSDLInjector;
import sapience.injectors.configuration.HeaderDetector;
import sapience.injectors.wps.v040.getCapabilities.InjectorImpl;
import sapience.injectors.wsdl.v11.WSDLInjector1_1;

import static junit.framework.Assert.*;


public class TestWPS040 extends TestingIdentification {

	@Override
	public String getFileName() {
		return "wps-describeProcess-0.4.0.xml";
	}

	@Override
	public void testIdentifyMimeType() throws IOException {
		// has to be buffered to support mark/reset
		BufferedInputStream bis = new BufferedInputStream(getFileURL().openStream());
		MimeType mimeType = TikaConfig.getDefaultConfig().getMimeRepository().getMimeType(bis);
		assertEquals("application/xml", mimeType.toString());

	}
	@Before
	public void loadConfig() throws IOException {
		InputStream resourceAsStream = TestWSDLInjector.class.getClassLoader().getResourceAsStream("InjectorConfig.xml");		
		ConfigurationFactory.getInstance().loadConfig(resourceAsStream);
	}
	
	@Test
	public void testPattern() {
		String yes1 = "<wps:Capabilities xmlns:wps=\"http://www.opengeospatial.net/wps\"	xmlns:ows=\"http://www.opengeospatial.net/ows\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"0.4.0\"	xsi:schemaLocation=\"http://www.opengeospatial.net/wps ..\\wpsGetCapabilities.xsd\">";
		
		Pattern p1 = Pattern.compile(".*http://www.opengeospatial.net/wps.*", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
		Pattern p2 = Pattern.compile(".*version=\"0.4.0\".*.*", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
		Pattern p3 = Pattern.compile(".*Capabilities.*", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
		
		assertTrue(p1.matcher(yes1).matches()&&p2.matcher(yes1).matches()&&p3.matcher(yes1).matches());	
	}
	
	@Test
	public void testInjector() throws IOException {
		Injector injector = ConfigurationFactory.getInstance().getInjector(getFileURL().openStream());
		assertTrue(injector instanceof InjectorImpl);
		
	}

	
}
