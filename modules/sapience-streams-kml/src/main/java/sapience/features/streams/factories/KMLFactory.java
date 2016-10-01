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
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.geospatial.kml.Document;
import org.geospatial.kml.Folder;
import org.geospatial.kml.KML;
import org.geospatial.kml.KMLContainer;
import org.geospatial.kml.KMLFeature;
import org.geospatial.kml.Placemark;
import org.geospatial.kml.geometries.InnerBoundary;
import org.geospatial.kml.geometries.LineString;
import org.geospatial.kml.geometries.LinearRing;
import org.geospatial.kml.geometries.OuterBoundary;
import org.geospatial.kml.geometries.Point;
import org.geospatial.kml.geometries.Polygon;
import org.geospatial.kml.metadata.Data;
import org.geospatial.kml.metadata.ExtendedData;

import sapience.annotations.model.Description;
import sapience.annotations.model.KeyValueProperty;
import sapience.annotations.model.Name;
import sapience.annotations.model.contact.Address;
import sapience.features.Feature;
import sapience.features.FeatureCollection;
import sapience.features.factories.FeatureFactory;


import com.vividsolutions.jts.geom.Geometry;

/**
 * TODO: not finished
 * 
 * Creates a {@link KML} object from a {@link FeatureCollection}
 * 
 * @author pajoma
 *
 */
public class KMLFactory {

	
	private List<Feature> features;
	private Set<FeatureCollection> containers;
	private KML kml;
	private static KMLFactory thisFactory;
	
	/**
	 * Singleton method to create and retrieve the factory
	 * @return
	 */
	public static KMLFactory getFactory() {
		if (thisFactory == null) {
			thisFactory = new KMLFactory();			
		}
		return thisFactory;
	}
	
	// private constructor
	private KMLFactory() {
	}
	


	/**
	 * Creates a KML Model using the given Feature Collection. We always assume only one document exists
	 * 
	 * @param coll
	 * @return
	 * @throws IOException 
	 */
	public KML buildKML(FeatureCollection coll) throws IOException {
		kml = new KML();
		kml.setFeature((Document) populateContainer(new Document(), coll));
		
		return kml;
	}
	
	@Deprecated
	private void loadContainer(Feature f) {
		FeatureCollection afc = f.getAssociatedFeatureCollection();
		if(! containers.contains(afc)) 
			containers.add(afc);
		
		if(afc.getAssociatedFeatureCollection() != null) 
			this.loadContainer(afc);
	}
	@Deprecated
	private void buildContainer() throws IOException {
		
		for(FeatureCollection fc : this.containers) {
			if(fc.getAssociatedFeatureCollection() == null) {
				Document doc = (Document) populateContainer(new Document(), fc);
				kml.setFeature(doc);
				
				// now we have to find all containers which have this document as container
				for(FeatureCollection fc2 : this.containers) {
					if(fc2.getAssociatedFeatureCollection() == fc) {
						doc.addFeature(buildKMLFeature(fc2));
					}
				}
			}				
		}
	}
	
	/**
	 * Recursively builds the container and all features in this container
	 * 
	 * @param fc
	 * @return
	 * @throws IOException
	 */
	private KMLContainer populateContainer(KMLContainer container, FeatureCollection fc) throws IOException {
		this.renderAnnotations(fc,container);
		for(Feature f : fc.listFeatures()) {
			container.addFeature(this.buildKMLFeature(f));
		}	
		return container;
	}
	


	
	private KMLFeature buildKMLFeature(Feature f) throws IOException {
		if(f instanceof FeatureCollection) return populateContainer(new Folder(), (FeatureCollection) f);
		else {
			Placemark p = new Placemark();
			this.renderAnnotations(f, p);
			this.renderGeometries(f,p);
			return p;
		}
	}



	private void renderGeometries(Feature f, Placemark p) {
		Geometry g = f.getGeometry();
		
		if(g instanceof com.vividsolutions.jts.geom.Point) 
			p.addGeometry(new Point(g.getCoordinate()));
		else if(g instanceof com.vividsolutions.jts.geom.LineString) 
			p.addGeometry(new LineString(g.getCoordinates()));
		else if(g instanceof com.vividsolutions.jts.geom.LinearRing) 
			p.addGeometry(new LinearRing(g.getCoordinates()));
		else if(g instanceof com.vividsolutions.jts.geom.Polygon) {
			com.vividsolutions.jts.geom.Polygon jtsPolygon = (com.vividsolutions.jts.geom.Polygon) g;
			Polygon pg = new Polygon();
			pg.setOuterBoundary(new OuterBoundary(new LinearRing(jtsPolygon.getExteriorRing().getCoordinates())));
			for (int i = 0; i < jtsPolygon.getNumInteriorRing(); i++) {
				pg.addInnerBoundary(new InnerBoundary(new LinearRing(jtsPolygon.getInteriorRingN(i).getCoordinates())));
				
			}
			p.addGeometry(pg);
		}
			
		
	}


	
	private void renderAnnotations(Feature f, KMLFeature k) throws IOException {
		for(Serializable annot : f.listAnnotations()) {
			if(annot instanceof Name) k.setName(annot.toString());
			else if(annot instanceof Description) k.setDescription(annot.toString());
			else if(annot instanceof Address) k.setAddress(annot.toString());
			else if(annot instanceof KeyValueProperty) {
				k.getExtendedData().add(this.buildExtendedData((KeyValueProperty) annot));
			} else {
				throw new IOException(annot.toString());
			}
		}
	}

	public ExtendedData buildExtendedData(KeyValueProperty annot) {		
		return new Data(annot.getKey(), annot.getValue().toString());
	}

}
