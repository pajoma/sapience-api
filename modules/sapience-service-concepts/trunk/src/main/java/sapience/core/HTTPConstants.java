package sapience.core;

/**
 * @author jtheuer
 *
 * Global http header constants
 */
public interface HTTPConstants {

	/** The PageParameters key that holds the http accept header */
	public static final String ACCEPT = "Accept";
	
	/** The accept header type for html */
	public static final String TEXT_HTML = "text/html";
	
	/** The accept header type for rdf */
	public static final String RDF = "application/rdf"; 
		
	/** The accept header for plain text */
	public static final String PLAIN = "text/plain";

	/** The accept header type for rdf */
	public static final String RDF_N3 = "application/rdf+n3";

	/** The accept header type for rdf */
	public static final String RDF_XML = "application/rdf+xml";
	
}
