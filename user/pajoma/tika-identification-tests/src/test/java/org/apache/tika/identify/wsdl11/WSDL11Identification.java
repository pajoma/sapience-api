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

import sapience.injectors.detectors.HeaderDetector;

import static junit.framework.Assert.*;


public class WSDL11Identification extends TestingIdentification {

	@Override
	public String getFileName() {
		return "TestWSDL_1_1(noRefs).wsdl";
	}

	@Override
	public void testIdentifyMimeType() throws IOException {
		// has to be buffered to support mark/reset
		BufferedInputStream bis = new BufferedInputStream(getFileURL().openStream());
		MimeType mimeType = TikaConfig.getDefaultConfig().getMimeRepository().getMimeType(bis);
		assertEquals("application/xml", mimeType.toString());

	}

	@Test
	public void testPattern() {
		String yes1 = "wsdl:definitions targetNamespace=\"http://www.opengis.net/wfs/requests\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"";
		String yes2 = "wsdl:definitions targetNamespace=\"http://www.opengis.net/wfs/requests\" xmlns=\"http://schemas.xmlsoap.org/wsdl/\"";
		String no1 = "wsdl:description targetNamespace=\"http://www.opengis.net/wfs/requests\" xmlns:wsdl=\"http://www.w3.org/ns/wsdl\"";
		
		String yes3 = "<wsdl:definitions targetNamespace=\"http://www.opengis.net/wfs/requests\"" + '\n' + 
				"xmlns:wfs=\"http://www.opengis.net/wfs\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"" +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";
		
		Pattern root = Pattern.compile(".*xmlns.*http://schemas.xmlsoap.org/wsdl.*", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
		assertTrue(root.matcher(yes1).matches());
		assertTrue(root.matcher(yes2).matches());
		assertFalse(root.matcher(no1).matches());
		assertTrue(root.matcher(yes3).matches());
		
		
	}
	
	@Override
	public void testIdentifySchema() throws IOException {
		// read the header definition
		BufferedInputStream stream = new BufferedInputStream(getFileURL().openStream());
		
        if (stream == null) {
            throw new IllegalArgumentException("InputStream is missing");
        }
        Pattern root = Pattern.compile(".*xmlns.*=http://schemas.xmlsoap.org/wsdl.*");
        String header = new HeaderDetector().readXMLHeader(stream);
        
        boolean matches = root.matcher(header).matches();
        // if this string includes "http://schemas.xmlsoap.org/wsdl/" 
        assertTrue(matches);
        
		// read every byte
		
	}

	
}
