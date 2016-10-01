package sapience.core.model;

import java.util.List;

public interface ConceptNode  extends ModelEntity {

	public List<ConceptRelation> listRelations();
	public List<String> getLables();
}
