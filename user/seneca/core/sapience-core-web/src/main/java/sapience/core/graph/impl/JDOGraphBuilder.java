package sapience.core.graph.impl;


import java.net.URI;
import java.net.URISyntaxException;

import sapience.core.graph.CoreGraph;
import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.CoreRelationImpl;
import sapience.core.graph.impl.CoreGraphImpl;
import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.util.GraphBuilder;

public class JDOGraphBuilder implements GraphBuilder {

	private CoreGraph graph;

	public CoreGraph getGraph() {
		if (graph == null) {
			
			// not new graph, but take the graph from the database
			graph = new CoreGraphImpl();

		}

		return graph;
	}

	public CoreNode createNode(String identifier) throws URISyntaxException {
		// check if already in graph (or even in repository)
		CoreNode cn = getGraph().searchNode(identifier);

		// no, create new
		if (cn == null) {
			cn = new CoreNodeImpl(identifier);
			((CoreGraphImpl) getGraph()).mapConceptNodes().put(identifier, cn);
		}
		return cn;

	}

	public CoreRelation createRelation(URI uri, CoreNode source, CoreNode related) {
		CoreRelation cn = getGraph().searchRelation(uri.toString());

		if (cn == null) {
			cn = new CoreRelationImpl(uri, source, related);
			((CoreGraphImpl) getGraph()).mapConceptRelations().put(uri.toString(), cn);
			
			// chaining
			source.listRelatedNodes().add(cn);
		}
		return cn;
	}
	
	public CoreRelation createRelation(org.openrdf.model.URI uri, CoreNode source, CoreNode related) {
		return this.createRelation(URI.create(uri.toString()), source, related);
	}
	

	

}
