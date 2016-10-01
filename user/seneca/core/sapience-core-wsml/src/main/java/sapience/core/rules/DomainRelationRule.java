package sapience.core.rules;

//Rule2
import java.net.URI;
import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.impl.CoreRelationImpl;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;

public class DomainRelationRule implements Rule {

	public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException {
		if (st.getPredicate().getLocalName().equalsIgnoreCase("domain")) {
			
			//Search Node
			CoreNode node = gb.createNode(st.getObject().toString());
			
			URI tmp = new URI(st.getSubject().stringValue());
			//Add Node to relation
			System.out.println(tmp);
			CoreRelation relation = gb.createRelation(tmp, node, null);
			
			//Chaining can be done already!
			node.listRelatedNodes().add(relation);
		    
			return true;
		}
		return false;
	}

}
