package sapience.core.consistency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openrdf.model.Graph;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.helpers.StatementCollector;

import sapience.core.graph.RDFGraph;

public class ConsistencyChecks {

	private final RepositoryConnection temporaryConnection;
	private List<? extends Statement> checkedListOfStatements = null;
	
	
	public ConsistencyChecks(RepositoryConnection temporaryConnection) {
		this.temporaryConnection = temporaryConnection;

		
	}
	
	
	public void run() throws RepositoryException, QueryEvaluationException {
		GraphQueryResult graphResult = null;
		
		try {
			// get just everything from the repository
			GraphQuery q = temporaryConnection.prepareGraphQuery(QueryLanguage.SPARQL, "CONSTRUCT  * FROM {?a ?b ?c}");
			
			// let the handler do the work
			ConsistencyCheckHandler cch = new ConsistencyCheckHandler();
			
			q.evaluate();

			
			// copy into collection, to be able to build a graph
			Collection<Statement> sts = new ArrayList<Statement>();
			while (graphResult.hasNext()) {
				sts.add(graphResult.next());
			}
			
			RDFGraph graph = new RDFGraph(sts); 
			for(Statement st : graph.listStatements()) {
				this.checkStatement(st, graph);
			}
			
			
			// every statement should be marked as checked now
			
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			if (graphResult != null) 
				graphResult.close();
		}
		
	}


	private RDFGraph RDFGraph(Collection<Statement> sts) {
		// TODO Auto-generated method stub
		return null;
	}


	private void checkStatement(Statement st, RDFGraph graph) {
		
		System.out.println(st.toString());
		
	}


	public List<? extends Statement> getStatements() {
		if(checkedListOfStatements == null) {
			checkedListOfStatements = new ArrayList<Statement>(); 
		}
		return checkedListOfStatements;
	}
}
