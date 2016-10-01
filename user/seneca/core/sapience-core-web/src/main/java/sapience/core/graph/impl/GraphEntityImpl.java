package sapience.core.graph.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Persistent;

import sapience.core.graph.GraphEntity;


public class GraphEntityImpl implements GraphEntity {

	@Persistent
	private List<String> subjects;
	
	@Persistent
	private String title;
	
	@Persistent
	private final URI identifier;

	public GraphEntityImpl(String id) throws URISyntaxException {
		identifier = new URI(id);
	}
	
	public GraphEntityImpl(URI id) {
		identifier = id;
	}
	
	public void addSubject(String subject) {
		listSubjects().add(subject);

	}

	public String getTitle() {
		return title;
	}

	public URI getIdentifier() {
		return identifier;
	}

	public List<String> listSubjects() {
		if (subjects == null) {
			subjects = new ArrayList<String>();
			
		}

		return subjects;
	}

	public void setTitle(String title) {
		this.title = title;

	}

}
