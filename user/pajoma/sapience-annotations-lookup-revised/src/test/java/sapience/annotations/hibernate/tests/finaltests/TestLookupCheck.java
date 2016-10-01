/**
 * 
 */
package sapience.annotations.hibernate.tests.finaltests;


import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import sapience.annotations.Lookup;
import sapience.annotations.LookupImpl;
import sapience.annotations.exceptions.AddRecordFailedException;
import sapience.annotations.exceptions.DatabaseException;
import sapience.annotations.exceptions.RecordExistsException;
import sapience.annotations.exceptions.TableNotExistException;
import sapience.annotations.exceptions.WrongEntityException;
import sapience.annotations.hibernate.ReferenceController;
import sapience.annotations.hibernate.entities.Reference;


/**
 * @author Henry
 *
 */
public class TestLookupCheck {
	
	private static LookupImpl lookup;
	private static ReferenceController refControl;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		lookup = new LookupImpl();
		refControl = lookup.getReferenceController();
		refControl.deleteAllRecords("Concept");
		addExampleRecords();
	}
	
	@Test
	public void noStoredConcepts() throws DatabaseException{
		List<sapience.annotations.model.Reference> concepts = null;
		try {
			concepts = lookup.check("testURI2");
		} catch (TableNotExistException e) {
			e.printStackTrace();
		} finally{
			if (concepts != null) {
				Assert.assertTrue(concepts.isEmpty());
			} else {
				new AssertionError("noStoredConcepts failed!");
			}
		}
	}
	
	@Test
	public void oneStoredConcept(){
		List<sapience.annotations.model.Reference> concepts = null;
		try {
			concepts = lookup.check("testURI1");
		} catch (TableNotExistException e) {
			e.printStackTrace();
		}finally{
			if (concepts != null) {
				Assert.assertTrue(concepts.size() == 1);
			} else {
				new AssertionError("noStoredConcepts failed!");
			}
		}
	}
	
	@Test
	public void manyStoredConcepts(){
		List<Reference> concepts = null;
		try {
			concepts = lookup.check("testURI");
		} catch (TableNotExistException e) {
			e.printStackTrace();
		}finally{
			if (concepts != null) {
				Assert.assertEquals(5, concepts.size());
			} else {
				new AssertionError("noStoredConcepts failed!");
			}
		}
	}
	
	private static void addExampleRecords(){
		
		for (int i = 0; i < 5; i++) {
			Reference c = new Reference();
			c.setUri("testURI");
			c.setXPath("testXPath"+i);
			c.setUrl("testURL"+i);
			try {
				refControl.addRecord(c);
			} catch (WrongEntityException e) {
				e.printStackTrace();
			} catch (TableNotExistException e) {
				e.printStackTrace();
			} catch (AddRecordFailedException e) {
				e.printStackTrace();
			} catch (RecordExistsException e) {
				e.printStackTrace();
			}
		}
		
		
		Reference c = new Reference();
		c.setUri("testURI1");
		c.setXPath("testXPath");
		c.setUrl("testURL");
		
		try {
			refControl.addRecord(c);
		} catch (WrongEntityException e) {
			e.printStackTrace();
		} catch (TableNotExistException e) {
			e.printStackTrace();
		} catch (AddRecordFailedException e) {
			e.printStackTrace();
		} catch (RecordExistsException e) {
			e.printStackTrace();
		}
	}

}
