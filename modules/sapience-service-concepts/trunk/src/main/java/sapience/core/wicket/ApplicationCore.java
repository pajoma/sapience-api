package sapience.core.wicket;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.util.file.Folder;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.slf4j.LoggerFactory;

import sapience.core.ConfigurationCore;
import sapience.core.controllers.RepositoryController;
import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.pages.RootPage;
import sapience.core.tasks.BuiltinRegistry;
import sapience.core.tasks.builtins.Commit;
import sapience.core.tasks.builtins.Describe;
import sapience.core.tasks.builtins.Neighbors;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see net.q14.core.web.Start#main(String[])
 */
public class ApplicationCore extends WebApplication {
	private final static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(ApplicationCore.class);

	public final static String BASEURL = "core.baseurl";
	//public final static String BASEURL = null;
	private Folder uploadFolder = null;

	//private static final BuiltinRegistry registry = BuiltinRegistry.getInstance();

	
	private static String baseurl = null;

	private GuiceConfiguration guiceConfig; 
	

	/**
	 * Constructor
	 */
	public ApplicationCore() {

	}

	/**
	 * @return the folder for uploads
	 */
	public Folder getUploadFolder() {
		return uploadFolder;
	}

	/**
	 * @see org.apache.wicket.examples.WicketExampleApplication#init()
	 */
	@Override
	protected void init() {
		try {
			
			System.setProperty(BASEURL, getBaseURL());

			// very important to make security work!
			super.init();

			// it is important to configure Guice first!
			// check if we need a special debug injector:
			initGuice();

			initUpload();

			mountPages();
		} catch (Exception e) {
			// TODO: handle exception!!!
			e.printStackTrace();
		}


	}
	
	public static String getBaseURL() {
		if (baseurl == null) {

			baseurl = System.getProperty(BASEURL);
			
			if (baseurl == null) {
				baseurl = ApplicationCore.get().getServletContext().getInitParameter(BASEURL);
			}
			if (baseurl == null) { // defined in web.xml
				baseurl = "http://localhost:8080/core/rdf/";
			}			
		}

		return baseurl;
	}



	@Override
	public Session newSession(final Request request, final Response response) {
		Session session = super.newSession(request, response);
		guiceConfig.getInjector().injectMembers(session);
		return session;
	}

	@Override
	public Class<RootPage> getHomePage() {
		return RootPage.class;
	}

	/**
	 * Create a new WebRequest. In normal wicket pages, the http parameters are
	 * lost so we just have to create our own WebRequest implementation...
	 * 
	 * @param servletRequest
	 * @return a WebRequest object
	 */
	protected WebRequest newWebRequest(final HttpServletRequest servletRequest) {
		// we will read the http Accept header and copy it to the
		// pageparameters. Simple.
		return new SapienceServletWebRequest(servletRequest);
	}

	/**
	 * mounting strategy
	 * 	/core/rdf => creates a query task
	 *  /core/commit => creates a update task
	 *  
	 *  http://purl.org/net/concepts/rdf/concept?title=Marble&subject=geology
	 *  http://purl.org/net/concepts/rdf/concept?title=Marble&subject=geology
	 *  
	 *  http://purl.org/net/concepts/rdf/neighbors?subject=transportation
	 *  
	 *  http://purl.org/net/concepts/rdf?task=neighbors?subject=transportation
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void mountPages() throws InstantiationException, IllegalAccessException {
		mount(new RDFPathDecoder("/rdf"));	// another option 
		
		// path decoding is done in the RDFPathDecoder
		
		
//		mount(new RDFPathDecoder("/rdf/describe", Describe.class));
//		mount(new RDFPathDecoder("/rdf/neighbors", Neighbors.class));
//		//mount(new CoreUrlDecodingStrategy("/rdf/neighbors", NeighborsTask.class));
//		mount(new RDFPathDecoder("/commit", Commit.class));
	}

	
	
	private void initGuice() {
		guiceConfig = new GuiceConfiguration(this);
		addComponentInstantiationListener(guiceConfig.getComponentInstantiationListener());
	}


	private void initUpload() {
		getResourceSettings().setThrowExceptionOnMissingResource(false);

		uploadFolder = new Folder(System.getProperty("java.io.tmpdir"),"wicket-uploads");
		// Ensure folder exists
		uploadFolder.mkdirs();

	}
}
