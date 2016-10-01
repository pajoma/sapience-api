package org.apache.tika.parser.spatial.esri.shapefile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.spatial.GeospatialContentHandler;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ESRIShapefileParser implements Parser {


	public static String SHAPEFILE_MIMETYPE ="application/octet-stream";


	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {	

		if(!(handler instanceof GeospatialContentHandler)) {
			throw new TikaException("Only handler of type GeospatialContentHandler supported");
		}
		

		
		// get original url from context
		URL source = context.get(URL.class);
		
		// check if this is really a shapefile
		if(!new ShapefileDataStoreFactory().canProcess(source)) {
			throw new TikaException("No ESRI shapefile: "+source.getFile());
		}
		
		ESRIShapefileContentHandler gch = (ESRIShapefileContentHandler) handler;
		
		// init handler, which gives access to the spatial content
		ShapefileDataStore store = new ShapefileDataStore(source, true);
		gch.setStore(store);
		
		
		// set common metadata fields for 
		metadata.set(DublinCore.SOURCE, source.toString());
		metadata.set(DublinCore.TITLE, store.getInfo().getTitle());
		metadata.set(DublinCore.DESCRIPTION, store.getInfo().getDescription());
		metadata.set(DublinCore.PUBLISHER, store.getInfo().getPublisher().toString());
		metadata.set(DublinCore.SUBJECT, store.getInfo().getKeywords().toString());


	}

	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata) throws IOException, SAXException, TikaException {
		throw new TikaException("Undefined Context. Has to include the URL to original source");

	}

}
