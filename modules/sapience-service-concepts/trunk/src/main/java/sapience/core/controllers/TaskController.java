package sapience.core.controllers;

import java.io.OutputStream;

import org.openrdf.repository.RepositoryException;

import sapience.core.exceptions.TaskExecutionException;
import sapience.core.tasks.CoreTask;
import sapience.core.tasks.QueryTask;
import sapience.core.tasks.UpdateTask;

public interface TaskController {

	/**
	 * Constructs a query depending on the task received as a parameter, 
	 * checks the consistency of the query, executes the query and processes the results.
	 *  
	 * @param task
	 * @return
	 * @throws RepositoryException 
	 * @throws Exception 
	 * @throws IOException 
	 * @throws RepositoryException 
	 * @throws RDFParseException 
	 */
	/**
	 * Constructs a query depending on the task received as a parameter, 
	 * checks the consistency of the query, executes the query and processes the results.
	 * 
	 * @param task
	 * @param out
	 */
	public  void executeTask(UpdateTask task, OutputStream out) 
		throws TaskExecutionException;

	/**
	 * @param task
	 * @throws TaskExecutionException
	 * @throws RepositoryException 
	 */
	public  void executeTask(QueryTask task, OutputStream out)
			throws TaskExecutionException;

	
	
	/**
	 * Main entry point for executing the task. Default Implementation is based on Sesame, but other backends (and therefore other
	 * approaches to implement the queries and updates) is thinkable as well. This method delegates to the other two methods in this interface.
	 * 
	 * @param task
	 * @param out
	 * @throws TaskExecutionException
	 * @throws RepositoryException
	 */
	public  void executeTask(CoreTask task, OutputStream out)
			throws TaskExecutionException;

}