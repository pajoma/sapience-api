package sapience.core.consistency;


import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import sapience.core.controllers.sesame.SesameRepositoryController;
public class TemporalRepositoryBuilder {


	private Repository repo;
	private ServletContext context;
	//private SesameDatabaseConnection conn;
	private RepositoryConnection conn;

	private String rdfFilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "sapience" + File.separator + "core" + File.separator + "example.rdf";


	public TemporalRepositoryBuilder() {
		//ApplicationCore coreApp = new ApplicationCore();
		ServletContext servletContext = null;
		//ServletContext servletContext = coreApp.getServletContext();
		try {

			repo = Repository.create(servletContext);
			//repo.initialize();
			conn = repo.getConnection();
			loadRdf(rdfFilePath);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadRdf(String filePath) {
		File file = new File(filePath);
		String baseURI = "http://purl.org/net/concepts/";
		try {
			conn.add(file, baseURI, RDFFormat.RDFXML);
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Repository getRepository() {
		return repo;
	}

}


