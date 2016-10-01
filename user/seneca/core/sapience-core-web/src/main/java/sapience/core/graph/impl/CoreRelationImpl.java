package sapience.core.graph.impl;

import java.net.URI;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//import com.google.appengine.api.datastore.Key;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

@PersistenceCapable
public class CoreRelationImpl extends GraphEntityImpl implements CoreRelation {
	
//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Key id; 
	
	@Persistent
	final private CoreNode related;
	
	@Persistent
	final private CoreNode source;

	public CoreRelationImpl(URI uri, CoreNode source, CoreNode related) {
		super(uri);
		this.related = related;
		this.source = source;
	}

	public CoreNode target() {
		return related;
	}

	public CoreNode source() {
		return source;
	}



	public String toString() {
		return new String("[Related " + related.getIdentifier() + " Source " + source.getIdentifier() + " Label " + getIdentifier() + "]");
	}



}
