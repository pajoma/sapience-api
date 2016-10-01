package sapience.core.persistence;

import java.io.IOException;
import java.util.List;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

public interface GraphRepository {

	public List<CoreNode> listNodes() throws IOException;
	
	
	public List<CoreRelation> listRelations() throws IOException;
	
	public void addNodes(List<CoreNode> nodes) throws IOException;
}

