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

/*
/*
 *    GeoTools - The Open Source Java GIS Tookit
 *    http://geotools.org
 *
 *    (C) 2006-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This file is hereby placed into the Public Domain. This means anyone is
 *    free to do whatever they wish with this file. Use it well and enjoy!
 */

package org.geotools.demo.xml;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.kml.KML;
import org.geotools.kml.KMLConfiguration;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xml.Encoder;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * This example illustrates how to produce KML representations
 * of geometry objects and feature collections.
 *
 * @source $URL$
 */
public class KMLExample {

	
	@Test
	public void runExample() throws Exception {
	
       polygonToKML();
	   featureCollectionToKML();
	}


    /**
     * Creates a JTS Polygon object and writes it to the console
     * encoded as KML
     */
    private void polygonToKML() throws Exception {
        GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);
        WKTReader reader = new WKTReader(gf);
        Geometry triangle = reader.read("POLYGON((0 0, 10 20, 20 0, 0 0))");

        Encoder encoder = new Encoder(new KMLConfiguration());
        encoder.setIndenting(true);
        encoder.encode(triangle, KML.Polygon, System.out);
    }

    /**
     * Writes a small feature collection (world cities) to the console
     * encoded as KML
     */
    private void featureCollectionToKML() throws Exception {
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = createSampleFeatures();
        Encoder encoder = new Encoder(new KMLConfiguration());
        encoder.setIndenting(true);
        encoder.encode(features, KML.kml, System.out );
    }

    /**
     * This method creates a small collection of world city features. Each feature has
     * location (Point), name (String) and country (String) attributes.
     *
     * If the code here doesn't make sense to you, have a look at the
     * example: <a href="http://geotools.org/examples/csv2shp.html">CSV To Shape Lab</a>
     * for an introduction to Feature, FeatureType and FeatureCollection.
     */
    private FeatureCollection<SimpleFeatureType, SimpleFeature> createSampleFeatures() {
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("cities");
        typeBuilder.add("geometry", Point.class, DefaultGeographicCRS.WGS84);
        typeBuilder.add("name", String.class);
        typeBuilder.add("country", String.class);
        SimpleFeatureType TYPE = typeBuilder.buildFeatureType();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = FeatureCollections.newCollection();

        // Rome
        featureBuilder.add(gf.createPoint(new Coordinate(12.5000, 41.8833)));
        featureBuilder.add("Rome");
        featureBuilder.add("Italy");
        features.add( featureBuilder.buildFeature("1") );

        // New York
        featureBuilder.add(gf.createPoint(new Coordinate(-74.0000, 40.7000)));
        featureBuilder.add("New York");
        featureBuilder.add("USA");
        features.add( featureBuilder.buildFeature("2") );

        // Paris
        featureBuilder.add(gf.createPoint(new Coordinate(2.3333, 48.8667)));
        featureBuilder.add("Paris");
        featureBuilder.add("France");
        features.add( featureBuilder.buildFeature("3") );

        // London
        featureBuilder.add(gf.createPoint(new Coordinate(-0.0833, 51.5000)));
        featureBuilder.add("London");
        featureBuilder.add("England");
        features.add( featureBuilder.buildFeature("4") );

        return features;
    }
}
