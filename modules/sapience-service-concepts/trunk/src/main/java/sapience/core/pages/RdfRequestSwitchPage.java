package sapience.core.pages;

import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;

import sapience.core.ConfigurationCore;
import sapience.core.HTTPConstants;
import sapience.core.beans.RequestParameter;
import sapience.core.exceptions.MissingQueryParameterException;
import sapience.core.exceptions.UserInputException;
import sapience.core.stream.RdfStreamTargetWriter;
import sapience.core.tasks.BuiltinRegistry;
import sapience.core.tasks.CoreTask;
import sapience.core.tasks.builtins.Describe;
import sapience.core.wicket.ApplicationCore;

/**
 * @author jtheuer
 * 
 *         General {@link Page} that checks for the accept header in the request
 *         and then decides if we deliver plain RDF or a HTML page
 */
public class RdfRequestSwitchPage extends Page {

	/** The Base url of the service */
	private static String BASE_URL = System.getProperty(ApplicationCore.BASEURL);
	
	/** the PageParameters key that holds the relative url of the concept */
	public static final String URL = "request_url";
	
	public static final String TASK = "request_task";
	
	/** This shouldn't be here */
	//ConfigurationCore conf;
	private static final BuiltinRegistry registry = BuiltinRegistry.getInstance();

	private CoreTask task;

	
	public RdfRequestSwitchPage(PageParameters parameters) throws UserInputException {
		super(parameters);
		
		/* redirect result */
		Map<String, String[]> parametersMap = getRequest().getParameterMap();
		//System.out.println("request_task: " + parametersMap.get("request_task")[0]);
		//System.out.println("request_url: " + parametersMap.get("request_url")[0]);
		redirect(parametersMap, parameters);
	}

	
	/**
	 * decides where the request shall be redirected to: Either html or rdf download
	 * @param parameterMap
	 * @param page_parameters
	 * @throws MissingQueryParameterException 
	 */
	// RequestParameter , String[]
	private void redirect(Map<String, String[]> parameterMap, PageParameters parameters) throws UserInputException {
		System.out.println("registry map keys: " + registry.getMap().keySet().toString());
		// NullPointerException!! There are no keys in the map, so it returns always null...
		task = registry.getTask((String) parameters.get(TASK)); 
		if (task != null)
			task.setParameters(parameterMap);
		else
			throw new UserInputException();

		String value = parameterMap.get(HTTPConstants.ACCEPT)[0];
//		if(value == null) value = HTTPConstants.PLAIN;
//		
//		task.setParameter(URL, RdfRequestSwitchPage.getURIfromParameters(parameters));
//		
//		/* decide which page to use depending on the accept header */
//		if( (value.equalsIgnoreCase(HTTPConstants.PLAIN) || value.equalsIgnoreCase(HTTPConstants.TEXT_HTML))) {
//			setResponsePage(new HtmlPage(parameters, task));
//		} else  if (value.startsWith(HTTPConstants.RDF)) {
//			getRequestCycle().setRequestTarget(new RdfStreamTargetWriter(task));	
//		}
//		
//	
//
//
//
	}

	/**
	 * This is a helper method used by the ResponsePages
	 * 
	 * @param parameters
	 *            a {@link PageParameters} instance that holds the URL
	 * @return the full qualified url
	 */
	public static String getURIfromParameters(PageParameters parameters) {
		return BASE_URL + parameters.getString(URL);
	}
	
	
//	/**
//	 * Load the available tasks in the registry
//	 */
//	private void buildTasks() {
//		/** This shouldn't be here */
//		conf = new ConfigurationCore();
//		registry = BuiltinRegistry.getInstance();
//		
//		// add them to registry
//		for (Iterator it = conf.getTasks().iterator(); it.hasNext(); ) {
////			if(taskCls != null) {
//			try {
//				registry.addTask((Class<? extends CoreTask>) it.next().getClass());
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * Checks if the ?all parameter for recursive searches is in the PageParameters
	 * @param parameters
	 * @return
	 */
	@Deprecated
	public static boolean isSetAll(PageParameters parameters) {
		return parameters.containsKey("all");
	}

	/**
	 * Checks if the ?neighbor parameter is in the PageParameters
	 * @param parameters
	 * @return
	 */
	@Deprecated
	public static boolean isSetNeighbor(PageParameters parameters) {
		return parameters.containsKey("neighbor");
	}

	

}
