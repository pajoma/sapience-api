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

package sapience.features.factories;


import java.io.Serializable;
import java.util.Collection;

import org.opengis.feature.Association;
import org.opengis.feature.Attribute;
import org.opengis.feature.ComplexAttribute;
import org.opengis.feature.Feature;
import org.opengis.feature.FeatureFactory;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AssociationDescriptor;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.ComplexType;
import org.opengis.feature.type.FeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.referencing.crs.CoordinateReferenceSystem;



public class SimpleFeatureFactory implements FeatureFactory {
//
//	public Feature createFeature() {
//		return new Feature() {
//			
//		};
//	}
//
//	public Feature createFeature(Feature f) {
//		
//		Feature fnew = new Feature() { };
//		for(Serializable annot : f.listAnnotations()) {
//			fnew.addAnnotation(annot);
//		}
//		fnew.setGeometry(f.getGeometry());
//		fnew.setAssociatedFeatureCollection(f.getAssociatedFeatureCollection());
//		
//		return fnew;
//	}

	public Association createAssociation(Attribute value,
			AssociationDescriptor descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Attribute createAttribute(Object value,
			AttributeDescriptor descriptor, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public GeometryAttribute createGeometryAttribute(Object geometry,
			GeometryDescriptor descriptor, String id,
			CoordinateReferenceSystem crs) {
		// TODO Auto-generated method stub
		return null;
	}

	public ComplexAttribute createComplexAttribute(Collection<Property> value,
			AttributeDescriptor descriptor, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ComplexAttribute createComplexAttribute(Collection<Property> value,
			ComplexType type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature createFeature(Collection<Property> value,
			AttributeDescriptor descriptor, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature createFeature(Collection<Property> value, FeatureType type,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleFeature createSimpleFeature(Object[] array,
			SimpleFeatureType type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleFeature createSimpleFeautre(Object[] array,
			AttributeDescriptor decsriptor, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
