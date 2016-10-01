/**
 * 
 */
package sapience.annotations.hibernate.entities;

import sapience.annotations.model.Model;

/**
 * @author Henry
 *
 *
 * Used for what?
 */
public interface Entity {
	
	/**
	 * Returns the create-statement for this entity.
	 * @return
	 * 
	 * 
	 */
	// pat: should be in the controller for this entity
	// public String getCreateStatement();
	
	/**
	 * This method compares the actual instance of Concept with the given instance of Concept
	 * @param entity
	 * @return
	 */
	// either Interface "comparable" or using the already existing equals
	//public boolean equals(Entity entity);
	
	/**
	 * This method returns the name of the table of this Entity
	 * @return
	 */
	// also handled by the Controller
	// public String getTableName();
	
	/**
	 * This method returns the associated Model.
	 * @return
	 */
	// ???
	public Model toModel();
}