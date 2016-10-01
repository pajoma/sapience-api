package sapience.proxy.persistence;



import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import sapience.injectors.model.Reference;
import sapience.proxy.persistence.model.JDOStoredServices;

@Repository
public interface ServiceRepository {

	
	public List<Reference> load(String serviceID) throws IOException;
	
	public JDOStoredServices loadService(String serviceID) throws IOException;
	
	public Collection<JDOStoredServices> loadServices() throws IOException;

	public void delete(String serviceID) throws IOException;
	
	public void purge() throws IOException;

	void store(String sid, String request, List<Reference> references) throws IOException;
	
	void store(JDOStoredServices jss) throws IOException;

	
}
