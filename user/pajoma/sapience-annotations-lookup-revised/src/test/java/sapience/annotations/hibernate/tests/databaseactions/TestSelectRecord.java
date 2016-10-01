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
public class TestSelectRecord {
	
	SessionFactory sessionFactory;
	Session session;
	private Transaction tx;
	private Transaction tx2;
    
    @Before
	public void setUp() throws Exception {
		
		/** Getting the Session Factory and session */
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testDeleteRecord(){
		Reference concept = addRecord();
		Reference concept2 = null;
		try {
			/** Starting the Transaction */
		    tx2 = session.beginTransaction();
		    SQLQuery query = session.createSQLQuery("SELECT Concept.* FROM concept as Concept WHERE Concept.uri = '"+concept.getUri()+"' AND Concept.xPath = '"+concept.getXPath()+"'");
		    query.addEntity("Concept", Reference.class);
			List records = query.list();
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				concept2 = (Reference) iterator.next();
			}
			/** Commiting the changes */
		    tx2.commit();
		}
		catch (Exception e) {
		     if (tx2!=null) tx2.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     Assert.assertEquals(concept.getUri(), concept2.getUri());
		     Assert.assertEquals(concept.getUrl(), concept2.getUrl());
		     Assert.assertEquals(concept.getXPath(), concept2.getXPath());
		}
	}
	
	/**
	 * This method creates a Concept, stores this Concept in the database and sends it back.
	 * @return Concept
	 */
	@SuppressWarnings("finally")
	public Reference addRecord(){
		Reference concept = null;
		try {
			/** Starting the Transaction */
//			tx = session.beginTransaction();
			
		    /** Creating Concept */
		    concept = new Reference();
		    concept.setUri("testURI");
		    concept.setXPath("testXPath");
		    concept.setUrl("testURL");
		    
		    /** Saving Concept */
//		    session.saveOrUpdate(concept);
		    
		    /** Commiting the changes */
//		    tx.commit();
		}
	    catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     e.printStackTrace();
		}
	    finally {
	    	return concept;
	    }
	}
}
