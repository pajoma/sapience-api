package sapience.proxy.testing;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.factories.ReferenceFactory;
import sapience.injectors.model.Reference;
import sapience.proxy.persistence.JDOServiceRepository;
import sapience.proxy.persistence.ServiceRepository;
import sapience.proxy.persistence.model.JDOReference;
import sapience.proxy.persistence.model.JDOStoredServices;


public class TestStoring extends ProxyTestCase {
	
	@Autowired ServiceRepository repository;
	
	@Autowired ReferenceFactory fac;
	
	 @Test

	public void test() throws IOException {
		
		String documentName = "http://swing.brgm.fr/cgi-bin/carrieres?service=wfs&version=1.0.0&request=GetCapabilities";
		String elementName = "//WFS_Capabilities /Service/Title/text()=\"Quarries\"";
		String reference = "http://wsmo.cat.org/api/few3lkr3";		
		String sid = "fef312a3";
		JDOReference ref = (JDOReference) fac.createBinding(fac.createCompositeName(documentName, elementName), reference);
		JDOStoredServices sr = new JDOStoredServices(sid,documentName,ref);
		
		((JDOServiceRepository) repository).store(sr);
		
		
		// find
			
		List<Reference> load = repository.load(sid);
		Assert.assertEquals(1, load.size());
				
	}

}

