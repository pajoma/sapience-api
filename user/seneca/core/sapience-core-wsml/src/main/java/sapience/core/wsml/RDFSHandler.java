package sapience.core.wsml;

import java.net.URISyntaxException;
import java.util.Iterator;

import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

import sapience.core.graph.GraphEventHandler;
import sapience.core.graph.impl.CoreNodeImpl;

public class RDFSHandler implements RDFHandler {

	private GraphEventHandler graph;
	public RDFSHandler(GraphEventHandler graph)
	{
		this.graph = graph;
	}
	public void endRDF() throws RDFHandlerException {
		// TODO Auto-generated method stub
		
		Iterator graphIter = graph.getGraphHandler().getGraph().listConceptNodes().iterator();
	    while(graphIter.hasNext())
	    {
	    	CoreNodeImpl corne =(CoreNodeImpl) graphIter.next();
	    	System.out.println(corne.toString());
	    }
		
	}

	public void handleComment(String comment) throws RDFHandlerException {
		// TODO Auto-generated method stub
		//Comments are not read yet! So we can't add them to the stack.
		}

	public void handleNamespace(String prefix, String uri)
			throws RDFHandlerException {
		// TODO Auto-generated method stub

	}

	public void handleStatement(Statement st) throws RDFHandlerException {
		// TODO Auto-generated method stub
		//Value Object = st.getObject();
		//Value Predicate = st.getPredicate();
		//Value Subject = st.getSubject();
		//System.out.println("Statements: "+Subject.stringValue()+" "+Predicate.stringValue()+" "+ Object.stringValue());
		try {
			this.graph.detectEvent(st);
		} catch (URISyntaxException e) {
			throw new RDFHandlerException(e);
		}
	}

	public void startRDF() throws RDFHandlerException {
		// TODO Auto-generated method stub
		System.out.println("start");

	}

	


}
