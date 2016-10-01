

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class TikaConfigWPS extends TikaConfig {
    public static final String KML_CONFIG_LOCATION = "tika-config-kml.xml";
    	
	public TikaConfigWPS(InputStream stream) throws TikaException, IOException,
			SAXException {
		super(stream);
	}
	

	public static TikaConfig getConfig()  {
	
			try {
				
				
				return new TikaConfigKML(TikaConfigKML.class.getResourceAsStream(KML_CONFIG_LOCATION));
			} catch (TikaException e) {
				throw new RuntimeException(
	                    "Unable to access KML configuration", e);
			} catch (SAXException e) {
				throw new RuntimeException(
	                    "Unable to access KML configuration", e);
			} catch (IOException e) {
				throw new RuntimeException(
	                    "Unable to access KML configuration", e);
			}

	}

}
