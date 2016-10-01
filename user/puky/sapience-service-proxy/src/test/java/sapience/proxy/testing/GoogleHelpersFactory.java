package sapience.proxy.testing;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


/**
 * Bean, configured in applicationContext-tests.xml, ensures that the helpers will be setup at the beginning of the tests
 * (Local setup will always happen after injection, which depends on the helpers)
 * 
 * @author pajoma
 *
 */
public class GoogleHelpersFactory {

 
	
	private LocalServiceTestHelper datahelper;
	private  LocalServiceTestHelper memhelper;

	public static GoogleHelpersFactory createInstance() {
		GoogleHelpersFactory ghf = new GoogleHelpersFactory();
		ghf.memhelper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());
		ghf.memhelper.setUp();

		ghf.datahelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		ghf.datahelper.setUp();

		
		return ghf;
	}

	/**
	 * @return the datahelper
	 */
	public  LocalServiceTestHelper getDatahelper() {
		return datahelper;
	}

	/**
	 * @return the memhelper
	 */
	public  LocalServiceTestHelper getMemhelper() {
		return memhelper;
	}

	public void tearDownHelpers() {
		memhelper.tearDown();
		datahelper.tearDown();
		
	}
	

	
}
