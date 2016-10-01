package org.apache.tika.parser.spatial.esri.shapefile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;
import org.opengis.geometry.BoundingBox;
import org.xml.sax.ContentHandler;

public class ESRIShapefileContentHandler extends GeospatialContentHandler {


	public ESRIShapefileContentHandler(ContentHandler handler) {
		super(handler);
	}
	
	public ESRIShapefileContentHandler() {
		super(null);
	}
	

	private ShapefileDataStore store;

	private FeatureSource<SimpleFeatureType, SimpleFeature> source;

	private Set<FeatureType> ftList;	


	void setStore(ShapefileDataStore store) {
		this.store = store;
	}
	
	public ShapefileDataStore getStore() throws IOException {
		if(this.store == null) {
			throw new IOException("Internal Error - ShapefileDataStore not set");
		}
		return store;
	}
	
	
	@Override
	public Set<FeatureType> listFeatureTypes() throws IOException {
		if (ftList == null) {
			ftList = new HashSet<FeatureType>();
			ftList.add(getFeatureSource().getSchema());
		}
		
		return ftList;
		
		
	}



	public FeatureSource getFeatureSource() throws IOException {
		if(source == null) {
			this.source = getStore().getFeatureSource();
		}
		return this.source;
		
	}





	@Override
	public List<Feature> listFeatures(FeatureType type) throws IOException {
		ArrayList<Feature> res = new ArrayList<Feature>();
		
		FeatureCollection fc = getFeatureSource().getFeatures();
		while(fc.features().hasNext()) {
			res.add(fc.features().next());
		}
		
		return res;
	}

	public BoundingBox getBoundingBox() throws IOException {
		
		ReferencedEnvelope bounds = getFeatureSource().getBounds();
		
		return bounds;
//		try {
//			return getFeatureSource().getBounds().toBounds(getDefaultCoordinateReferenceSystem());
//		} catch (TransformException e) {
//			return getFeatureSource().getBounds();
//		}
	}




}
