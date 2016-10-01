package sapience.annotations.hibernate.tests.databaseactions;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.utilities.HibernateUtil;

/**
 * This class tests to create the table for the entity "Concept".
 * 
 * @author Henry
 */

public class TestCreateTable {
    Logger logger = Logger.getLogger(TestCreateTable.class.getName());
    
    
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;
	private Transaction tx2;
	private boolean exist;
	
	@Before
	public void setUp() throws Exception {
		
		logger.info("Test: Creating Tables");
		
		/** Getting the Session Factory and session */
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testCreateTable(){
		try {
		    /** Starting the Transaction */
		    tx = session.beginTransaction();
		    Reference concept = new Reference();
		    concept.setUri("http://example.com/ontology#concept");
		    
		    SQLQuery query = session.createSQLQuery(concept.getCreateStatement());
			query.executeUpdate();
			tx.commit();
			
			tx2 = session.beginTransaction();
			exist = tableExists("concept");
		    /** Commiting the changes */
		    tx2.commit();
		}
		catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     if (tx2!=null) tx2.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     Assert.assertEquals(true, exist);
		}
	}
	
	
//	@Test
//	public void testCreateTable(){
//		try {
//		    /** Starting the Transaction */
//		    tx = session.beginTransaction();
//		    Concept concept = new Concept();
//		    SQLQuery query = session.createSQLQuery(concept.getCreateStatement());
//			query.executeUpdate();
//			tx.commit();
//			
//			tx2 = session.beginTransaction();
//			exist = tableExists("concept");
//		    /** Commiting the changes */
//		    tx2.commit();
//		}
//		catch (Exception e) {
//		     if (tx!=null) tx.rollback();
//		     if (tx2!=null) tx2.rollback();
//		     e.printStackTrace();
//		}
//		finally {
//		     session.close();
//		     Assert.assertEquals(true, exist);
//		}
//	}
//	
	/**
	 * This method checks whether a given table exists or not.
	 * @param name
	 * @return
	 */
	public boolean tableExists(String name){
		SQLQuery query = session.createSQLQuery("select name from sqlite_master where name = '"+name+"'");
		List tables = query.list();
		System.out.println(tables.get(0));
		if (tables.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
}
