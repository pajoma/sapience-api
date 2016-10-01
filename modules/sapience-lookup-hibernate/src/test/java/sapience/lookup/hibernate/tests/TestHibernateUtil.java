/**
 * 
 */
package sapience.lookup.hibernate.tests;

import org.hibernate.SessionFactory;
import org.junit.Test;

import sapience.lookup.hibernate.utilities.HibernateUtil;


/**
 * @author Henry
 *
 */
public class TestHibernateUtil {
	
	@Test
	public void testInitialization(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	}

}
