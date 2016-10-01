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

package sapience.features.streams.kml;

 import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.geospatial.kml.Document;
import org.geospatial.kml.Folder;
import org.geospatial.kml.KML;
import org.geospatial.kml.KMLContainer;
import org.geospatial.kml.KMLFeature;
import org.geospatial.kml.Placemark;
import org.opengis.feature.type.FeatureType;

import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;
import sapience.features.streams.factories.KMLFactory;
import sapience.features.streams.factories.KMLFeatureFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;



public class KMLStream extends Streams {

	private KML start(InputStream in) throws IOException {

		
		KML kml = null;
		try {
			QNameMap qnameMap = new QNameMap();
			qnameMap.setDefaultNamespace("http://www.opengis.net/kml/2.2");
			XStream xstream = new XStream(new StaxDriver(qnameMap));
			new XStreamsConfiguration().configure(xstream);
			kml = (KML) xstream.fromXML(in);
			
			// build relations between features and featurecollections
	
			this.buildKMLAssociations(kml.getDocument());
	
			
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return kml;

	}

	/**
	 * Recursive method to build the associations between KML features and KML containers
	 * @param container
	 */
	private void buildKMLAssociations(KMLContainer container) {
		
			for (KMLFeature f : container.listFeatures()) {
				if(f instanceof Placemark) f.setContainer(container);
				else if(f instanceof Folder) {
					f.setContainer(container);
					this.buildKMLAssociations((Folder) f);
				}
			}
	}
	

	
	
		


	@Override
	public List<FeatureType> read(InputStream in) throws IOException {
		KML kml = this.start(in);
		KMLFeatureFactory factory = KMLFeatureFactory.getFactory();
		return factory.createFeatureCollection(kml.getDocument());


	}

	public void write(FeatureCollection collection, OutputStream out) throws IOException {
		QNameMap qnameMap = new QNameMap();
		qnameMap.setDefaultNamespace("http://www.opengis.net/kml/2.2");
		XStream xstream = new XStream(new StaxDriver(qnameMap));
		
		new XStreamsConfiguration().configure(xstream);
		
		KMLFactory factory = KMLFactory.getFactory();
		
		xstream.toXML(factory.buildKML(collection), out);


	}
	
	



	
}
