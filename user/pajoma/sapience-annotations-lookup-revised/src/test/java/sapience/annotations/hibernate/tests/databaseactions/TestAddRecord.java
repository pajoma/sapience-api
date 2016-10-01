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
import sapience.annotations.hibernate.tests.AnnotationsLookupTest;
import sapience.annotations.hibernate.utilities.HibernateUtil;


/**
 * This class tests to add a record into the database
 *
 * @author Henry
 */
public class TestAddRecord extends AnnotationsLookupTest {
    SessionFactory sessionFactory;
    Session session;
    private Transaction tx;
    private Transaction tx2;
    private Transaction tx3;
    private boolean exist;

    @Before
    public void setUp() throws Exception {

        /** Getting the Session Factory and session */
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Test
    public void testAddRecord() {
        try {
            /** Starting the Transaction */
            tx = session.beginTransaction();
            deleteAllRecords();
            tx.commit();

            tx2 = session.beginTransaction();
            /** Creating Concept */

            Reference reference = super.references.get(0);

            /** Saving Concept */
            session.save(reference);

            tx2.commit();
           
            tx3 = session.beginTransaction();
            Assert.assertTrue(recordExists(reference));

            /** Commiting the changes */
            tx3.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            if (tx2 != null) tx2.rollback();
            if (tx3 != null) tx3.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    /**
     * This method checks whether a concept is selected or not.
     *
     * @param concept
     * @return exist
     */
    public boolean recordExists(Reference concept) {
        SQLQuery query = session.createSQLQuery("SELECT * FROM concept c WHERE c.uri = '" + concept.getUri() + "' AND c.xPath = '" + concept.getXPath() + "' AND c.url = '" + concept.getUrl() + "'");
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
    public void deleteAllRecords() {
        Query query = session.createQuery("delete from Concept");
        query.executeUpdate();
	}
}
