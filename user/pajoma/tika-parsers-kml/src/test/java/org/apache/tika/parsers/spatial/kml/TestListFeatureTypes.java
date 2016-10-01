package org.apache.tika.parsers.spatial.kml;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.tika.parser.xml.kml.KMLContentHandler;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.Parser;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.FeatureType;
import org.opengis.geometry.BoundingBox;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestListFeatureTypes extends TestCase {

	String name = "easy.kml";
	private Parser parser;
	private SimpleFeature feature;
	private BoundingBox box;
	
	@Override
	public void setUp() throws Exception {
		 parser = new Parser(new KMLConfiguration());
	     feature = (SimpleFeature) parser.parse(getClass().getResourceAsStream(name));
	}
	
	public void testComputeBoundingBox() throws IndexOutOfBoundsException, IOException {
		KMLContentHandler kch = new KMLContentHandler();
		kch.setContainer(feature);
		
		Set<FeatureType> listFeatureTypes = kch.listFeatureTypes();
	
		
		
		Assert.assertNotNull(listFeatureTypes);
		Assert.assertEquals(3, listFeatureTypes.size());

	}
	
	
}
