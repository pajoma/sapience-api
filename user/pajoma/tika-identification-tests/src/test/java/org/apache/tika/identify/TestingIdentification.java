package org.apache.tika.identify;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;


/**
 * Testing mimetype and schema detection
 * @author pajoma
 *
 */
public abstract class TestingIdentification {
	
	
	private URL file;

	
	@Before
	public void setup () {
//		conf = new TikaConfigCollection();
//		conf.addConfiguration(TikaConfig.getDefaultConfig());
//		conf.addConfiguration(TikaConfigKML.getDefaultConfig());	
//		
		// load file
		String name = this.getFileName();
		if(name != null) {
			String path = "samples/".concat(this.getFileName());
			file = TestingIdentification.class.getClassLoader().getResource(path);
		}
		
		
	}
	
	
	@Test
	public void testIdentifyMimeType() throws IOException {
	}
	
	
	@Test
	public void testIdentifySchema() throws IOException {
		
	}
	
	public abstract String getFileName();
	
	public URL getFileURL() {
		return file;
	}
	


}
