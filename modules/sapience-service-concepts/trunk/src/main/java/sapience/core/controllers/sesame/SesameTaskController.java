package sapience.core.controllers.sesame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.openrdf.model.Statement;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.sail.memory.MemoryStore;

import sapience.core.HTTPConstants;
import sapience.core.controllers.RepositoryController;
import sapience.core.controllers.TaskController;
import sapience.core.exceptions.TaskExecutionException;
import sapience.core.exceptions.UserInputException;
import sapience.core.tasks.CoreTask;
import sapience.core.tasks.QueryTask;
import sapience.core.tasks.UpdateTask;
import sapience.core.wicket.ApplicationCore;

import com.google.inject.Singleton;

@Singleton
public class SesameTaskController implements TaskController{

	// injection does not work here, since this class itself is also injected (dead-lock?)
	private RepositoryController<Repository> repoControl;
	
	private Logger logger;

	public SesameTaskController(RepositoryController<Repository> repoController) {
		this.repoControl = repoController;
	}


	
	/* (non-Javadoc)
	 * @see sapience.core.controllers.sesame.TaskController#executeTask(sapience.core.tasks.UpdateTask, java.io.OutputStream)
	 */

	
	public void executeTask(UpdateTask task, OutputStream out) throws TaskExecutionException {
	
		RepositoryConnection temp_connection = null; 
		
		try {
			
			InputStream source = task.getStream();
			// load source into temporary repository
			Repository temp_repository = new SailRepository(new MemoryStore());
			temp_repository.initialize();
			temp_connection = temp_repository.getConnection();
			temp_connection.add(source, ApplicationCore.getBaseURL(), RDFFormat.valueOf(task.getParameter(UpdateTask.PARAM_FORMAT, true)));
			
			// run query on repository, and let ConsistencyCheckHandler process the link checks
			GraphQuery q = temp_connection.prepareGraphQuery(QueryLanguage.SPARQL, "SELECT * FROM {?s ?p ?o}");
			q.evaluate(new N3Writer(System.out));
			
			//
			
		} catch (RepositoryException e) {
			throw new TaskExecutionException(e);
		}  catch (RDFParseException e) {
			throw new TaskExecutionException(e);
		} catch (IOException e) {
			throw new TaskExecutionException(e);
		} catch (UserInputException e) {
			throw new TaskExecutionException(e);
		} catch (MalformedQueryException e) {
			throw new TaskExecutionException(e);
		} catch (Exception e) {
			throw new TaskExecutionException(e);
		} finally {
			if(temp_connection != null) {
				try {
					temp_connection.close();
				} catch (RepositoryException e) {
					throw new RuntimeException(e);
				}
			}
		}
	
		
		
	}

	/* (non-Javadoc)
	 * @see sapience.core.controllers.sesame.TaskController#executeTask(sapience.core.tasks.QueryTask, java.io.OutputStream)
	 */
	public void executeTask(QueryTask task, OutputStream out) throws TaskExecutionException {
		RepositoryConnection connection = null;
		try {
			String query = task.constructQuery();
			System.out.println(query);
		
		
//		connection = repoControl.getRepository().getConnection();
//		List<String> requestedContentTypes = task.getParameters(HTTPConstants.ACCEPT, true);
//
//		StatementCollector collector = new StatementCollector();
//		
//
//		
//		connection.prepareGraphQuery(QueryLanguage.SPARQL, query).evaluate(collector);
//	
//		// processing results
//		Collection<Statement> processedStatements = task.processResult(collector.getStatements());
//	
//		RDFHandler output_handler = requestedContentTypes.contains(HTTPConstants.RDF_XML) ? 
//			new RDFXMLWriter(out) : new NTriplesWriter(out);
//		
//		// write results to outputstream
//		output_handler.startRDF();
//		for(Statement st : processedStatements) output_handler.handleStatement(st);
//		output_handler.endRDF();
		
		
		} catch (UserInputException e) {
			throw new TaskExecutionException(e);
//		} catch (RepositoryException e) {
//			throw new TaskExecutionException(e);
//		} catch (QueryEvaluationException e) {
//			throw new TaskExecutionException(e);
//		} catch (RDFHandlerException e) {
//			throw new TaskExecutionException(e);
//		} catch (MalformedQueryException e) {
//			throw new TaskExecutionException(e);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (RepositoryException e) {
					throw new RuntimeException(e);
				}
			}

		}
	}
	
	
	


	/* (non-Javadoc)
	 * @see sapience.core.controllers.sesame.TaskController#executeTask(sapience.core.tasks.CoreTask, java.io.OutputStream)
	 */
	public void executeTask(CoreTask task, OutputStream out) throws TaskExecutionException  {
		if(task instanceof QueryTask) executeTask((QueryTask) task, out); 
		else if(task instanceof UpdateTask) executeTask((UpdateTask) task, out);
		else {
			throw new TaskExecutionException("Unknown Task");
			
		}
	}
	
	
	
	
	
	
	
	
	

	/* (non-Javadoc)
	 * @see sapience.service.concepts.database.DatabaseConnection#getRDF(java.lang.String, java.lang.String)
	 * 
	 * rewrite to get all necessary parameters
	 */
//	@Override
//	public String getRDF(String uri, String content_type) throws DatabaseException {
//		StringWriter s = new StringWriter();
//		writeRDF(uri, s, content_type);
//		return s.toString();
//	}
//

//	public void writeRDF(String uri, Writer writer, String content_type) throws DatabaseException {
//		RepositoryConnection conn = null;
//		try {
//			conn = repository.getConnection();
//
//			/* check if we want a whole document or just a single concept/class */
//			if (uri.endsWith("/") || uri.endsWith("#")) {
//				writePath(conn, uri, writer, content_type);				
//			} else {
//				writeStatements(conn, uri, writer, content_type);
//			}
//
//		} catch (Exception e) {
//			throw new DatabaseException(e);
//		} finally {
//			try {
//				conn.close();
//			} catch (Exception e) {
//				throw new DatabaseException(e);
//			}
//		}
//	}
//
//	protected void writeStatements(RepositoryConnection connection,
//			String subject, Writer writer, String content_type) throws RepositoryException,
//			RDFHandlerException {
//		
//		/* select writer depending on desired content type */
//		RDFHandler w = HTTPConstants.RDF_XML.equals(content_type) ? new RDFXMLWriter(writer) : new NTriplesWriter(writer);
//		
//		connection.exportStatements(new URIImpl(subject), null, null, true,	w);
//	}
////
//	/**
//	 * For a the uri http://example.com/ it returns all statements where the
//	 * subject starts with this uri
//	 * 
//	 * @param connection
//	 * @param uri
//	 * @param writer
//	 * @param content_type
//	 * @throws RepositoryException
//	 * @throws MalformedQueryException
//	 * @throws QueryEvaluationException
//	 * @throws RDFHandlerException
//	 */
//	protected void writePath(RepositoryConnection connection, String uri,
//			Writer writer, String content_type) throws RepositoryException, MalformedQueryException,
//			QueryEvaluationException, RDFHandlerException {
//		final String q = "DESCRIBE ?s ?p ?o WHERE { ?s ?p ?o FILTER regex(str(?s),\"^"
//				+ uri + "\") }";
//		GraphQuery query = connection
//				.prepareGraphQuery(QueryLanguage.SPARQL, q);
//		
//		/* select writer depending on content type */
//		RDFHandler w = RDF_XML.equals(content_type) ? new RDFXMLWriter(writer) : new NTriplesWriter(writer);
//		
//		query.evaluate(w);
//	}
//	
	


}
