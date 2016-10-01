package sapience.core.model;

public interface ConceptRelation extends ModelEntity {

	public ConceptNode getSource();
	
	public ConceptNode getRelated();
	
	public void setLabel(String label);

	public String getLabel();	
	
}
