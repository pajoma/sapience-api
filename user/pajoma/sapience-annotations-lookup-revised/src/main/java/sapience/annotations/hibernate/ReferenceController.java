package sapience.annotations.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import sapience.annotations.exceptions.AddRecordFailedException;
import sapience.annotations.exceptions.CreationTableFailedException;
import sapience.annotations.exceptions.DeleteRecordFailedException;
import sapience.annotations.exceptions.RecordExistsException;
import sapience.annotations.exceptions.TableNotExistException;
import sapience.annotations.exceptions.UpdateRecordFailedException;
import sapience.annotations.exceptions.WrongEntityException;
import sapience.annotations.hibernate.entities.Reference;
import sapience.annotations.hibernate.entities.Entity;

/**
 * This class handles the access to the database
 * 
 * @author Henry
 *
 */
public class ReferenceController extends DatabaseController{

	private Reference associatedEntity = new Reference();
	
	// table properties
	private static String fieldElementID = "ElementID";
	private static String fieldReferencedElement = "ReferencedElement";
	private static String fieldReferencedType = "ReferencedType";
	private static String fieldResourceID = "ResourceID";
	private static String tableName = "ReferenceTable";
	

	
	/**
	 * Empty Constructor
	 * @throws CreationTableFailedException 
	 */
	public ReferenceController() throws CreationTableFailedException{
		try {
			createTable();
			
			
		} catch (CreationTableFailedException e) {
			throw e;
		}
	}
	
