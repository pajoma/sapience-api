package sapience.core.graph;

import java.util.ArrayList;
import java.util.List;

import sapience.core.model.ConceptNodeImpl;

public class GraphHandler {
	
	private List<ConceptNodeImpl> graphNodes;
	
	public GraphHandler()
	{
		setGraphNodes(new ArrayList<ConceptNodeImpl>());
	}
	
	public void addConceptNode(ConceptNodeImpl node)
	{
		graphNodes.add(node);
	}

	public void setGraphNodes(List<ConceptNodeImpl> graphNodes) {
		this.graphNodes = graphNodes;
	}

	public List<ConceptNodeImpl> getGraphNodes() {
		return graphNodes;
	}
	
	public ConceptNodeImpl searchNode(String uriIdentifier)
	{
		ConceptNodeImpl searchResult=null;
		
		for(int i=0; i<graphNodes.size();i++)
		{
			if(graphNodes.get(i).getIdentifier().toString().equalsIgnoreCase(uriIdentifier))
			{
				searchResult = graphNodes.get(i);
			}
		}
		
		return searchResult;
	}
}
