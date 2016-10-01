package sapience.core.tasks;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.openrdf.model.Statement;
import org.openrdf.query.GraphQueryResult;

import sapience.core.exceptions.MissingQueryParameterException;
import sapience.core.exceptions.UserInputException;



public abstract class QueryTask extends CoreTask {
	
	
	public static String PARAM_TITLE = "title";
	public static String PARAM_SUBJECT = "subject";
	
	/**
	 * Optional, in most cases the result can simply be returned
	 * 
	 * @param result
	 * @return
	 */
	public Collection<Statement> processResult(Collection<Statement> collection) {
		return collection;
	}
	
	/**
	 * Constructs a SPARQL query with the given parameters
	 * 
	 * @param parameters: the parameters encoded in the request URL
	 * @return  a string containing the sparql query
	 * @throws MissingQueryParameterException
	 * @throws UserInputException 
	 */
	public abstract String constructQuery() throws UserInputException;

	

}