	@Override
	public void createTable() throws CreationTableFailedException{
		Transaction transaction = null;
		try {
			
			StringBuffer sb = new StringBuffer("CREATE TABLE IF NOT EXISTS ").append(tableName);
			sb.append("(").append(fieldResourceID).append("VARCHAR(250) NOT NULL,")
			  .append(fieldElementID).append(" VARCHAR(250) NOT NULL,")
			  .append(fieldReferencedElement).append(" VARCHAR(250) NOT NULL,")
			  .append(fieldReferencedType).append(" VARCHAR(250) NOT NULL,")
			  .append("PRIMARY KEY (").append(fieldResourceID).append(",").append(fieldElementID).append("))");
		
			
			
			
			startDatabaseAccess();
			transaction = session.beginTransaction();
		    SQLQuery query = session.createSQLQuery(sb.toString());
			query.executeUpdate();
			transaction.commit();
			if (!tableExists(DbProperties.getString("Reference"))) {
				throw new CreationTableFailedException("Table could not be created!");
			}
			logger.log(Level.FINE, "Table created: "+tableName);
		}catch (CreationTableFailedException e) {
		     if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		finally {
		     endDatabaseAccess();
		}
	}
	
	/* (non-Javadoc)
	 * @see sapience.annotations.hibernate.DatabaseController#deleteAllRecords(java.lang.String)
	 */
	@Override
	public void deleteAllRecords(String tableName) throws TableNotExistException, DeleteRecordFailedException{
		Transaction transaction = null;
		try {
			startDatabaseAccess(); 
			if(!tableExists(tableName)) throw new TableNotExistException(tableName);
	
			
			
			transaction = session.beginTransaction();
			Query query = session.createQuery("delete from "+tableName);
			query.executeUpdate();
			transaction.commit();
			if (tableIsEmpty(tableName)) {
				logger.log(Level.FINE, "All records deleted!");
			} else {
				throw new DeleteRecordFailedException(query);
			}
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (DeleteRecordFailedException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} 
		finally {
			endDatabaseAccess();
		}
	}

	@Override
	public void addRecord(Entity entity) throws WrongEntityException, TableNotExistException, AddRecordFailedException, RecordExistsException {
		Transaction transaction = null;
		Reference concept = null;
		try {
			startDatabaseAccess();
			if (entity instanceof Reference) {
				concept = (Reference) entity;
			}else{
				throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
			}
			if (!tableExists(tableName)) {
				throw new TableNotExistException("Table "+tableName+" doesn't exist");
			}
			if (!recordExists(concept)) {
				transaction = session.beginTransaction();
			    session.save(concept);
			    transaction.commit();
			    
			    if (recordExists(concept)) {
					logger.log(Level.FINE, "Record added!");
				} else {
					throw new AddRecordFailedException("Record could not be added!");
				} 
			} else {
				throw new RecordExistsException("Record could not be added, because this record already exists!");
			}
		} catch (WrongEntityException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}  catch (AddRecordFailedException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (RecordExistsException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} 
		
		finally {
			endDatabaseAccess();
		}
	}

	@Override
	public void deleteRecord(Entity entity) throws WrongEntityException, TableNotExistException, DeleteRecordFailedException {
		Transaction transaction = null;
		Reference concept = null;
		try {
			startDatabaseAccess();
			if (entity instanceof Reference) {
				concept = (Reference) entity;
			}else{
				throw new WrongEntityException(entity.getClass().toString(), "Concept");
			}
			if (!tableExists(tableName)) {
				throw new TableNotExistException(tableName);
			}
			
			
			transaction = session.beginTransaction();
			SQLQuery q = session.createSQLQuery("delete from Concept where uri = :uri and xPath = :xPath");
		    q.setString("uri", concept.getUri());
		    q.setString("xPath", concept.getXPath());
		    q.executeUpdate();
		    transaction.commit();
		    if (!recordExists(concept)) {
				logger.log(Level.FINE, "Record deleted!");
			} else {
				throw new DeleteRecordFailedException("Record could not be deleted!");
			}
		} catch (WrongEntityException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (DeleteRecordFailedException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		
		finally {
			endDatabaseAccess();
		}
	}

	/* (non-Javadoc)
	 * @see sapience.annotations.hibernate.DatabaseController#recordExists(sapience.annotations.hibernate.entities.Entity)
	 */
	@Override
	protected boolean recordExists(Entity entity) throws WrongEntityException {
		Reference ref = null;
		if (entity instanceof Reference) {
			ref = (Reference) entity;
		}else{
			throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
		}
		Transaction transaction = session.beginTransaction();
		
		Criterion sameResourceID =  Restrictions.ilike(fieldResourceID, ref.getLocalElement().getResourceID(), MatchMode.EXACT);
		Criterion sameElementID = Restrictions.ilike(fieldElementID, ref.getLocalElement().getElementID(), MatchMode.EXACT);
		Criterion sameReferencedElement = Restrictions.ilike(fieldReferencedElement, ref.getReferencedEntity(), MatchMode.EXACT);
		
		
		Criteria query = session.createCriteria(Reference.class).add(sameResourceID).add(sameElementID).add(sameReferencedElement);
		
		
		List records = query.list();
		transaction.commit();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks whether a concept exists or not regarding to its URI and xPath.
	 * @param entity
	 * @return
	 * @throws WrongEntityException
	 */
	public boolean conceptExists(Entity entity) throws WrongEntityException {
		Reference concept = null;
		if (entity instanceof Reference) {
			concept = (Reference) entity;
		}else{
			throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
		}
		startDatabaseAccess();
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(Reference.class).add(Restrictions.ilike("uri", concept.getUri(), MatchMode.EXACT)).add(Restrictions.ilike("xPath", concept.getXPath(), MatchMode.EXACT));
		List records = query.list();
		transaction.commit();
		endDatabaseAccess();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Entity selectRecord(Entity entity) throws WrongEntityException, TableNotExistException {
		Transaction transaction = null;
		Reference concept = null;
		try {
			startDatabaseAccess();
			if (entity instanceof Reference) {
				concept = (Reference) entity;
			}else{
				throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
			}
			if (!tableExists(concept.getTableName())) {
				throw new TableNotExistException("Table "+concept.getTableName()+" doesn't exist");
			}
			transaction = session.beginTransaction();
			Criteria query =
		    	session.createCriteria(Reference.class).add(Restrictions.ilike("uri", concept.getUri(), MatchMode.EXACT)).add(Restrictions.ilike("xPath", concept.getXPath(), MatchMode.EXACT));
			List records = query.list();
			transaction.commit();
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				concept = (Reference) iterator.next();
			}
			return concept;
		} catch (WrongEntityException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		finally {
			endDatabaseAccess();
		}
	}

	@Override
	public List<Entity> selectRecords(String tablename, String column, String value) throws TableNotExistException {
		Transaction transaction = null;
		ArrayList<Reference> concepts = new ArrayList<Reference>();
		try {
			startDatabaseAccess();
			if (!tableExists(tablename)) {
				throw new TableNotExistException("Table "+tablename+" doesn't exist");
			}
			transaction = session.beginTransaction();
		    Criteria query =
		    	session.createCriteria(Reference.class).add(Restrictions.ilike(column, value, MatchMode.EXACT));
			List<Entity> records = query.list();
			transaction.commit();
			return records;
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		finally {
			endDatabaseAccess();
		}
	}

	@Override
	protected boolean tableExists(String tableName) {
		Transaction transaction = session.beginTransaction();
		SQLQuery query = session.createSQLQuery("select name from sqlite_master where name = '"+tableName+"'");
		List tables = query.list();
		transaction.commit();
		if (tables.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean tableIsEmpty(String tableName) {
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(Reference.class);
		List tables = query.list();
		transaction.commit();
		if (tables.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateRecord(Entity entity) throws WrongEntityException, TableNotExistException, UpdateRecordFailedException {
		Transaction transaction = null;
		Reference concept = null;
		try {
			startDatabaseAccess();
			if (entity instanceof Reference) {
				concept = (Reference) entity;
			}else{
				throw new WrongEntityException("Wrong entity! This method needs the entity Concept");
			}
			if (!tableExists(concept.getTableName())) {
				throw new TableNotExistException("Table "+concept.getTableName()+" doesn't exist");
			}
			transaction = session.beginTransaction();
			session.update(concept);
			transaction.commit();
			if (recordExists(concept)) {
				logger.log(Level.FINE, "Record updated!");
			}else{
				throw new UpdateRecordFailedException("Record could not be updated");
			}
		} catch (WrongEntityException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (UpdateRecordFailedException e) {
			if (transaction!=null) transaction.rollback();
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		
		finally {
			endDatabaseAccess();
		}
	}
}
