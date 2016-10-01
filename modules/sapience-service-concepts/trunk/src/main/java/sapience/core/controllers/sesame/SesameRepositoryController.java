package sapience.core.controllers.sesame;

import javax.servlet.ServletContext;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.sail.memory.MemoryStore;

import sapience.core.controllers.RepositoryController;

import com.google.inject.Singleton;

/**
 * The Sesame repository type is defined in the Web.xml. The default is in memory store, but should be set to native for production use.
 * @author pajoma
 *
 */
@Singleton
public class SesameRepositoryController implements RepositoryController<Repository> {

	
	private Repository repo;


	public SesameRepositoryController(ServletContext servletContext) throws RepositoryException {
		repo = this.create(servletContext);
	}
	

	
	

	@Override
	public Repository getRepository() {
		return repo;
	}
	
	/**
	 * @param servletContext
	 * @return
	 * @throws RepositoryException
	 */
	private Repository create(ServletContext servletContext) throws RepositoryException {
		String repoAttribute = null;
		if (servletContext != null)
			repoAttribute = servletContext.getInitParameter("repository.type");
		
        if("memory".equalsIgnoreCase(repoAttribute)) 
				return createMemoryRepository();
    	else if("native".equalsIgnoreCase(repoAttribute))
				return createNativeRepository(servletContext);
    	else if("rdbms".equalsIgnoreCase(repoAttribute))
				return createRdbmsRepository(servletContext);
    	else if("remote".equalsIgnoreCase(repoAttribute))
				return createHttpRepository(servletContext);
    	else {
    		// default is memory repository
    		return  createMemoryRepository();
		}
		// support for memory
		
		

	}
	
	private  Repository createHttpRepository(
			ServletContext servletContext) throws RepositoryException {
		
			String repoURL = servletContext.getInitParameter("repository.url");
			Repository repo =  new HTTPRepository(repoURL);
			repo.initialize();
			return repo;
			
	}

	private  SailRepository createRdbmsRepository(
			ServletContext servletContext) throws RepositoryException {
		throw new RepositoryException("RDBMS Repository not implemented yet");
	}

	private  SailRepository createNativeRepository(
			ServletContext servletContext) throws RepositoryException {
		throw new RepositoryException("Native Repository not implemented yet");
	}

	private  SailRepository createMemoryRepository() throws RepositoryException {
		SailRepository sailRepository = new SailRepository(new MemoryStore());
		initializeRepository(sailRepository);
		return sailRepository;
	}
	
	
	private  void initializeRepository(SailRepository repo) throws RepositoryException {
		
		repo.initialize();

		SailRepositoryConnection conn = repo.getConnection();
		// the following should only happen if repository is empty
//		System.out.println(System.getProperty(ApplicationCore.BASEURL));
//		URIImpl service = new URIImpl(System.getProperty(ApplicationCore.BASEURL) + "service");
//		URIImpl creator = new URIImpl(System.getProperty(ApplicationCore.BASEURL) + "creator");
//
//		// where to find the DC dependency?
//		conn.add(service, DC.SUBJECT, new LiteralImpl("concept repository"));
//		conn.add(service, DC.CREATOR, creator);
//		conn.add(creator, DC.DESCRIPTION, new LiteralImpl("default added creator"));
//		conn.add(creator, DC.TITLE, new LiteralImpl("ADMIN"));
		
		conn.close();
	}



}
