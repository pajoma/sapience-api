package sapience.annotations.hibernate.tests.databaseactions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.entities.Entity;
import sapience.annotations.hibernate.utilities.HibernateUtil;



/**
 * This class tests the selection of more than one records with the same URI.
 * 
 * @author Henry
 */
public class TestSelectRecords {
	
	SessionFactory sessionFactory;
	Session session;
	private Transaction tx;
	private Transaction tx2;
	private Transaction tx3;
    
    @Before
	public void setUp() throws Exception {
		
		/** Getting the Session Factory and session */
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testDeleteRecord(){
		deleteAllRecords();
		ArrayList<Reference> concepts = addRecords();
		ArrayList<Reference> concepts2 = new ArrayList<Reference>();
		try {
			/** Starting the Transaction */
		    tx3 = session.beginTransaction();
		    Criteria query =
		    	session.createCriteria(Reference.class).
		    	add(Restrictions.
		    	ilike("uri", "testUri", MatchMode.EXACT));
//		    SQLQuery query = session.createSQLQuery("SELECT Concept.* FROM concept as Concept WHERE Concept.uri like :uri");
//		    query.setString("uri", "testURI");
		    //		    query.setMaxResults(10);
//		    query.addEntity("Concept", Concept.class);
			List<Entity> records = query.list();
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				Reference concept = (Reference) iterator.next();
				System.out.println(concept.getUrl());
				concepts2.add(concept);
			}
			/** Saving Concept */
		    tx3.commit();
		}
		catch (Exception e) {
		     if (tx3!=null) tx3.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     int i = concepts.size();
		     while (concepts.size() > 0 && i > 0) {
				for (int j = 0; j < concepts2.size(); j++) {
					if (concepts.get(i-1).equals(concepts2.get(j))) {
						concepts.remove(concepts.size()-1);
						break;
					}
				}
				i--;
			}
		     Assert.assertEquals(0, concepts.size());
		}
	}
	
	/**
	 * This method creates two Concepts, stores them in the database and sends them back.
	 * @return Concept
	 */
	@SuppressWarnings("finally")
	public ArrayList<Reference> addRecords(){
		Reference concept = null;
		Reference concept2 = null;
		ArrayList<Reference> concepts = new ArrayList<Reference>();
		try {
			/** Starting the Transaction */
			tx = session.beginTransaction();
		    /** Creating Concept */
		    concept = new Reference();
		    concept.setUri("testURI");
		    concept.setXPath("testXPath");
		    concept.setUrl("testURL");
		    concepts.add(concept);
		    /** Saving Concept */
		    session.save(concept);
		    tx.commit();
		    session.close();
		    
		    session = sessionFactory.openSession();
		    /** Starting the Transaction */
		    tx2 = session.beginTransaction();
		    /** Creating Concept */
		    concept2 = new Reference();
		    concept2.setUri("testURI");
		    concept2.setXPath("testXPath2");
		    concept2.setUrl("testURL2");
		    concepts.add(concept2);
		    /** Saving Concept */
		    session.save(concept2);
		    /** Commiting the changes */
		    tx2.commit();
		}
	    catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     if (tx2!=null) tx2.rollback();
		     e.printStackTrace();
		}
	    finally {
	    	return concepts;
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
