/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package sapience.features.streams.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geospatial.kml.Document;
import org.geospatial.kml.KMLContainer;
import org.geospatial.kml.KMLFeature;
import org.geospatial.kml.Placemark;
import org.geospatial.kml.geometries.KMLGeometry;
import org.geospatial.kml.geometries.LineString;
import org.geospatial.kml.geometries.LinearRing;
import org.geospatial.kml.geometries.Point;
import org.geospatial.kml.metadata.Data;
import org.geospatial.kml.metadata.ExtendedData;
import org.opengis.feature.type.FeatureType;

import sapience.annotations.factory.AnnotationsFactory;



import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class KMLFeatureFactory extends FeatureFactory {

	private GeometryFactory geomFactory;
	private AnnotationsFactory annotFactory;
	private Map<KMLContainer, List<Feature>> containerCache;
	private static KMLFeatureFactory thisFactory = null;
	
	/**
	 * Singleton method to create and retrieve the factory
	 * @return
	 */
	public static KMLFeatureFactory getFactory() {
		if (thisFactory == null) {
			thisFactory = new KMLFeatureFactory();			
		}
		return thisFactory;
	}
	
	// private constructor
	private KMLFeatureFactory() {
		geomFactory = getGeometryFactory(4326);
		annotFactory = new AnnotationsFactory();
	}


	/* (non-Javadoc)
	 * @see sapience.features.factories.FeatureFactory#createFeature()
	 */
	@Override
	public Feature createFeature() {
		return new Feature(){
			
		};
	}
	
	/**
	 * Creates a new feature depending on the nature of the given KMLFeature
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public Feature createFeature(KMLFeature f) throws IOException {
		if(f instanceof Placemark) return this.createFeature((Placemark) f);
		else if(f instanceof KMLContainer) return this.createFeatureList((KMLContainer) f);
		
		// missing: Overlay, NetworkLink
		else {
			throw new IOException("Unsupported Feature: "+f.toString());
		}
	
	}
	
	/**
	 * Create the feature using a placemark. 
	 * 
	 * @param p
	 * @return
	 */
	private Feature createFeature(Placemark p)  throws IOException {
		Feature f = createFeature();
		this.renderAnnotations(f, p);
		
		if(p.getContainer() != null) {
			FeatureCollection fc = this.createFeatureList(p.getContainer());
			fc.addFeature(f);
		}
		f.setGeometry(this.renderGeometry(p.listGeometries()));
		return f;
	}
	
	
	/**
	 * Creates a new empty feature collection
	 * @param c
	 * @return
	 */
	public FeatureCollection createFeatureCollection() {
		FeatureCollection fc = new FeatureCollection();
		return fc;
	}

	
	/**
	 * Packs the given collection of containers into a new container if the given list has more then 1 container
	 * 
	 * @param collections
	 * @return
	 * @throws IOException 
	 */
	public List<Feature> createFeatureCollection(List<Document> collections) throws IOException {
		if(collections.size()==1) return this.createFeatureList(collections.get(0)); 	// this should be standard
		else 
		{
			List<Feature> result = createFeatureList();
			for(KMLContainer doc : collections) {
				result.addFeature(this.createFeatureList(doc));
			}
			return result;
			
		}
		
	}
	
	/**
	 * Creates a feature collection from the given container (but only once), an internal 
	 * cache prevents multiple collections.
	 * @param container
	 * @return
	 * @throws UnsupportedFeatureException 
	 */
	public FeatureCollection createFeatureList(KMLContainer container) throws IOException {
		// we have to check if this container has been created already
		if (containerCache == null) containerCache = new HashMap<KMLContainer, List<Feature>>();
		if(containerCache.containsKey(container)) return containerCache.get(container);

	
		// not cached, we create a new one
		List<Feature> fList = new ArrayList<Feature>();
		this.renderAnnotations(fList, container);
		
		containerCache.put(container, fList);
		
		
		// recursive
		for(KMLFeature k : container.listFeatures()) {
			fc.addFeature(createFeature(k));
		}
		
		return fc;
	}

	private void renderAnnotations(Feature f, KMLFeature k) {
		
		f.addAnnotation(annotFactory.createAddress(f,  k.getAddress()));
		f.addAnnotation(annotFactory.createName(f,  k.getName()));
		f.addAnnotation(annotFactory.createDescription(f, k.getDescription()));
		
		// extended data
		ExtendedData ed = k.getExtendedData();
		if(ed != null) {
			for(ExtendedData extended : ed.list() ) {
				if(extended instanceof Data) {
					Data d = (Data) extended;
					f.addAnnotation(annotFactory.createUntypedAnnotation(f, d.getName(), d.getValue()));
				}
				
			}
		}
		
		

	}

	


	private Geometry renderGeometry(List<? extends KMLGeometry> listGeometries) {
		for(KMLGeometry kgeo : listGeometries) {
			List<Coordinate> coords = kgeo.listCoordinates();
	
			if(kgeo instanceof Point) {
				try {
					return geomFactory.createPoint(((Point) kgeo).getCoordinate());
				} catch (Exception e) {
					return geomFactory.createPoint(new Coordinate());
				}
			}
				
			
			if(kgeo instanceof LineString) {
				try {
					Coordinate[] coordinates = coords.toArray(new Coordinate[] {});
					return geomFactory.createLineString(coordinates);	
				} catch (Exception e) {
					// create empty geometry
					return geomFactory.createLineString(new Coordinate[] {});
				}
				
			}
				
			
			if(kgeo instanceof LinearRing) {
				try {
					return geomFactory.createLinearRing(coords.toArray(new Coordinate[] {}));	
				} catch (Exception e) {
					return geomFactory.createLinearRing(new Coordinate[] {});
				}
				
			}
				
			
//			if(kgeo instanceof Polygon) 
//				return geomFactory.createP(coords.toArray(new Coordinate[] {}));
		}
		
		return null;
	}



	
	

}

