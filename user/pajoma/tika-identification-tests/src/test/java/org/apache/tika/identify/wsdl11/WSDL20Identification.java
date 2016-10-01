package org.apache.tika.identify.wsdl11;

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
import org.junit.Test;

import sapience.injectors.Injector;
import sapience.injectors.SelectorFactory;
import sapience.injectors.TestInjectorFactory;
import sapience.injectors.detectors.HeaderDetector;
import sapience.injectors.wsdl.v11.WSDLInjector1_1;
import sapience.injectors.wsdl.v20.WSDLInjector2_0;

import static junit.framework.Assert.*;


public class WSDL20Identification extends TestingIdentification {

	@Override
	public String getFileName() {
		return "TestWSDL_2_0(noRefs).wsdl";
	}

	@Override
	public void testIdentifyMimeType() throws IOException {
		// has to be buffered to support mark/reset
		BufferedInputStream bis = new BufferedInputStream(getFileURL().openStream());
		MimeType mimeType = TikaConfig.getDefaultConfig().getMimeRepository().getMimeType(bis);
		assertEquals("application/xml", mimeType.toString());

	}


	@Override
	public void testIdentifySchema() throws IOException {
		InputStream resourceAsStream = TestInjectorFactory.class.getClassLoader().getResourceAsStream("InjectorConfig.xml");
		SelectorFactory.getInstance().loadConfig(resourceAsStream);
		
		Injector injector = SelectorFactory.getInstance().getInjector(getFileURL().openStream());
		assertTrue(injector instanceof WSDLInjector2_0);
	}

	

	
}
