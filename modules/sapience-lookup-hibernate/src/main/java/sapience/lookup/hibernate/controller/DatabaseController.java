package sapience.lookup.hibernate.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sapience.lookup.exceptions.DatabaseException;
import sapience.lookup.exceptions.FailedCreateTableException;
import sapience.lookup.exceptions.FailedDeleteRecordException;
import sapience.lookup.hibernate.model.DatabaseEntity;
import sapience.lookup.hibernate.utilities.HibernateUtil;

/**
 * @author Henry Michels
 *
 */
public abstract class DatabaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	protected SessionFactory sessionFactory;
	protected Session session;
	
	/**
	 * Initialize the connection to the database and opens a new session
	 */
	protected void startDatabaseAccess(){
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	/**
	 * Closes the session.
	 */
	protected void endDatabaseAccess(){
		session.close();
	}
	
	/**
	 * This method creates a table in the database for the implemented entity.
	 * @throws FailedCreateTableException
	 */
	abstract public void createTable()throws DatabaseException;
	
	/**
	 * This mehtod adds a record into the table
	 * @param entity
	 * @throws DatabaseException
	 */
	abstract public void addRecord(DatabaseEntity entity)throws DatabaseException;
	
	/**
	 * This method deletes a record from the table
	 * @param entity
	 * @throws DatabaseException
	 */
	abstract public void deleteRecord(DatabaseEntity entity) throws DatabaseException;
	
	/**
	 * This method selects a record which is represented by the given entity and sends the record
	 * as an entity back.
	 * Return "null" if nothing is selected.
	 * @param entity
	 * @return
	 */
	abstract public DatabaseEntity selectRecord(DatabaseEntity entity)throws DatabaseException;
	
	/**
	 * This method selects all records with the given value in the given column out of the table
	 * and sends them back as a list of entities.
	 * Returns an empty list if nothing is selected.
	 * @param column
	 * @param value
	 * @return
	 */
	abstract public List<DatabaseEntity> selectRecords(Hashtable<String,String> columnsAndValues)throws DatabaseException;;
	
	/**
	 * This method updates the given entity.
	 * @param entity
	 */
	abstract public void updateRecord(DatabaseEntity entity) throws DatabaseException;
	
	/**
	 * This method checks whether a record exists or not.
	 * @param entity
	 * @return
	 */
	abstract protected boolean recordExists(DatabaseEntity entity)throws DatabaseException;
	
	/**
	 * This method searches for the given table in the database
	 * @param tableName
	 * @return
	 */
	abstract protected boolean tableExists();
	
	/**
	 * This method checks whether the given table is empty or not.
	 * 
	 * @param tableName
	 * @return
	 */
	abstract protected boolean tableIsEmpty();
	
	/**
	 * This method deletes all records from the given table.
	 * @param tableName
	 * @throws FailedDeleteRecordException 
	 */
	abstract public void deleteAllRecords() throws DatabaseException;
}
