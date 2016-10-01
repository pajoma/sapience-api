/**
 * 
 */
package sapience.annotations.hibernate.tests.databaseactions;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.utilities.HibernateUtil;


/**
 * @author Henry
 *
 */
public class TestUpdateRecord {
	SessionFactory sessionFactory;
	Session session;
	private Transaction tx;
	private Transaction tx2;
	private Transaction tx3;
	private Transaction tx4;
	private boolean exist;
    
    @Before
	public void setUp() throws Exception {
		
		/** Getting the Session Factory and session */
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testAddRecord(){
		Reference concept2 = null;
		try {
			/** Starting the Transaction */
		    tx = session.beginTransaction();
		    deleteAllRecords();
		    tx.commit();
		    
		    tx2 = session.beginTransaction();
		    /** Creating Concept */
		    Reference concept = new Reference();
		    concept.setUri("testURI");
		    concept.setXPath("testXPath");
		    concept.setUrl("testURL");
		    /** Saving Concept */
		    session.save(concept);
		    tx2.commit();
		    
		    tx3 = session.beginTransaction();
		    SQLQuery query = session.createSQLQuery("SELECT Concept.* FROM concept as Concept WHERE Concept.uri = '"+concept.getUri()+"' AND Concept.xPath = '"+concept.getXPath()+"'");
		    query.addEntity("Concept", Reference.class);
			List records = query.list();
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				concept2 = (Reference) iterator.next();
			}
			concept2.setUrl("testURL2");
			session.update(concept2);
		    /** Commiting the changes */
		    tx3.commit();
		    
		    tx4 = session.beginTransaction();
		    exist = recordExists(concept2);
		    /** Commiting the changes */
		    tx4.commit();
		}
		catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     if (tx2!=null) tx2.rollback();
		     if (tx3!=null) tx3.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     Assert.assertEquals(true, exist);
		}
	}
	
	/**
	 * This method checks whether a concept is selected or not.
	 * @param concept
	 * @return exist
	 */
	public boolean recordExists(Reference concept){
		SQLQuery query = session.createSQLQuery("SELECT * FROM concept c WHERE c.uri = '"+concept.getUri()+"' AND c.xPath = '"+concept.getXPath()+"' AND c.url = '"+concept.getUrl()+"'");
		List records = query.list();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method deletes all records in the database.
	 */
	public void deleteAllRecords(){
		SQLQuery query = session.createSQLQuery("delete from concept");
		query.executeUpdate();
	}
}
