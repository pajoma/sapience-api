package sapience.core.graph;

import java.util.Collection;

public interface CoreGraph {

    public Collection<CoreNode> listConceptNodes();
    
    public Collection<CoreRelation> listConceptRelations();
    
    public CoreNode searchNode(String uri);

    public CoreRelation searchRelation(String uri);
}
