package sapience.core.rules;

//Rule2
import java.net.URISyntaxException;
import java.util.Iterator;

import org.openrdf.model.Statement;
import org.openrdf.model.util.URIUtil;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;

public class SubClassOfRule implements Rule {

    public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException {
	if (st.getPredicate().getLocalName().equalsIgnoreCase("subClassOf")) {

	    CoreNode node = gb.createNode(st.getSubject().toString());
	    
	    // title is local name of subject
	    //already done in JDOGraphBuilder
	    //node.setTitle(st.getSubject().stringValue());

	    // domain is related //sailing Boat
	    // get Sailing Boat
	    CoreNode related = gb.createNode(st.getObject().stringValue());
	    
	    
	    // create relation
	    CoreRelation relation = gb.createRelation(st.getPredicate(), node, related);
	    
	    //add Relation to Source! Chaining!
	    node.listRelatedNodes().add(relation);

	    return true;
	}
	return false;
    }

}
