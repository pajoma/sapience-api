/**
 * 
 */
package sapience.lookup.hibernate.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.lookup.hibernate.controller.ReferenceController;
import sapience.lookup.hibernate.model.HibernateLocalElement;
import sapience.lookup.hibernate.model.HibernateModelFactory;
import sapience.lookup.hibernate.model.HibernateReference;

/**
 * @author Henry
 *
 */
public class TestPatterns {
	
	ReferenceController refControl;
	HibernateModelFactory fac;
	String docID = "http://gdi-kalypso1.gridlab.uni-hannover.de:8080/ogc?service=WPS&request=DescribeProcess&version=0.4.0&identifier=Gaja3d_createTin";
	String elementID = "//ns3:Capabilities[@version='0.4.0']/ns3:ProcessOfferings/ns3:Process/ns1:Identifier[@codeSpace='']/text()='Gaja3d_createTin'/sibling()";
	String element = "http://services.ifgi.de/Gaja3DWorkflow#CreateTin";
    
    @Before
	public void setUp() throws Exception {
		refControl = new ReferenceController();
	}
	@Test
	public void testQuote(){
		try {
			refControl.deleteAllRecords();
			HibernateLocalElement local = fac.createCompositeName(docID, elementID);
		    HibernateReference ref = HibernateModelFactory.apostrophesToQuotes(fac.createBinding(local, element));
		    refControl.addRecord(ref);
		}
		catch (Exception e) {
		     e.printStackTrace();
		     Assert.fail();
		}
	}

}
