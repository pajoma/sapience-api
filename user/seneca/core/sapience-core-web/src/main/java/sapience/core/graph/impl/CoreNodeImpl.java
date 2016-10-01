package sapience.core.graph.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//import com.google.appengine.api.datastore.Key;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

@PersistenceCapable
public class CoreNodeImpl extends GraphEntityImpl implements CoreNode {
	
//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Key id; 
	
	public CoreNodeImpl(String id) throws URISyntaxException {
		super(id);
	}

	
	private List<CoreRelation> relations = null;

	
	public List<CoreRelation> listRelatedNodes() {
		if (relations == null) {
			relations = new ArrayList<CoreRelation>();
			
		}
		return relations;
	}

	

	public String toString() {
		return new String("Identifier: " + super.getIdentifier().toString() + " Lables: " + getTitle().toString() + " Relations " + relations.toString());
	}




}
