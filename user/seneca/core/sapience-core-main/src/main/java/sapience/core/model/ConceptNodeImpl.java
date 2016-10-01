package sapience.core.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConceptNodeImpl implements ConceptNode {

	private URI identifier;
	private List<String> lables = new ArrayList<String>();
	private List<ConceptRelation> relations = new ArrayList<ConceptRelation>();

	public ConceptNodeImpl(String identifier)
	{
		try
		{
			this.identifier = new URI(identifier);
		}
		catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<ConceptRelation> listRelations() {
		// TODO Auto-generated method stub
		return relations;
	}

	public URI getIdentifier() {
		// TODO Auto-generated method stub
		return identifier;
	}

	public void setLables(List<String> lables) {
		this.lables = lables;
	}

	public List<String> getLables() {
		return lables;
	}

	public String toString()
	{
		return new String("Identifier: "+ identifier.toString()+" Lables: "+ lables.toString()+" Relations " + relations.toString());
	}

}
