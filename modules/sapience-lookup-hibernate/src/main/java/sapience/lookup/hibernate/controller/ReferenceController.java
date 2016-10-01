package sapience.lookup.hibernate.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import sapience.lookup.exceptions.FailedAddRecordException;
import sapience.lookup.exceptions.FailedCreateTableException;
import sapience.lookup.exceptions.FailedDeleteRecordException;
import sapience.lookup.exceptions.FailedUpdateRecordException;
import sapience.lookup.exceptions.RecordExistException;
import sapience.lookup.exceptions.TableNotExistException;
import sapience.lookup.exceptions.WrongEntityException;
import sapience.lookup.hibernate.model.DatabaseEntity;
import sapience.lookup.hibernate.model.HibernateReference;

/**
 * This class implements the DatabaseController.
 * This class handles only PersistentReference objects (see sapience.annotations.lookup.hibernate.entities)
 * 
 * This class is able to create the correct database table and to provide standard database accesses
 * like add, delete, update or check.
 * 
 * @author Henry Michels
 *
 */
public class ReferenceController extends DatabaseController{

	/**
	 * Constructor, which creates the correct table for PersistentReference objects as its first step.
	 * @throws CreationTableFailedException 
	 */
	public ReferenceController() throws FailedCreateTableException{
		try {
			createTable();
		} catch (FailedCreateTableException e) {
			throw e;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#createTable()
	 */
	@Override
	public void createTable() throws FailedCreateTableException{
		Transaction transaction = null;
		try {
			startDatabaseAccess();
			transaction = session.beginTransaction();
		    SQLQuery query = session.createSQLQuery(DbProperties.getString("Reference.Table.Create"));
			query.executeUpdate();
			transaction.commit();
			if (!tableExists()) {
				throw new FailedCreateTableException(query);
			}
			logger.log(Level.FINE, "Table created: "+DbProperties.getString("Reference"));
		}catch (FailedCreateTableException e) {
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
	public void deleteAllRecords() throws TableNotExistException, FailedDeleteRecordException{
		Transaction transaction = null;
		try {
			startDatabaseAccess();
			session.clear(); 
			session.flush(); 
			
			if(!tableExists()) throw new TableNotExistException(DbProperties.getString("Reference"));
			transaction = session.beginTransaction();
			StringBuffer sb = new StringBuffer();
			sb.append("DELETE FROM ");
			sb.append(DbProperties.getString("Reference"));
			Query query = session.createQuery(sb.toString());
			query.executeUpdate();
			transaction.commit();
			session.flush(); 
			if (tableIsEmpty()) {
				logger.log(Level.FINE, "All records deleted!");
			} else {
				throw new FailedDeleteRecordException(query);
			}
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (FailedDeleteRecordException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} 
		finally {
			endDatabaseAccess();
		}
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#addRecord(sapience.annotations.lookup.hibernate.entities.DatabaseEntity)
	 */
	@Override
	public void addRecord(DatabaseEntity entity) throws WrongEntityException, TableNotExistException, FailedAddRecordException, RecordExistException {
		Transaction transaction = null;
		HibernateReference reference = null;
		try {
			startDatabaseAccess();
			if (entity instanceof HibernateReference) {
				reference = (HibernateReference) entity;
			}else{
				throw new WrongEntityException(entity);
			}
			if (!tableExists()) {
				throw new TableNotExistException("Table "+DbProperties.getString("Reference")+" doesn't exist");
			}
			if (!recordExists(reference)) {
				transaction = session.beginTransaction();
			    session.save(reference);
			    transaction.commit();
			    
			    if (recordExists(reference)) {
					logger.log(Level.FINE, "Record added!");
				} else {
					throw new FailedAddRecordException();
				} 
			} else {
				throw new RecordExistException();
			}
		} catch (WrongEntityException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}  catch (FailedAddRecordException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}  catch (RecordExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} 
		
		finally {
			endDatabaseAccess();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#deleteRecord(sapience.annotations.lookup.hibernate.entities.DatabaseEntity)
	 */
	@Override
	public void deleteRecord(DatabaseEntity entity) throws WrongEntityException, TableNotExistException, FailedDeleteRecordException {
		Transaction transaction = null;
		HibernateReference ref = null;
		try {
			startDatabaseAccess();
			if (entity instanceof HibernateReference) {
				ref = (HibernateReference) entity;
			}else{
				throw new WrongEntityException(entity);
			}
			if (!tableExists()) {
				throw new TableNotExistException(DbProperties.getString("Reference"));
			}
			transaction = session.beginTransaction();
			StringBuffer sb = new StringBuffer();
			sb.append("DELETE ");
			sb.append("FROM ");
			sb.append(DbProperties.getString("Reference"));
			sb.append(" WHERE ");
			sb.append(DbProperties.getString("Reference.Field.DocID"));
			sb.append(" = '");
			sb.append(ref.getId().getDocID());
			sb.append("' AND ");
			sb.append(DbProperties.getString("Reference.Field.ElementID"));
			sb.append(" = '");
			sb.append(ref.getId().getElemID());
			sb.append("'");
			Query query = session.createQuery(sb.toString());
			query.executeUpdate();
		    transaction.commit();
		    if (!recordExists(ref)) {
				logger.log(Level.FINE, "Record deleted!");
			} else {
				throw new FailedDeleteRecordException(query);
			}
		} catch (WrongEntityException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (FailedDeleteRecordException e) {
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
	public boolean recordExists(DatabaseEntity entity) throws WrongEntityException {
		HibernateReference ref = null;
		if (entity instanceof HibernateReference) {
			ref = (HibernateReference) entity;
		}else{
			throw new WrongEntityException(entity);
		}
		Transaction transaction = session.beginTransaction();
		StringBuffer sb = new StringBuffer();
		sb.append("FROM ");
		sb.append(DbProperties.getString("Reference"));
		sb.append(" WHERE ");
		sb.append(DbProperties.getString("Reference.Field.DocID"));
		sb.append(" = '");
		sb.append(ref.getId().getDocID());
		sb.append("' AND ");
		sb.append(DbProperties.getString("Reference.Field.ElementID"));
		sb.append(" = '");
		sb.append(ref.getId().getElemID());
		sb.append("' AND ");
//		sb.append(" = :test");
//		sb.append(" AND ");
		sb.append(DbProperties.getString("Reference.Field.Element"));
		sb.append(" = '");
		sb.append(ref.getElement());
		sb.append("'");
		Query query = session.createQuery(sb.toString());
//		query.setString("test", ref.getSource().getElementID().toString());
		List records = query.list();
		transaction.commit();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks whether a reference exists or not regarding to its ResourceID and ElementID.
	 * @param entity
	 * @return
	 * @throws WrongEntityException 
	 */
	public boolean referenceExists(DatabaseEntity entity) throws WrongEntityException {
		HibernateReference ref = null;
		if (entity instanceof HibernateReference) {
			ref = (HibernateReference) entity;
		}else{
			throw new WrongEntityException(entity);
		}
		startDatabaseAccess();
		Transaction transaction = session.beginTransaction();
		StringBuffer sb = new StringBuffer();
		sb.append("FROM ");
		sb.append(DbProperties.getString("Reference"));
		sb.append(" WHERE ");
		sb.append(DbProperties.getString("Reference.Field.DocID"));
		sb.append(" = '");
		sb.append(ref.getId().getDocID());
		sb.append("' AND ");
		sb.append(DbProperties.getString("Reference.Field.ElementID"));
		sb.append(" = '");
		sb.append(ref.getId().getElemID());
		sb.append("'");
		Query query = session.createQuery(sb.toString());
		List records = query.list();
		transaction.commit();
		endDatabaseAccess();
		if (records.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#selectRecord(sapience.annotations.lookup.hibernate.entities.DatabaseEntity)
	 */
	@Override
	public DatabaseEntity selectRecord(DatabaseEntity entity) throws WrongEntityException, TableNotExistException {
		Transaction transaction = null;
		HibernateReference ref = null;
		try {
			startDatabaseAccess();
			if (entity instanceof HibernateReference) {
				ref = (HibernateReference) entity;
			}else{
				throw new WrongEntityException(entity);
			}
			if (!tableExists()) {
				throw new TableNotExistException(DbProperties.getString("Reference"));
			}
			transaction = session.beginTransaction();
			StringBuffer sb = new StringBuffer();
			sb.append("FROM ");
			sb.append(DbProperties.getString("Reference"));
			sb.append(" WHERE ");
			sb.append(DbProperties.getString("Reference.Field.DocID"));
			sb.append(" = '");
			sb.append(ref.getId().getDocID());
			sb.append("' AND ");
			sb.append(DbProperties.getString("Reference.Field.ElementID"));
			sb.append(" = '");
			sb.append(ref.getId().getElemID());
			sb.append("'");
			Query query = session.createQuery(sb.toString());
			ref = (HibernateReference) query.uniqueResult();
			transaction.commit();
			return ref;
		} catch (WrongEntityException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		finally {
			endDatabaseAccess();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#selectRecords(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DatabaseEntity> selectRecords(Hashtable<String,String> columnsAndValues) throws TableNotExistException {
		Transaction transaction = null;
		try {
			startDatabaseAccess();
			if (!tableExists()) {
				throw new TableNotExistException(DbProperties.getString("Reference"));
			}
			Set<Entry<String, String>> columnsAndValuesSet = columnsAndValues.entrySet();
			int findEnd = columnsAndValuesSet.size();
			transaction = session.beginTransaction();
			StringBuffer sb = new StringBuffer();
			sb.append("FROM ");
			sb.append(DbProperties.getString("Reference"));
			sb.append(" WHERE ");
			for (Entry<String, String> entry : columnsAndValuesSet) {
				sb.append(entry.getKey());
				sb.append(" = '");
				sb.append(entry.getValue());
				if (findEnd > 1) {
					sb.append("' AND ");
				}
				findEnd--;
			}
			sb.append("'");
			Query query = session.createQuery(sb.toString());
			List<DatabaseEntity> records = query.list();
			transaction.commit();
			return records;
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		finally {
			endDatabaseAccess();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#tableExists()
	 */
	@Override
	protected boolean tableExists() {
		Transaction transaction = session.beginTransaction();
		SQLQuery query = session.createSQLQuery(DbProperties.getString("Reference.Table.Exist"));
		List tables = query.list();
		transaction.commit();
		if (tables.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#tableIsEmpty()
	 */
	@Override
	protected boolean tableIsEmpty() {
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(HibernateReference.class);
		List tables = query.list();
		transaction.commit();
		if (tables.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.annotations.lookup.hibernate.DatabaseController#updateRecord(sapience.annotations.lookup.hibernate.entities.DatabaseEntity)
	 */
	@Override
	public void updateRecord(DatabaseEntity entity) throws WrongEntityException, TableNotExistException, FailedUpdateRecordException {
		Transaction transaction = null;
		HibernateReference reference = null;
		try {
			startDatabaseAccess();
			if (entity instanceof HibernateReference) {
				reference = (HibernateReference) entity;
			}else{
				throw new WrongEntityException(entity);
			}
			if (!tableExists()) {
				throw new TableNotExistException(DbProperties.getString("Reference"));
			}
			transaction = session.beginTransaction();
			session.update(reference);
			transaction.commit();
			if (recordExists(reference)) {
				logger.log(Level.FINE, "Record updated!");
			}else{
				throw new FailedUpdateRecordException();
			}
		} catch (WrongEntityException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (TableNotExistException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		} catch (FailedUpdateRecordException e) {
		     logger.log(Level.SEVERE, e.getMessage(), e);
		     throw e;
		}
		
		finally {
			endDatabaseAccess();
		}
	}
	

}
