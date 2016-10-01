package org.apache.tika.parsers.spatial.kml;

import java.io.IOException;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.tika.TikaConfigKML;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.config.TikaConfigCollection;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.Parser;

import org.xml.sax.SAXException;



public class BuildCollection extends TestCase {

	public void testKMLConfiguration() throws TikaException, IOException, SAXException {
		TikaConfig tikaConfigKML = TikaConfigKML.getConfig();
		Map<String, Parser> parsers = tikaConfigKML.getParsers();
		
		Assert.assertEquals(2, parsers.size());
		
		Parser parser = tikaConfigKML.getParser("application/vnd.google-earth.kml+xml");
		Assert.assertNotNull(parser);
	}
	
	public void testBuildingCollection() throws TikaException, IOException, SAXException {

		
		TikaConfigCollection tcc = new TikaConfigCollection();

		tcc.addConfiguration(TikaConfig.getDefaultConfig());
		tcc.addConfiguration(TikaConfigKML.getConfig());
		
		Map<String, Parser> parsers = tcc.getParsers();
		Assert.assertEquals(92, parsers.size());
	}
}
