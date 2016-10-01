/**
 * 
 */
package sapience.annotations.hibernate.tests.databaseactions;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
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
public class TestDeleteAllRecords {
	
	SessionFactory sessionFactory;
	Session session;
	private Transaction tx;
	boolean empty;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testDeleteAllRecords(){
		Reference concept = null;
		try {
			tx = session.beginTransaction();
		    deleteAllRecords();
		    tx.commit();
		    empty = tableIsEmpty("Concept");
		}
		catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     e.printStackTrace();
		}
		finally {
		     session.close();
		     Assert.assertEquals(true, empty);
		}    
	}
	
	public boolean tableIsEmpty(String tableName) {
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(Reference.class);
//		SQLQuery query = session.createSQLQuery("select * from '"+tableName+"'");
		List tables = query.list();
		transaction.commit();
		if (tables.size() == 0) {
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
