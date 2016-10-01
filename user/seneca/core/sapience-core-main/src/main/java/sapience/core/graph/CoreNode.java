package sapience.core.graph;

import java.util.List;

public interface CoreNode  extends GraphEntity {

	/**
	 * Returns a list of relations where 
	 * @return
	 */
	public List<CoreRelation> listRelatedNodes();

}
