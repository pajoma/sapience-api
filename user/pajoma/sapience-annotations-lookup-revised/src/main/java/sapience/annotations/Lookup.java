package sapience.annotations;

import java.util.List;

import sapience.annotations.exceptions.DatabaseException;
import sapience.annotations.model.Reference;

/**
 * The interface of the lookup component
 * 
 * @author Henry Michels
 */
public interface Lookup {
	
	/**
	 * This method returns all References, which contain the given URI.
	 * @param uri
	 * @return list of References
	 */
	public List<Reference> check(String uri)throws DatabaseException;
	
	/**
	 * This method adds or updates the given reference in the database.
	 * @param reference
	 */
	public void put(Reference reference)throws DatabaseException;

}
