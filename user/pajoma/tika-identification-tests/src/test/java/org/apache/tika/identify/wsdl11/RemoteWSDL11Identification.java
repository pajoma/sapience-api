package org.apache.tika.identify.wsdl11;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.identify.TestingIdentification;
import org.apache.tika.mime.MimeType;
import static junit.framework.Assert.*;


public class RemoteWSDL11Identification extends TestingIdentification {

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public void testIdentifyMimeType() throws IOException {
		URL url = new URL("http://swing.brgm.fr:8080/axis/services/INSEE?wsdl");
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		MimeType mimeType = TikaConfig.getDefaultConfig().getMimeRepository().getMimeType(bis);
		assertEquals("application/xml", mimeType.toString());
		// should actually be application/wsdl+xml
	}

	@Override
	public void testIdentifySchema() {
		// TODO Auto-generated method stub
		

	}
	


}
