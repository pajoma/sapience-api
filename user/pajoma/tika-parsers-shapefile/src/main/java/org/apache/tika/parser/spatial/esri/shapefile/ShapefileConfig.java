package org.apache.tika.parser.spatial.esri.shapefile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.xml.sax.SAXException;

public class ShapefileConfig extends TikaConfig {

	public static String SHAPEFILE_MIMETYPE ="application/octet-stream";
	public static String CONFIG_NAME = "tika-shapefile-config.xml";
	
	public ShapefileConfig() throws TikaException, IOException,
			SAXException {
		super(ShapefileConfig.class.getResource(CONFIG_NAME));

		
		
		// check with geotools if this is really a shapefile

//		if(new ShapefileDataStoreFactory().canProcess(file)) {
//			super.getParsers().put(SHAPEFILE_MIMETYPE, new ESRIShapefileParser(file));
//		}
		
		// we add the ShapefileParser to the registered parsers in the default config
		
		
		// TODO: is triggered for every binary type
	}
	
	

}
