package sapience.proxy.persistence;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.Lookup;
import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;
import sapience.proxy.persistence.model.JDOReference;
import sapience.proxy.persistence.model.JDOStoredServices;

public class LookupImpl implements Lookup {

	@Autowired ServiceRepository repo;
	
	public List<Reference> check(Serializable sid) throws IOException {
		
		try {
			return repo.load(sid.toString());			
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public void put(String request, Reference reference) throws IOException {
		throw new RuntimeException("not supported");
	}

	public void put(String request, List<Reference> refList) throws IOException {
		Serializable sid = ((LocalElement) refList.get(0).getSource()).getDocumentID();
		
		// we always replace
		if (check(sid).size() > 0) {
		    repo.delete(sid.toString());
		}
		
		
		List<JDOReference> res = new ArrayList<JDOReference>();
		
		for(Reference ref : refList) {
			if(ref instanceof JDOReference) 
				res.add((JDOReference) ref);
		}
		
		this.put(new JDOStoredServices(sid.toString(), request, res));
		
	}
	
	public void put(JDOStoredServices jss) throws IOException {
		repo.store(jss);
	}


}
