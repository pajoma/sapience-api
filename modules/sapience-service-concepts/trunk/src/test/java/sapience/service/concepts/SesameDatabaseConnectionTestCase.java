package sapience.service.concepts;

import javax.servlet.ServletContext;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.DatabaseException;
import junit.framework.TestCase;

public class SesameDatabaseConnectionTestCase extends TestCase {
	private String sesameServer = "http://localhost:8080/openrdf-sesame";
	private String repositoryId = "testContextRepo";
	private String baseUri = "http://swing.uni-muenster.de/core/";
	
	private Repository repo;
	private RepositoryConnection repoConnection;
	private SesameTaskController sesameConnection;
	
	private void connect() {
		repo = new HTTPRepository(sesameServer, repositoryId);
		try {
			repo.initialize();
			sesameConnection = new SesameTaskController(repo);
			//repoConnection = (SesameDatabaseConnection) repo.getConnection();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void testGetRDF() {
		String rdf;
		String subject1 = "Hydrology";
		String subject2 = "Swing";
		String concept = "River";
		String query = baseUri + subject1 + "/" + concept;
		connect();
		try {
			rdf = sesameConnection.getRDF(query, "application/rdf+xml");
			System.out.println(rdf);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void testWriteRDF() {
//		fail("Not yet implemented");
//	}

}
