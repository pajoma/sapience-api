package sapience.core.graph.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import sapience.core.graph.CoreGraph;
import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

//import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class CoreGraphImpl implements CoreGraph {

//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	Key id;

	@Persistent
	private Map<String, CoreNode> nodes;

	@Persistent
	private Map<String, CoreRelation> edges;

	public Map<String, CoreNode> mapConceptNodes() {
		if (nodes == null) {
			nodes = new HashMap<String, CoreNode>();

		}

		return nodes;
	}

	public Collection<CoreNode> listConceptNodes() {
		return mapConceptNodes().values();
	}

	public Map<String, CoreRelation> mapConceptRelations() {
		if (edges == null) {
			edges = new HashMap<String, CoreRelation>();

		}

		return edges;
	}

	public CoreNode searchNode(String uriIdentifier) {
		return mapConceptNodes().get(uriIdentifier);
	}

	public CoreRelation searchRelation(String uri) {
		return mapConceptRelations().get(uri);

	}

	public Collection<CoreRelation> listConceptRelations() {
		return mapConceptRelations().values();
	}

}
