package sapience.core.tasks.builtins;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openrdf.model.Statement;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.rdfxml.RDFXMLWriter;

import sapience.core.HTTPConstants;
import sapience.core.exceptions.ConfusingQueryParameterException;
import sapience.core.exceptions.MissingQueryParameterException;
import sapience.core.exceptions.UserInputException;
import sapience.core.pages.RdfRequestSwitchPage;
import sapience.core.tasks.QueryTask;
import sapience.core.wicket.ApplicationCore;


public class Describe extends QueryTask {
	
	
	@Override
	public String constructQuery() throws UserInputException {
		String base = ((ApplicationCore) ApplicationCore.get()).getBaseURL();
		
		// get needed parameters
		String title = getParameter(QueryTask.PARAM_TITLE, true);
		String subject = getParameter(QueryTask.PARAM_SUBJECT, false);

	//	<http://purl.org/net/concepts/#_qwer>
	// 	concepts:_qwer
		
		// extract parameters from url

		// construct query for describing one single concept
		StringBuffer sb = new StringBuffer();
		sb.append("DESCRIBE ?s ?p ?o ");
		sb.append("WHERE { ?s dc:title \"").append(title).append("\"");
		
		// if sub
		
		if(subject != "") {
			// TODO: rework
			sb.append(" . ?s ?p ?o FILTER regex(str(?s),\"^");
			sb.append(base).append(subject);
			sb.append("\") ");
		}

		sb.append(" }");
//		
		
		return sb.toString();
	}

//	@Override
//	public String getName() {
//		return "describe";
//	}

	
//	public static void constructQuery(String uri) throws RepositoryException, MalformedQueryException, QueryEvaluationException, RDFHandlerException {
//		/* check if we want a whole document or just a single concept/class */
////		if (uri.endsWith("/") || uri.endsWith("#")) {
////			writePath(con, uri, writer, content_type);				
////		} else {
////			writeStatements(con, uri, writer, content_type);
////		}
//		writePath(con, uri, writer, content_type);
//	}
	
	
	/**
	 * For a the uri http://example.com/ it returns all statements where the
	 * subject starts with this uri
	 * 
	 * @param connection
	 * @param uri
	 * @param writer
	 * @param content_type
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException
	 * @throws RDFHandlerException
	 */
	protected static void writePath(RepositoryConnection con, String uri,
			Writer writer, String content_type) throws RepositoryException, MalformedQueryException,
			QueryEvaluationException, RDFHandlerException {
		String q = "DESCRIBE ?s ?p ?o WHERE { ?s ?p ?o FILTER regex(str(?s),\"^" + uri + "\") }";
		for (QueryLanguage lan : QueryLanguage.values())
			System.out.println(lan);
		GraphQuery query = null;
		//try {
			query = con.prepareGraphQuery(QueryLanguage.SPARQL, q, "");
		//} catch(UnsupportedQueryLanguageException e) {
		//	e.printStackTrace(System.out);
		//}
		//Query query = con.prepareQuery(QueryLanguage.SPARQL, q);
		/* select writer depending on content type */
		RDFHandler w = HTTPConstants.RDF_XML.equals(content_type) ? new RDFXMLWriter(writer) : new NTriplesWriter(writer);
		//query.evaluate(w);
		query.evaluate(w);
	}



	
	
//	private static String writeResult(Writer writer) {
//		return writer.toString();
//	}
	


}
