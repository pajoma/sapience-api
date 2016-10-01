package sapience.core.rules;

import java.net.URISyntaxException;

import org.openrdf.model.Statement;

import sapience.core.graph.impl.JDOGraphBuilder;

public interface Rule {

	public abstract boolean meetsRule(Statement st, JDOGraphBuilder graphHandler) throws URISyntaxException;	
}
