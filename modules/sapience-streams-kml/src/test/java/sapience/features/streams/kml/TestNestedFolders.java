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

import org.geospatial.kml.Document;
import org.geospatial.kml.Folder;
import org.geospatial.kml.KML;
import org.geospatial.kml.KMLContainer;
import org.geospatial.kml.KMLFeature;
import org.geospatial.kml.Placemark;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import sapience.features.FeatureCollection;
import sapience.features.streams.Streams;

public class TestNestedFolders {

	String file = "samples/NestedFolders.kml";
	private XStream xstream;
	
	@Before
	public void prepareParser() {
		QNameMap qnameMap = new QNameMap();
		qnameMap.setDefaultNamespace("http://www.example.com/");
		xstream = new XStream(new StaxDriver(qnameMap));
		this.configure();
		

	}
	
	private void configure() {
		xstream.processAnnotations(KML.class);
		
		xstream.processAnnotations(KMLContainer.class);
		xstream.processAnnotations(Document.class);
		xstream.processAnnotations(Folder.class);
		xstream.processAnnotations(KMLFeature.class);
		xstream.processAnnotations(Placemark.class);
		
	}

	@Test
	public void parse() throws IOException {
		InputStream is = this.getClass().getResourceAsStream(file);
		
		KML doc = (KML) xstream.fromXML(is);
		
		System.out.println(xstream.toXML(doc));
	}
}
