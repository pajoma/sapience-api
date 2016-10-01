package org.apache.tika.parser.spatial;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Feature;
import org.opengis.feature.type.FeatureType;
import org.opengis.geometry.BoundingBox;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This handler is never invoked
 * @author pajoma
 *
 */
public abstract class GeospatialContentHandler extends BodyContentHandler {
	public StringBuilder text_content = null;
	
	public CoordinateReferenceSystem getDefaultCoordinateReferenceSystem() {
		return new DefaultGeographicCRS(DefaultGeographicCRS.WGS84);
	}
	
	public abstract Set<FeatureType> listFeatureTypes() throws IOException;

	public abstract List<Feature> listFeatures(FeatureType type) throws IOException;
	
	public abstract BoundingBox getBoundingBox() throws IOException;

	/**
	 * The text content of all nodes in the KML document
	 * @return
	 * @throws IOException 
	 */
	public StringBuilder getTextContent() throws IOException {
		if (text_content == null) {
			text_content = new StringBuilder();
		}
	
		return text_content;
	}
	 
	
}
