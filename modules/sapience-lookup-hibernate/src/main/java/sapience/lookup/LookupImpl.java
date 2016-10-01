/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package sapience.lookup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sapience.injectors.Lookup;
import sapience.injectors.model.Reference;
import sapience.lookup.exceptions.FailedAddRecordException;
import sapience.lookup.exceptions.FailedCreateTableException;
import sapience.lookup.exceptions.FailedUpdateRecordException;
import sapience.lookup.exceptions.RecordExistException;
import sapience.lookup.exceptions.TableNotExistException;
import sapience.lookup.exceptions.WrongEntityException;
import sapience.lookup.hibernate.controller.DbProperties;
import sapience.lookup.hibernate.controller.ReferenceController;
import sapience.lookup.hibernate.model.DatabaseEntity;
import sapience.lookup.hibernate.model.HibernateModelFactory;
import sapience.lookup.hibernate.model.HibernateReference;

/**
 * This class provides load and store functionalities for Reference objects and can be used by any Injector.
 * This object works as a Singleton.
 * 
 * @author Henry Michels
 *
 */
public class LookupImpl implements Lookup {
	
	protected static Logger LOG = Logger.getLogger(Lookup.class.getName());
	
	private ReferenceController refControl = null;
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static LookupImpl lookup = null;
	
	/**
	 * Constructor, which calls the "init" method.
	 * @throws IOException
	 */
	private LookupImpl() throws IOException {
		init();
	}
	
	/**
	 * Method to provide the Singleton design pattern.
	 * @return
	 * @throws IOException
	 */
	public static Lookup getInstance() throws IOException{
		if (lookup == null) {
			lookup = new LookupImpl();
			return lookup;
		} else {
			return lookup;
		}
	}

	/**
	 * First actions, which are called only one time if the object will be created.
	 * @throws IOException
	 */
	private void init() throws IOException  {
		try {
			
			//new instance of the ReferenceController, which delegates all database actions
			refControl = new ReferenceController();
			
		} catch (FailedCreateTableException e) {
			LOG.log(Level.WARNING, "Failed to init lookup");
			throw new IOException(e);
		} 
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public ReferenceController getReferenceController() throws IOException{
		if (refControl == null) {
			init();
		} 
		return refControl;
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.injectors.Lookup#check(java.io.Serializable)
	 */
	public List<Reference> check(Serializable request) throws IOException{
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		parameters.put(DbProperties.getString("Reference.Field.DocID"), request.toString());
		// reading out all database entries with the given docID
		List<DatabaseEntity> incomingDatabaseEntity;
		try {
			incomingDatabaseEntity = refControl.selectRecords(parameters);
			List<Reference> outgoingPersistentReferences = new ArrayList<Reference>();
			HibernateReference binding;
			for (int i = 0; i < incomingDatabaseEntity.size(); i++) {
				if (incomingDatabaseEntity.get(i) instanceof HibernateReference) {
					binding = (HibernateReference) incomingDatabaseEntity.get(i);
					outgoingPersistentReferences.add(HibernateModelFactory.quotesToApostrophes(binding));
				}
			}
			return outgoingPersistentReferences;
		} catch (TableNotExistException e) {
			throw new IOException(e);
		}
	}



	/*
	 * (non-Javadoc)
	 * @see sapience.injectors.Lookup#put(java.lang.String, java.util.List)
	 */
	public void put(String request, List<Reference> refList) throws IOException {
		try {
			/*
			File logFile = new File("C:/Dokumente und Einstellungen/Henry/Desktop/1dblog.txt");
			FileWriter out = new FileWriter(logFile);
			*/
			for(Reference ref : refList) {
				if(ref instanceof HibernateReference) 
					/*
					out.write("FastSearchDocID: "+((HibernateReference) ref).getFastSearchDocID());
					out.write("DocID: "+((HibernateReference) ref).getId().getDocID());
					out.write("ElemID: "+((HibernateReference) ref).getId().getElemID());
					out.write("Element: "+((HibernateReference) ref).getElement());
					out.close();
					*/
					HibernateModelFactory.apostrophesToQuotes((HibernateReference) ref);
					
					if (refControl.referenceExists((HibernateReference) ref)) {
						refControl.updateRecord((HibernateReference) ref);
					} else {
						refControl.addRecord((HibernateReference) ref);
					}
			}
		} catch (FailedUpdateRecordException e) {
			throw new IOException(e);
		} catch (FailedAddRecordException e) {
			throw new IOException(e);
		} catch (RecordExistException e) {
			throw new IOException(e);
		} catch (WrongEntityException e) {
			throw new IOException(e);
		} catch (TableNotExistException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.injectors.Lookup#put(java.lang.String, sapience.injectors.model.Reference)
	 */
	public void put(String request, Reference reference) throws IOException {
		throw new IOException("not supported");
	}

}
