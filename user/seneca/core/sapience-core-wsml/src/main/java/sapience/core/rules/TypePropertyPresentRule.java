package sapience.core.rules;
//Rule 5
import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.JDOGraphBuilder;

public class TypePropertyPresentRule implements Rule {

	public boolean meetsRule(Statement st,JDOGraphBuilder gb) throws URISyntaxException {
        //Search if there is alreay an Object in the Graph
		//essential to perform it here distinction from other rules
		if (st.getPredicate().getLocalName().equalsIgnoreCase("type") &&
				gb.getGraph().searchNode(st.getObject().stringValue())!=null )
		{
			CoreNode node = gb.createNode(st.getSubject().toString());
			 
		    CoreNode related = gb.createNode(st.getObject().stringValue());
		    
		    // create relation
		    CoreRelation relation = gb.createRelation(st.getPredicate(), node, related);
		    
		    //add Relation to Source! Chaining!
		    node.listRelatedNodes().add(relation);

		}
		return false;
	}

}
