package sapience.core.pages;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import sapience.core.controllers.TaskController;
import sapience.core.exceptions.TaskExecutionException;
import sapience.core.tasks.CoreTask;

import com.google.inject.Inject;

/**
 * @author jtheuer,pajoma
 *
 * Returns the html representation of html
 */
public class HtmlPage extends WebPage {

	@Inject
	TaskController taskController;

	public HtmlPage(PageParameters parameters, CoreTask task) {
		//super(parameters);
		
		String uri;
		try {
			uri = RdfRequestSwitchPage.getURIfromParameters(parameters);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			taskController.executeTask(task, output);

			
			
			// TODO: extract the other parameters, including version, context
			
			// get the RDF stream
			add(new Label("rdf-content", output.toString()));
		} catch (TaskExecutionException e) {
			//TODO nicer handling
			throw new RuntimeException(e);
		} 

	}

}
