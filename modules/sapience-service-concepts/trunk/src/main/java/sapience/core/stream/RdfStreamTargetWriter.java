/**
 * 
 */
package sapience.core.stream;

import java.io.OutputStream;

import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import sapience.core.HTTPConstants;
import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.TaskExecutionException;
import sapience.core.exceptions.UserInputException;
import sapience.core.tasks.CoreTask;

import com.google.inject.Inject;

/**
 * @author jtheuer
 * 
 */
public class RdfStreamTargetWriter extends ResourceStreamRequestTarget {
	


	/**
	 * Takes a database connection and an URI as argument and transforms the database
	 * query result into a wicket-resource stream
	 * @param connection
	 * @param uri
	 */
	public RdfStreamTargetWriter(final CoreTask task) {
		
		
		
		super(new AbstractResourceStreamWriter() {

			private static final long serialVersionUID = 5806570792679380187L;
			
			@Inject
			TaskController taskController;
			
			
			@Override
			public void write(OutputStream output) {				
				try {
					taskController.executeTask(task, output);
				}  catch (TaskExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public String getContentType() {
				try {
					return task.getParameter("Accept", true);
				} catch (UserInputException e) {
					e.printStackTrace();
					return HTTPConstants.RDF_XML;
				}	
				
				
			}

		});
	}

}
