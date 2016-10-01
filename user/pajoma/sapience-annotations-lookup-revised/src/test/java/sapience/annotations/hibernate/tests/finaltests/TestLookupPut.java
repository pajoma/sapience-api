/**
 * 
 */
package sapience.annotations.hibernate.tests.finaltests;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import sapience.annotations.LookupImpl;
import sapience.annotations.exceptions.AddRecordFailedException;
import sapience.annotations.exceptions.RecordExistsException;
import sapience.annotations.exceptions.TableNotExistException;
import sapience.annotations.exceptions.UpdateRecordFailedException;
import sapience.annotations.exceptions.WrongEntityException;
import sapience.annotations.hibernate.ReferenceController;
import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.entities.Entity;
import sapience.annotations.hibernate.utilities.HibernateUtil;
import sapience.annotations.model.Reference;

/**
 * @author Henry
 *
 */
public class TestLookupPut {
	
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
	}
	
	@Test
	public void testAddRecord(){
		Reference ref = new Reference("testURI", "testXPath", "testURL");
		try {
			lookup.put(ref);
		} catch (WrongEntityException e) {
			e.printStackTrace();
		} catch (TableNotExistException e) {
			e.printStackTrace();
		} catch (UpdateRecordFailedException e) {
			e.printStackTrace();
		} catch (AddRecordFailedException e) {
			e.printStackTrace();
		} catch (RecordExistsException e) {
			e.printStackTrace();
		}finally{
			try {
				Assert.assertTrue(recordExists(ref.toEntity()));
			} catch (WrongEntityException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testUpdateRecord(){
		Reference ref = new Reference("testURI", "testXPath", "testURL2");
		try {
			lookup.put(ref);
		} catch (WrongEntityException e) {
			e.printStackTrace();
		} catch (TableNotExistException e) {
			e.printStackTrace();
		} catch (UpdateRecordFailedException e) {
			e.printStackTrace();
		} catch (AddRecordFailedException e) {
			e.printStackTrace();
		} catch (RecordExistsException e) {
			e.printStackTrace();
		}finally{
			try {
				Assert.assertTrue(recordExists(ref.toEntity()));
				Assert.assertEquals(1, numberOfLines());
			} catch (WrongEntityException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int numberOfLines(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(Reference.class);
		List lines = query.list();
		transaction.commit();
		session.close();
		return lines.size();
	}
	
	private boolean recordExists(Entity entity) throws WrongEntityException {
		Reference concept = null;
		if (entity instanceof Reference) {
			concept = (Reference) entity;
		}else{
			throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
		}
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(Reference.class).add(Restrictions.ilike("uri", concept.getUri(), MatchMode.EXACT)).add(Restrictions.ilike("xPath", concept.getXPath(), MatchMode.EXACT)).add(Restrictions.ilike("url", concept.getUrl(), MatchMode.EXACT));
		List records = query.list();
		transaction.commit();
		session.close();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
}
