/**
 * 
 */
package sapience.annotations.model;

import sapience.annotations.hibernate.entities.Entity;

/**
 * @author Henry
 *
 */
public interface Model {
	
	/**
	 * This method returns the associated Concept
	 * @return
	 */
	public Entity toEntity();

}
