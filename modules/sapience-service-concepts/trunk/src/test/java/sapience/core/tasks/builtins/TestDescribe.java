package sapience.core.tasks.builtins;

import java.io.File;

import javax.servlet.ServletContext;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.google.inject.Inject;

import sapience.core.CoreRepositoryBuilder;
import sapience.core.wicket.ApplicationCore;
import sapience.core.controllers.RdfRepository;
import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.DatabaseException;
import sapience.core.pages.RootPage;


public class TestDescribe extends TestCase
{
	private Repository repo;
	private RepositoryConnection con;
	//private SailRepositoryConnection con;
	//private SesameDatabaseConnection sesameConn;
	private String baseUri = "http://purl.org/net/concepts/";
	private String rdfFilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "sapience" + File.separator + "core" + File.separator + "example.rdf";
	
	// River hash
	private String concept = "#_qwer"; 
	
	
	private void start() {
		ServletContext servletContext = null;
		try {
			this.repo = Repository.create(servletContext);
			this.con = this.repo.getConnection();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	
//	public void testExecute() {
//		start();
//		File file = new File(rdfFilePath);
//		String uri = this.baseUri.concat(concept);
//		Commit.execute(this.con, file);
//		String result = Describe.execute(this.con, uri);
//		System.out.println(result);
//	}
	
//	public void testConstructQuery() {
//		String rdf;
//		String subject1 = "Transportation";
//		String subject2 = "Hydrodynamics";
//		// River hash
//		String concept = "#_qwer";
//		String uri = baseUri + concept;
//		System.out.println(uri);
//		start();
//		try {
//			rdf = con.getRDF(uri, "application/rdf+xml");
//			System.out.println(rdf);
//		} catch (DatabaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
