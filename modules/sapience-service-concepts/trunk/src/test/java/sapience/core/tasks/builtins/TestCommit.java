package sapience.core.tasks.builtins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import sapience.core.HTTPConstants;
import sapience.core.controllers.RepositoryController;
import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.TaskExecutionException;



public class TestCommit  {
	

	
	private RepositoryController<Repository> repo;
	private TaskController controller;
	
	@Before
	public void start() throws RepositoryException {
		repo = new SesameRepositoryController(null);
		controller = new SesameTaskController(repo);
		
	}
	
	public File getFile(String filename) {
		URL resource = this.getClass().getResource(filename);
		return new File(resource.getPath());
	}
	
	@Test
	public void testExecuteWithFile() throws TaskExecutionException {
		File file = getFile("example.rdf");
		
		Commit commitTask = new Commit();
		commitTask.setParameter(Commit.PARAM_URL, file.toURI().toString());
		commitTask.setParameter(Commit.PARAM_FORMAT, HTTPConstants.RDF_XML);
		
		controller.executeTask(commitTask, System.out);
	}
	
	public void testExecuteWithRemoteURL() {
		Commit commitTask = new Commit();
		commitTask.setParameter(Commit.PARAM_URL, "http://data-gov.tw.rpi.edu/raw/92/index.rdf");
		commitTask.setParameter(Commit.PARAM_FORMAT, HTTPConstants.RDF_XML);
		
		
	}
	public void testLoadRdfFile() {
		
	}
	
	public void testLoadRdfUrl() {
		
	}
}
	



///**
// * Testing the commit action (take one simple file, write it into the database). 
// * 
// * Open Problems: 
// *  - how to emulate the full HTTP stack in WicketTester (the 
// */
//public class TestCommit extends TestCase
//{
//	
//	
//	private WicketTester tester;
//	@Inject
//	private RdfRepository connection;
//	
//	/* (non-Javadoc)
//	 * @see junit.framework.TestCase#setUp()
//	 * 
//	 * 
//	 */
//	@Override
//	public void setUp()
//	{
//		ApplicationCore coreApp = new ApplicationCore();
//		
//		
//		// add sample ontology  
//		
//		
//	}
//
//
//	public void testCommit()
//	{
//		//start and render the test page
//		tester.startPage(RootPage.class);
//
//		//assert rendered page class
////		tester.assertPageLink(path, expectedPageClass);
//		
//		tester.assertRenderedPage(RootPage.class);
//
//		//assert rendered label component
//		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
//	}
//}
