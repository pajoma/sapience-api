package sapience.injectors.testclient;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import sapience.injectors.factories.ConfigurationFactory;
import sapience.lookup.LookupImpl;

/**
 * Testcase, testing the complete lifecycle
 * 
 * @author pajoma
 *
 */
public abstract class TestLifeCycle extends BaseTest {
	
	@Autowired ConfigurationFactory config;

	public void testLifeCycle() throws IOException {
		this.testExtract();
		this.testInject();
	
		try {
			
			
			LookupImpl lookup = (LookupImpl) config.getLookup();
		
			lookup.getReferenceController().deleteAllRecords(); 
		} catch (Exception e) {
			throw new IOException(e); 
		} 
	}
	


	public ConfigurationFactory getConfiguration() {
		return config; 
	}
	
	public abstract void testExtract() throws IOException;
	
	
	public abstract void testInject() throws IOException;

}
