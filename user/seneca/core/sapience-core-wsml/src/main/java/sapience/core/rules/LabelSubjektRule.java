package sapience.core.rules;
//Rule2
import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.CoreNode;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;

public class LabelSubjektRule implements Rule{

	public boolean meetsRule(Statement st, JDOGraphBuilder gb) throws URISyntaxException {
		if (st.getPredicate().getLocalName().equalsIgnoreCase("label"))
				{
			 //Node should be already present. So it is just returned here.
			 CoreNode node = gb.createNode(st.getSubject().toString());
			 
			 //Add the Label to the Node ( is stored in the Object)
			 node.listSubjects().add(st.getObject().stringValue());
					return true;
				}
		return false;
	}

}
