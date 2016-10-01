package sapience.core.database;

import junit.framework.Assert;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import sapience.core.controllers.RepositoryController;
import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.TaskExecutionException;
import sapience.core.tasks.CoreTask;
import sapience.core.tasks.builtins.Describe;

public class TestSesameDatabaseConnection {
	
	private TaskController controller = null;
	private RepositoryConnection connection;
	
	protected WicketTester wicketTester = null;


	
	@Test
	public void testConnection() throws RepositoryException {
		
		RepositoryController<Repository>  repo = new SesameRepositoryController(null);
		TaskController controller = new SesameTaskController(repo);
		
		Assert.assertNotNull(controller);
		
		connection = repo.getRepository().getConnection();
		
		// is alive
		Assert.assertTrue(connection.isOpen());
		
		// release
		connection.close();
		
		Assert.assertFalse(connection.isOpen());
	}


	public void testExecuteTask() throws TaskExecutionException {

		CoreTask task = new Describe();

		task.setParameter("title", "river");
		task.setParameter("Accept", "text/plain");
			
		controller.executeTask(task, System.out);
	
		
	}

}
