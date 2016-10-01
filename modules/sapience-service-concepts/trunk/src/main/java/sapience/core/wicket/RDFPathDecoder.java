package sapience.core.wicket;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.PageParameters;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.basic.URIRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;

import sapience.core.pages.RdfRequestSwitchPage;

public class RDFPathDecoder extends URIRequestTargetUrlCodingStrategy {

	

	public RDFPathDecoder(String mountPath) throws InstantiationException, IllegalAccessException {
		super(mountPath);
		
	}
	
	
	/* TODO: get parameters, check if 
	 * 
	 * /rdf/River_Biology -> describe?title=river&subject=Biology
	 * /rdf/River -> describe?title=river
	 * /rdf/River?subject=Biology -> describe?title=river&subject=Biology
	 */
	@Override
	public IRequestTarget decode(RequestParameters requestParameters) {
		PageParameters p = new PageParameters();
		
		String title = null;
		String subject = null;
		String task = "describe";
		
		String path = requestParameters.getPath();
		//String queryString = requestParameters.getQueryString();
		path = path.replaceAll("rdf/", "");
		String[] terms = path.split("_");
		
		// /rdf/River or /rdf/[task]
		if (terms.length == 1) {
			if (path.startsWith("describe")) {
				// /rdf/describe?title=...
				p.add(RdfRequestSwitchPage.URL, requestParameters.getQueryString());
			}
			else {
				// /rdf/River
				requestParameters.setQueryString("title=" + terms[0]);
			}
		}
		else {
			// /rdf/River_Biology
			title = terms[0];
			subject = terms[1];
			requestParameters.setQueryString("title=" + title + "&subject=" + subject);
		}
			
		System.out.println("queryString: " + requestParameters.getQueryString());
		p.add(RdfRequestSwitchPage.URL, requestParameters.getQueryString());
		p.add(RdfRequestSwitchPage.TASK, task);
		
		return new BookmarkablePageRequestTarget(requestParameters.getPageMapName(), RdfRequestSwitchPage.class, p);
	}
	
	
	
//	protected String getURI(RequestParameters requestParameters) {
//		String title = null;
//		String subject = null;
//		PageParameters params = decodeParameters(requestParameters);
//		if (params.containsKey("title")) {
//			title = params.getString("title");
//			if (params.containsKey("subject"))
//				subject = params.getString("subject");
//		}
//		
//		// check for keywords of registered tasks ?
//			// yes, add task to the parameter
//			// no
//				// if uri contains _ -> split into title and subject
//				// no -> assume its the tile
//				// -> set parameter to describe task
//		
//		return "";
//	}

}
