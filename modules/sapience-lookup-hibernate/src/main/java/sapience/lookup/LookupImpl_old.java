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
//
//import java.io.IOException;
//import java.net.URI;
//import java.nio.CharBuffer;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import sapience.annotations.model.references.DomainReference;
//import sapience.annotations.model.references.ModelReference;
//import sapience.annotations.model.references.Reference;
//import sapience.injectors.Injector;
//import sapience.injectors.lookup.Lookup;
//import sapience.injectors.model.LocalElementIdentifier;
//import sapience.lookup.exceptions.FailedAddRecordException;
//import sapience.lookup.exceptions.FailedCreateTableException;
//import sapience.lookup.exceptions.FailedUpdateRecordException;
//import sapience.lookup.exceptions.RecordExistException;
//import sapience.lookup.exceptions.TableNotExistException;
//import sapience.lookup.exceptions.WrongDirectoryException;
//import sapience.lookup.exceptions.WrongEntityException;
//import sapience.lookup.filesystem.ConsistencyChecker;
//import sapience.lookup.filesystem.ConsistencyCheckerImpl;
//import sapience.lookup.hibernate.DbProperties;
//import sapience.lookup.hibernate.ReferenceController;
//import sapience.lookup.hibernate.entities.DatabaseEntity;
//import sapience.lookup.hibernate.entities.PersistentReference;
//
///**
// * This class provides load and store functionalities for Reference objects and can be used by any Injector.
// * This object works as a Singleton.
// * 
// * @author Henry Michels
// *
// */
//
//
//public class LookupImpl_old implements Lookup{
//	
//	protected static Logger LOG = Logger.getLogger(Lookup.class.getName());
//	
//	private ReferenceController refControl = null;
//	
//	protected Logger logger = Logger.getLogger(this.getClass().getName());
//	
//	private static LookupImpl_old lookup = null;
//	
//	// a contentClue is a CharBuffer, which stores the first 256 chars of a file
//	private Set<CharBuffer> contentClues = null; 
//	
//	private ConsistencyChecker consistencyChecker = null;
//	
//	/**
//	 * Constructor, which calls the "init" method.
//	 * @throws IOException
//	 */
//	private LookupImpl_old() throws IOException {
//		init();
//	}
//	
//	/**
//	 * Method to provide the Singleton design pattern.
//	 * @return
//	 * @throws IOException
//	 */
//	public static Lookup getInstance() throws IOException{
//		if (lookup == null) {
//			lookup = new LookupImpl_old();
//			return lookup;
//		} else {
//			return lookup;
//		}
//	}
//
//	/**
//	 * First actions, which are called only one time if the object will be created.
//	 * @throws IOException
//	 */
//	private void init() throws IOException  {
//		try {
//			
//			//new instance of the ReferenceController, which delegates all database actions
//			refControl = new ReferenceController();
//			
//		} catch (FailedCreateTableException e) {
//			LOG.log(Level.WARNING, "Failed to init lookup");
//			throw new IOException(e);
//		} 
//	}
//	
//	private ConsistencyChecker getDefaultConsistencyChecker() throws IOException  {
//		try {
//			return new ConsistencyCheckerImpl(this);
//		} catch (WrongDirectoryException e) {
//			LOG.log(Level.WARNING, "Failed to init lookup, wrong directory given.");
//			throw new IOException(e);
//		} 
//	}
//	
//	public void setConsistencyChecker(ConsistencyChecker cc) {
//		//new instance of the ConsistencyChecker, which is a Thread. This Thread looks for
//		//annotated files in a given filesystem in constant time intervals.
//		this.consistencyChecker = cc;
//	}
//	
//	public ConsistencyChecker getConsistencyChecker() throws IOException {
//		if (consistencyChecker == null) {
//			consistencyChecker = getDefaultConsistencyChecker();
//			
//		}
//
//		return consistencyChecker;
//	}
//	
//	/* (non-Javadoc)
//	 * @see sapience.annotations.lookup.Lookup#check(java.net.URI, sapience.injectors.AbstractInjector, java.nio.CharBuffer)
//	 */
//	public synchronized List<Reference> check(URI docID, Injector injector, CharBuffer contentClue) throws IOException, TableNotExistException {
//		
//		// Before the asked docID will be checked the given contentClue will be compared with all
//		// stored contentClues. If there is a match, the "update" method of the ConsistenyChecker
//		// will be called. This method takes the given Injector and initiates a "put" call on the
//		// Lookup component.
//		if (compareContentClue(contentClue)) {
////			consistencyChecker.update(docID.toString(), contentClue, injector);
//		}
//		
//		// reading out all database entries with the given docID
//		List<PersistentReference> incomingPersistentReferences = new ArrayList<PersistentReference>();
//		List<DatabaseEntity> incomingDatabaseEntity = refControl.selectRecords(DbProperties.getString("Reference.Field.ResourceID"), docID.toString());
//		for (int i = 0; i < incomingDatabaseEntity.size(); i++) {
//			if (incomingDatabaseEntity.get(i) instanceof PersistentReference) {
//				incomingPersistentReferences.add((PersistentReference) incomingDatabaseEntity.get(i));
//			}
//		}
//		
//		// Building up a new list, with the help of the database entries.
//		List<Reference> outgoingPersistentReferences = new ArrayList<Reference>();
//		for (int i = 0; i < incomingPersistentReferences.size(); i++) {
//			if (incomingPersistentReferences.get(i).getReferenceType().equalsIgnoreCase("domain")) {
//				outgoingPersistentReferences.add(new DomainReference(new LocalElementIdentifier(incomingPersistentReferences.get(i).getReferenceID().getResourceID(), incomingPersistentReferences.get(i).getReferenceID().getElementID()), incomingPersistentReferences.get(i).getReferencedElement()));
//
//			} else {
//				if (incomingPersistentReferences.get(i).getReferenceType().equalsIgnoreCase("model")) {
//					outgoingPersistentReferences.add(new ModelReference(new LocalElementIdentifier(incomingPersistentReferences.get(i).getReferenceID().getResourceID(), incomingPersistentReferences.get(i).getReferenceID().getElementID()), incomingPersistentReferences.get(i).getReferencedElement()));
//		
//				} else {
//					
//				}
//			}
//			
//		}
//		return outgoingPersistentReferences;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see sapience.annotations.lookup.Lookup#put(sapience.annotations.model.references.Reference)
//	 */
//	public synchronized void put(Reference reference) throws IOException, FailedUpdateRecordException, WrongEntityException, TableNotExistException, FailedAddRecordException, RecordExistException {
//		PersistentReference persistentReference = new PersistentReference(reference);
//		if (refControl.referenceExists(persistentReference)) {
//			refControl.updateRecord(persistentReference);
//		} else {
//			refControl.addRecord(persistentReference);
//		}
//	}
//	
//	/**
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	public ReferenceController getReferenceController() throws IOException{
//		if (refControl == null) {
//			init();
//		} 
//		return refControl;
//	}
//	
//	/**
//	 * This method compares the given contentClue out of the check request with all stored
//	 * contentClues
//	 * @param contentClue
//	 * @return
//	 */
//	private boolean compareContentClue(CharBuffer contentClue){
//		if (listClues().contains(contentClue)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	/**
//	 * This method provides a synchronized access to all contentClues
//	 * @return
//	 */
//	public Set<CharBuffer> listClues() {
//		if(this.contentClues == null) { //lazy
//			this.contentClues = Collections.synchronizedSet(new HashSet<CharBuffer>());
//		}
//		return this.contentClues;
//	}
//}
