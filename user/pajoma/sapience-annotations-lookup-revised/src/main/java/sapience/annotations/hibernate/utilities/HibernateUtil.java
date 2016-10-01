package sapience.annotations.hibernate.utilities;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This class handles the sessionfactory.
 * 
 * @author Henry
 */

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {

		Logger logger = Logger.getLogger(HibernateUtil.class.getName());

		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed

			// System.err.println("Initial SessionFactory creation failed." +
			// ex);

			logger.error(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * 
	 * @return sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Opens a session and will not bind it to a session context
	 * 
	 * @return the session
	 */
	public Session openSession() {
		return sessionFactory.openSession();
	}

	/**
	 * Returns a session from the session context. If there is no session in the
	 * context it opens a session, stores it in the context and returns it. This
	 * factory is intended to be used with a hibernate.cfg.xml including the
	 * following property <property
	 * name="current_session_context_class">thread</property> This would return
	 * the current open session or if this does not exist, will create a new
	 * session
	 * 
	 * @return the session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * closes the session factory
	 */
	public static void close() {
		if (sessionFactory != null)
			sessionFactory.close();
		sessionFactory = null;

	}
}
