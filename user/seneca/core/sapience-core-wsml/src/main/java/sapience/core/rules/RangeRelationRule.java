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

public class RangeRelationRule implements Rule{

	public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException {
		if (st.getPredicate().getLocalName().equalsIgnoreCase("range"))
				{
			//Search Node
			CoreNode related = gb.createNode(st.getObject().toString());
			
			URI tmp = new URI(st.getSubject().stringValue());
			//Add Node to relation
			System.out.println(tmp);
			CoreRelation relation = gb.createRelation(tmp, null, related);
			
			//No chaining nessesary!

				
			return true;
				}
		return false;
	}

}
