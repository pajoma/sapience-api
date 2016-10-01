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

package sapience.features.streams.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;


public class Cache {

	
	private String ext;



	public void setFileExtension(String ext) {
		this.ext = ext;
		
	}
	
	/**
	 * Tries to fetch a KML file from the given URL. If it points to a zipped KML, it will
	 * be extracted. Makes use of caching.
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public File fetch(URL url) throws IOException {
		
		StringBuffer pre = new StringBuffer(System.getProperty("java.io.tmpdir")); 
		if ( !(pre.toString().endsWith("/") || pre.toString().endsWith("\\")) ) pre.append(System.getProperty("file.separator"));
		pre.append("cached");
		pre.append(url.hashCode());
		
		File kml = new File(pre.toString().concat(".temp"));

		if(kml.createNewFile()) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = fetchStream(url);
				out = new FileOutputStream(kml);
				IOUtils.copy(in, out);
			} catch (IOException e) {
				throw e;
			} finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}
		return kml;
	}
	
	/**
	 * Directly returns the input stream coming from the URL (or the zipped
	 * KML). Does not cache. 
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public InputStream fetchStream(URL url) throws IOException {
		InputStream in = null;
		
		//if(getSuffix(url) == ".kmz") in = extractSource(url, "doc.kml"); 
		//else 
			in = url.openConnection().getInputStream();
		
		return in;
	}
	
	
	
	
	/**
	 * If the resource is hidden in a ZIP file, download, cache, and extract resource
	 * 
	 * @param url
	 * @return the input stream with the given source
	 * @throws IOException 
	 */
	private InputStream extractSource(URL url, String entry) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {

			File f = File.createTempFile("zippedSource", ".zip");
			out = new FileOutputStream(f);
			in = url.openStream();
			
			IOUtils.copy(in, out);
			ZipFile zip = new ZipFile(f);
			ZipEntry ze = zip.getEntry(entry);
			return zip.getInputStream(ze);
			
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		
		
		
	}
	

}
