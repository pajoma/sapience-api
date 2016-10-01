package sapience.core.tasks.builtins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.wicket.PageParameters;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import sapience.core.tasks.UpdateTask;

/**
 * The describe operation is triggered of the description of one particular concept is requested
 *
 * @author pajoma
 *
 */
public class Commit extends UpdateTask {
	
	public Commit() {
		super();
	}
	
//	// TODO: move to controller
//	@Deprecated
//	public boolean execute(RepositoryConnection con, Object source) {
//		if (File.class.isInstance(source)) {
//			return loadRdfFile(con, (File) source);
//		}
//		else if (URL.class.isInstance(source)) {
//			return loadRdfUrl(con, (URL) source);
//		}
//		else {
//			return false;
//		}
//	}

	/**
	 * Loads RDF content into the concept repository from a file
	 * @param RDF file
	 * @return true or false if the operation was successful or not
	 */

//	private boolean loadRdfFile(RepositoryConnection con, File file) {
//		try {
//			con.add(file, baseURI, RDFFormat.RDFXML);
//		} catch (RDFParseException e) {
//			e.printStackTrace();
//			return false;
//		} catch (RepositoryException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//	
	
	/**
	 * Loads RDF content into the concept repository from a URL
	 * @param url
	 * @return true or false if the operation was successful or not
	 */
	@Deprecated
	private boolean loadRdfUrl(RepositoryConnection con, URL url) {
		try {
			con.add(url, url.toString(), RDFFormat.RDFXML);
		} catch (RDFParseException e) {
			e.printStackTrace();
			return false;
		} catch (RepositoryException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}



	@Override
	public String getName() {
		return "Commit";
	}

	@Override
	public InputStream getStream() throws Exception {
		GetMethod method = null; 
		
		try {
			String parameter = getParameter(PARAM_URL, false);

			// if url = null bla blab la
			// String[] parameter = getParameter(PARAM_BODY)
			
			HttpClient cl = new HttpClient();
			method = new GetMethod(parameter);
			
			// TODO: if mimetype is set add it as parameter to the task
			
			int statusCode = cl.executeMethod(method);

			
			return method.getResponseBodyAsStream();
		} catch (Exception e) {
			throw e;
		} finally  {
			if(method != null) {
				method.releaseConnection();
			}
		}

	}



}
