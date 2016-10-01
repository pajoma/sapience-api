package sapience.core.rules;

//Rule2
import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.impl.JDOGraphBuilder;

public class DescriptionSubjectRule implements Rule {
    //obsolete
    public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException {
	if (st.getPredicate().getLocalName().equalsIgnoreCase("description")) {

		 //Node should be already present. So it is just returned here.
		 CoreNode node = gb.createNode(st.getSubject().toString());
		 
		 //Add the Description to the Node ( is stored in the Object)
		 node.listSubjects().add(st.getObject().stringValue());
	    return true;
	}
	return false;
    }

}
