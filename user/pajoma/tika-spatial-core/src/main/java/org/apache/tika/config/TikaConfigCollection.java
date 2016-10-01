package org.apache.tika.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;

public class TikaConfigCollection  {
	private List<TikaConfig> configurations;


	public TikaConfigCollection()  {
		// nothing
	}
	
	public TikaConfigCollection(TikaConfig config) {
		getConfigurations().add(config);
	}
	
	public void addConfiguration(TikaConfig config) {
		this.getConfigurations().add(config);
	}
	
	public List<TikaConfig> getConfigurations() {
		if (configurations == null) {
			configurations = new ArrayList<TikaConfig>();	
		}
		return configurations;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.tika.config.TikaConfig#getParsers()
	 */
	public Map<String,Parser> getParsers() {
		Map<String,Parser> res = new HashMap<String, Parser>();
		
		for(TikaConfig config : getConfigurations()) {
			res.putAll(config.getParsers());
		}
		return res;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.tika.config.TikaConfig#getParser(String mimeType)
	 */
	public Parser getParser(String mimeType) {
		Parser res = null;
		for(TikaConfig config : getConfigurations()) {
			res = config.getParser(mimeType);
		}
		return res;
	}
	
	public Parser getParser(URL url) throws IOException {
		// we have to buffer to support mark/rest
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		return getParser(bis);
	}
	
	public Parser getParser(InputStream input) throws IOException {
		String mt = getMimeRepository().getMimeType(input).getName();
		return getParser(mt);
	}
	
	public MimeTypes getMimeRepository() {
		return getConfigurations().get(0).getMimeRepository();
	}



	
}
