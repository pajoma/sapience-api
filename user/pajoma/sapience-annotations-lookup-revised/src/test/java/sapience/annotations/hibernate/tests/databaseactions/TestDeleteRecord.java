package sapience.annotations.hibernate.tests.databaseactions;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.utilities.HibernateUtil;

/**
 * This class tests to delete a record out of the database
 * 
 * @author Henry
 */

public class TestDeleteRecord {
	
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
	public void testDeleteRecord(){
		Reference concept = null;
		try {
			tx4 = session.beginTransaction();
		    deleteAllRecords();
		    tx4.commit();
		    tx = session.beginTransaction();
		    concept = addRecord();
		    tx.commit();
			/** Starting the Transaction */
		    tx2 = session.beginTransaction();
		    SQLQuery q = session.createSQLQuery("delete from Concept where uri = :uri and xPath = :xPath");
		    q.setString("uri", concept.getUri());
		    q.setString("xPath", concept.getXPath());
		    q.executeUpdate();
		    /** Commiting the changes */
		    tx2.commit();
		    
		    /** Starting the Transaction */
		    tx3 = session.beginTransaction();
		    exist = recordExists(concept);
		    /** Commiting the changes */
		    tx3.commit();
		}
		catch (Exception e) {
		     if (tx2!=null) tx2.rollback();
		     if (tx3!=null) tx3.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     Assert.assertEquals(false, exist);
		}
	}
	
	/**
	 * This method checks whether a concept is selected or not.
	 * @param concept
	 * @return exist
	 */
	public boolean recordExists(Reference concept){
		System.out.println(concept.getUrl());
		SQLQuery query = session.createSQLQuery("SELECT Concept.* FROM concept as Concept WHERE Concept.uri = '"+concept.getUri()+"' AND Concept.xPath = '"+concept.getXPath()+"'");
	    query.addEntity("Concept", Reference.class);
		List records = query.list();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method creates a Concept, stores this Concept in the database and sends it back.
	 * @return Concept
	 */
	public Reference addRecord(){
		Reference concept = null;
			
		    /** Creating Concept */
		    concept = new Reference();
		    concept.setUri("testURI");
		    concept.setXPath("testXPath");
		    concept.setUrl("testURL");
		    
		    /** Saving Concept */
		    session.save(concept);
		    
		    /** Commiting the changes */
		    
	    return concept;
	}
	
	/**
	 * This method deletes all records in the database.
	 */
	public void deleteAllRecords(){
		SQLQuery query = session.createSQLQuery("delete from concept");
		query.executeUpdate();
	}
}
