package sapience.core.graph;

import java.net.URI;
import java.util.List;

public interface GraphEntity {

	
	public URI getIdentifier();
	
	// core properties
	
	// dc:title
	public String getTitle();
	
	// dc:subject
	public List<String> listSubjects();

	public void addSubject(String subject);

	public void setTitle(String title);
}
