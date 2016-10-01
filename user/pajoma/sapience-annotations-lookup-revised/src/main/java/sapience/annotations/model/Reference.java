package sapience.annotations.model;

import sapience.annotations.hibernate.entities.Entity;

/**
 * This Class represents a Reference-Object, which stores three attributes:
 * - URI (of a document)
 * - xPath (of a element in the document)
 * - URL (of the concept)
 * It is provided by the entity "Concept".
 * 
 * @author Henry
 *
 */
public class Reference implements Model {
	
	private String uri;
	
	private String xPath;
	
	private String url;
	
	/**
	 * Constructor to generate an instance of the reference object with all important attributes.
	 * @param uri
	 * @param xPath
	 * @param url
	 */
	public Reference(String uri, String xPath, String url){
		setUri(uri);
		setUrl(url);
		setXPath(xPath);
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	private void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the xPath
	 */
	public String getXPath() {
		return xPath;
	}

	/**
	 * @param path the xPath to set
	 */
	private void setXPath(String path) {
		xPath = path;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	private void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Entity toEntity(){
		Reference concept = new Reference();
		concept.setUri(getUri());
	    concept.setXPath(getXPath());
	    concept.setUrl(getUrl());
		return concept;
	}
}
