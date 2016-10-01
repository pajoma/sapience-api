package sapience.core.consistency;

import java.util.Collection;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.helpers.StatementCollector;

import sapience.core.wicket.ApplicationCore;

public class ConsistencyCheckHandler extends StatementCollector {

	@Override
	public void startRDF() throws RDFHandlerException {
	
		
		
	}
	
	@Override
	public void endRDF() throws RDFHandlerException {
		// run namespace check procedure here

		for(Statement st :  super.getStatements()) {
			this.rewriteNamespace(st);
		}

		
	}
	
	private void rewriteNamespace(Statement st) {
		// for each statement with old base url 
		
		
		// replace namespace of subject
		
		// get object (resource)
			// if statement exists where object is subject
			// replace namespace of this object
				// go to statement
	}

	@Override
	public void handleComment(String comment) throws RDFHandlerException {
		// we transform the comments in rdf:comment 
		// check ExtendedTurtleParser in old core
		
	}

	@Override
	public void handleNamespace(String prefix, String uri)
			throws RDFHandlerException {
		// find out base url of the given document (
		
		super.handleNamespace(prefix, uri);
		
	}

	@Override
	public void handleStatement(Statement st) {
		super.handleStatement(st);
		
	}




}
