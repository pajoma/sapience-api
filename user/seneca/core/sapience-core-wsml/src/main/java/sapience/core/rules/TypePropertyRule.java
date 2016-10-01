package sapience.core.rules;
//Rule 5
import java.net.URI;
import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;
import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;

/**
 * This creates only an incomplete Relation source and relation Nodes are missing!
 * It is only complete if Rules RangeRelationRule and DomainRelation Rule set the source and related nodes! 
 * @author seneca
 *
 */
public class TypePropertyRule implements Rule {

	
	public boolean meetsRule(Statement st,JDOGraphBuilder gb) throws URISyntaxException {
		//obsolete
		if (st.getPredicate().getLocalName().equalsIgnoreCase("type") &&
				st.getObject().stringValue().endsWith("#Property"))
		{
			// create relation the relation it is incomplete!
			URI tmp = new URI(st.getSubject().stringValue());
		    CoreRelation relation = gb.createRelation(tmp, null, null);
			return true;
		}
		return false;
	}

}
