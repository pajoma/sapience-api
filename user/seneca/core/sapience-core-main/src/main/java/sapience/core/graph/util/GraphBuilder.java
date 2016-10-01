package sapience.core.graph.util;


import java.net.URI;
import java.net.URISyntaxException;

import sapience.core.graph.CoreGraph;
import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

public interface GraphBuilder {


	public CoreGraph getGraph();

	public CoreNode createNode(String identifier) throws URISyntaxException ;

	public CoreRelation createRelation(URI uri, CoreNode source, CoreNode related);
	

	

}
