package sapience.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sapience.annotations.exceptions.AddRecordFailedException;
import sapience.annotations.exceptions.CreationTableFailedException;
import sapience.annotations.exceptions.RecordExistsException;
import sapience.annotations.exceptions.TableNotExistException;
import sapience.annotations.exceptions.UpdateRecordFailedException;
import sapience.annotations.exceptions.WrongEntityException;
import sapience.annotations.hibernate.ReferenceController;
import sapience.annotations.hibernate.entities.Entity;
import sapience.annotations.model.Reference;

/**
 * This class provides load and store functionalities for Referenc objects and is used by the Injectors.
 * 
 * @author Henry
 *
 */
public class LookupImpl implements Lookup{
	
	private ReferenceController refControl;
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	public LookupImpl() throws CreationTableFailedException{
		init();
	}

	private void init() throws CreationTableFailedException {
		try {
			refControl = new ReferenceController();
		} catch (CreationTableFailedException e) {
			refControl = null;
			throw e;
		}
	}

	@Override
	public List<Reference> check(String uri) throws TableNotExistException {
		List<Entity> incomingReferences;
		incomingReferences = refControl.selectRecords("Concept", "uri", uri);
		ArrayList<Reference> outgoingReferences = new ArrayList<Reference>();
		for (int i = 0; i < incomingReferences.size(); i++) {
			outgoingReferences.add((Reference) incomingReferences.get(i).toModel());
		}
		return outgoingReferences;
	}

	@Override
	public void put(Reference reference) throws WrongEntityException, TableNotExistException, UpdateRecordFailedException, AddRecordFailedException, RecordExistsException {
		if (refControl.conceptExists(reference.toEntity())) {
			refControl.updateRecord(reference.toEntity());
		} else {
			refControl.addRecord(reference.toEntity());
		}
	}
	
	public ReferenceController getReferenceController(){
		return refControl;
	}
}
