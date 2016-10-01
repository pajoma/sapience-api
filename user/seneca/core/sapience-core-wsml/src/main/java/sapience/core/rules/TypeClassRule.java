package sapience.core.rules;
//Rule1

import java.net.URISyntaxException;
import java.util.Iterator;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;


public class TypeClassRule implements Rule {

	public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException  {
		// TODO Auto-generated method stub
		//obsolete
		if (st.getPredicate().getLocalName().equalsIgnoreCase("type") &&
				st.getObject().stringValue().endsWith("#Class"))
		{
			//create the node
		    CoreNode node = gb.createNode(st.getSubject().toString());
		    
		    // title is local name of subject
		    //already done in JDOGraphBuilder
		    //node.setTitle(st.getSubject().stringValue());

			return true;
		}
		return false;
	}
}
