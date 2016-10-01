package sapience.core.model;

import java.net.URI;

public class ConceptRelationImpl implements ConceptRelation {
ConceptNode related;
ConceptNode source;
private String label;

public void setRelated(ConceptNode related) {
	// TODO Auto-generated method stub
	this.related = related;
}

	public ConceptNode getRelated() {
		// TODO Auto-generated method stub
		return related;
	}


	public void setSource(ConceptNode source) {
		// TODO Auto-generated method stub
		this.source=source;
	}

	public ConceptNode getSource() {
		// TODO Auto-generated method stub
		return source;
	}

	public URI getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String toString()
	{
		return new String("[Related "+related.getIdentifier()+" Source " + source.getIdentifier()+" Label "+ label+"]");
	}
}
