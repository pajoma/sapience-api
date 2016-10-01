package org.apache.tika.parser.xml.kml;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.impl.ParserHandler;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;
import org.opengis.geometry.BoundingBox;

public class KMLContentHandler extends GeospatialContentHandler {
	


	private SimpleFeature container;
	private Set<FeatureType> types = null;
	private BoundingBox bbox = null;
	
	public void setContainer(SimpleFeature container) throws IOException {
		this.container = container;
		this.build(container);
	}
	

	/* (non-Javadoc)
	 * @see org.apache.tika.parser.spatial.GeospatialContentHandler#getBoundingBox()
	 */
	@Override
	public BoundingBox getBoundingBox() throws IOException {
		// computing the bounding box of all features in the kml file
		if (bbox == null) {
			bbox = new ReferencedEnvelope();
		}

		return bbox;

	}



	/* (non-Javadoc)
	 * @see org.apache.tika.parser.spatial.GeospatialContentHandler#listFeatureTypes()
	 */
	@Override
	public Set<FeatureType> listFeatureTypes() throws IOException {
		// KML serves either folders or placemarks
		if(this.types == null) {
			this.types = new HashSet<FeatureType>();

		}
		
		return this.types;
	}

	



	@Override
	public List<Feature> listFeatures(FeatureType type) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	/** helper methods 
	 * @throws IOException **/
	
	
	
	private void build(SimpleFeature feature) throws IOException {
		SimpleFeatureType featureType = feature.getFeatureType();
		listFeatureTypes().add(featureType);
		
		Object obj = feature.getAttribute("Feature");
		// does the feature contain other features
		if(obj instanceof Collection) {
			Collection featureList = (Collection) obj;
			
			for(Object obj2 : featureList) {
				SimpleFeature featureInCollection = (SimpleFeature) obj2;
				build(featureInCollection);
			}
		} else  
		{
			// everything else has to have some bounds defined
			getBoundingBox().include(feature.getBounds());
			
			// add text to string buffer
			for(Property p : feature.getProperties()) {
				Object value = p.getValue();
				if(value instanceof String) {
					getTextContent().append(value);
					getTextContent().append(' ');
				}
				
			}
			
		}
	}

	
}
